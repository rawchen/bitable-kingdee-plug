package com.lundong.plug.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lundong.plug.config.Constants;
import com.lundong.plug.convert.TenantAuthConvert;
import com.lundong.plug.entity.OrgEntity;
import com.lundong.plug.entity.StockEntity;
import com.lundong.plug.entity.TenantAuth;
import com.lundong.plug.entity.param.BitCommonContext;
import com.lundong.plug.entity.param.CommonReq;
import com.lundong.plug.entity.result.R;
import com.lundong.plug.entity.result.RecordResp;
import com.lundong.plug.entity.result.TableMetaResp;
import com.lundong.plug.entity.vo.TenantAuthVo;
import com.lundong.plug.mapper.TenantAuthMapper;
import com.lundong.plug.service.KingdeeService;
import com.lundong.plug.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shuangquan.chen
 * @date 2023-11-21 11:46
 */
@Slf4j
@RestController
public class MainController {

    @Autowired
    KingdeeService kingdeeService;

    @Autowired
    TenantAuthMapper tenantAuthMapper;


    @RequestMapping(value = "/kingdee/table_meta")
    public R<TableMetaResp> tableMeta(@RequestBody CommonReq req, HttpServletRequest request) {
        // 该接口返回 数据源的表结构，指明数据源的数据有哪些字段，每个字段是什么类型
        // formType = 3 默认只有一个即时库存需要同步
        Integer formType = 3;
        String timestamp = request.getHeader("X-Base-Request-Timestamp");
        String nonce = request.getHeader("X-Base-Request-Nonce");
        String signature = request.getHeader("X-Base-Signature");
        String s = SignUtil.genPostRequestSignature(nonce, timestamp, JSONUtil.toJsonStr(req), Constants.SECRET_KEY);
        log.info("签名: {}", s);
//        if (signature == null || !signature.equals(s)) {
//            return R.fail("签名不正确", null);
//        }

        log.info("req: {}", req);
        TableMetaResp tableMetaResp = kingdeeService.tableMeta(formType);
        return R.ok(tableMetaResp);
    }

    @RequestMapping(value = "/kingdee/records")
    public R<RecordResp> records(@RequestBody CommonReq req, HttpServletRequest request) {
        String timestamp = request.getHeader("X-Base-Request-Timestamp");
        String nonce = request.getHeader("X-Base-Request-Nonce");
        String signature = request.getHeader("X-Base-Signature");
        String s = SignUtil.genPostRequestSignature(nonce, timestamp, JSONUtil.toJsonStr(req), Constants.SECRET_KEY);
        log.info("签名: {}", s);
//        if (signature == null || !signature.equals(s)) {
//            return R.fail("签名不正确", null);
//        }

        log.info("req: {}", req);
        RecordResp resp = kingdeeService.records(req);
        return R.ok(resp);
    }

    @RequestMapping(value = "/kingdee/stockList")
    public R<List<StockEntity>> stockList(@RequestBody CommonReq req, HttpServletRequest request) {
        List<StockEntity> stockEntityList = kingdeeService.stockList(req);
        log.info("stockEntityList size: {}", stockEntityList.size());
        return R.ok(stockEntityList);
    }

    @RequestMapping(value = "/kingdee/orgList")
    public R<List<OrgEntity>> orgList(@RequestBody CommonReq req, HttpServletRequest request) {
        List<OrgEntity> orgList = kingdeeService.orgList(req);
        log.info("orgList size: {}", orgList.size());
        return R.ok(orgList);
    }

    @RequestMapping(value = "/kingdee/login")
    public R<RecordResp> kingdeeLogin(@RequestBody CommonReq req, HttpServletRequest request) {
        return kingdeeService.kingdeeLogin(req);
    }

    @RequestMapping(value = "/meta.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object metaJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("meta.json");
        String json = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        return JSONUtil.toBean(json, Object.class);
    }

    /**
     * 查看租户套餐（1试用带剩余天数 2普通 3高级）
     *
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryPackage")
    public R<TenantAuthVo> select(@RequestBody CommonReq req, HttpServletRequest request) {
        TenantAuthVo vo;
        BitCommonContext context = JSONUtil.toBean(req.getContext(), BitCommonContext.class);
        String tenantKey = context.getBitable().getTenantKey();
        log.info("queryPackage tenantKey: {}", tenantKey);
        TenantAuth tenantAuth = tenantAuthMapper.selectOne(new LambdaQueryWrapper<TenantAuth>().eq(TenantAuth::getTenantKey, tenantKey).last("limit 1"));
        if (tenantAuth == null) {
            // 通过租户获取tenant_auth，如果空的就说明该租户没用过插件，新建实体，设置套餐id为试用1
            TenantAuth tenantAuthNew = new TenantAuth().setTenantKey(tenantKey).setAuthorizationId(1L).setCreatedAt(LocalDateTime.now()).setUpdatedAt(LocalDateTime.now());
            tenantAuthMapper.insert(tenantAuthNew);
            vo = TenantAuthConvert.INSTANCE.tenantAuthToTenantAuthVo(tenantAuthNew);
        } else {
            vo = TenantAuthConvert.INSTANCE.tenantAuthToTenantAuthVo(tenantAuth);
        }
        return R.ok(vo);
    }

    @RequestMapping(value = "/insert")
    public R insert() {
        TenantAuth tenantAuth = new TenantAuth();
        tenantAuth.setTenantKey("ou_123");
        tenantAuth.setAuthorizationId(1L);
        tenantAuth.setCreatedAt(LocalDateTime.now());
        tenantAuth.setUpdatedAt(LocalDateTime.now());

        tenantAuthMapper.insert(tenantAuth);
        return R.ok();
    }

    //    @RequestMapping(value = "/kingdee/data_meta")
//    public void dataMeta() {
//        List<FormTypeDict> formTypeDictList = Constants.formTypeDictList;
//        log.info("formTypeDictList size: {}", formTypeDictList.size());
//        for (FormTypeDict formTypeDict : formTypeDictList) {
//            System.out.println(formTypeDict);
//        }
//    }

//    @RequestMapping(value = "/kingdee/formTypeList")
//    public List<FormTypeDict> formTypeList() {
//        List<FormTypeDict> formTypeDictList = Constants.formTypeDictList;
//        log.info("formTypeDictList size: {}", formTypeDictList.size());
//        return formTypeDictList;
//    }
}
