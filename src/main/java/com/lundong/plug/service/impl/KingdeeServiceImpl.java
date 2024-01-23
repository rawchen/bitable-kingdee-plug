package com.lundong.plug.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lundong.plug.config.Constants;
import com.lundong.plug.entity.*;
import com.lundong.plug.entity.param.BitCommonContext;
import com.lundong.plug.entity.param.BitCommonParam;
import com.lundong.plug.entity.param.CommonReq;
import com.lundong.plug.entity.param.KingdeeParam;
import com.lundong.plug.entity.result.*;
import com.lundong.plug.enums.FormTypeEnum;
import com.lundong.plug.mapper.TenantAuthMapper;
import com.lundong.plug.service.KingdeeService;
import com.lundong.plug.service.TenantAuthService;
import com.lundong.plug.util.SignUtil;
import com.lundong.plug.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:37
 */
@Slf4j
@Service
public class KingdeeServiceImpl implements KingdeeService {

    @Autowired
    TenantAuthMapper tenantAuthMapper;

    @Autowired
    TenantAuthService tenantAuthService;

    @Override
    public List<KingdeeDept> queryDepartmentList(KingdeeParam kingdeeParam, Long lineLimitNumber) {
        List<KingdeeDept> kingdeeDeptList = new ArrayList<>();
        try {
            List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
//            String resultString = api.executeBillQueryJson("");
            String paramDetailJson = "{\"Data\":{\"FormId\":\"BD_Department\",\"FieldKeys\":\"FNAME,FNUMBER,FDescription,FDEPTID,FPARENTID,FCreateOrgId,FUseOrgId\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":" + lineLimitNumber + ",\"SubSystemId\":\"\"}}";
            String resultString = HttpRequest.post(StringUtil.convertUrl(kingdeeParam.getKingdeeUrl()) + Constants.KINGDEE_QUERY)
                    .body(paramDetailJson)
                    .cookie(httpCookies)
                    .execute().body();
            log.info("金蝶部门列表查询接口: {}", resultString);
            JSONArray resultArray = (JSONArray) JSONObject.parse(resultString);
            if (resultArray == null) {
                return Collections.emptyList();
            }

            for (int i = 0; i < resultArray.size(); i++) {
                JSONArray jsonArray = (JSONArray) resultArray.get(i);
                KingdeeDept kingdeeDept = new KingdeeDept();
                kingdeeDept.setName(jsonArray.getString(0));
                kingdeeDept.setNumber(jsonArray.getString(1));
                kingdeeDept.setDescription(jsonArray.getString(2));
                kingdeeDept.setDeptId(jsonArray.getString(3));
                kingdeeDept.setParentId(jsonArray.getString(4));
                kingdeeDept.setCreateOrgId(jsonArray.getString(5));
                kingdeeDept.setUseOrgId(jsonArray.getString(6));
                kingdeeDeptList.add(kingdeeDept);
            }
            return kingdeeDeptList;
        } catch (Exception e) {
            log.error("接口调用失败: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<KingdeeEmployee> queryEmployeeList(KingdeeParam kingdeeParam, Long lineLimitNumber) {
        List<KingdeeEmployee> kingdeeEmployeeList = new ArrayList<>();
        try {
            List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
//            String resultString = api.executeBillQueryJson("");
            String paramDetailJson = "{\"Data\":{\"FormId\":\"BD_Empinfo\",\"FieldKeys\":\"FNAME,FStaffNumber,FDescription,FMobile,FEmail,FCreateOrgId,FUseOrgId\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10000,\"SubSystemId\":\"\"}}";
            String resultString = HttpRequest.post(StringUtil.convertUrl(kingdeeParam.getKingdeeUrl()) + Constants.KINGDEE_QUERY)
                    .body(paramDetailJson)
                    .cookie(httpCookies)
                    .execute().body();
            log.info("金蝶员工列表查询接口: {}", resultString);
            JSONArray resultArray = (JSONArray) JSONObject.parse(resultString);
            if (resultArray == null) {
                return Collections.emptyList();
            }

            for (int i = 0; i < resultArray.size(); i++) {
                JSONArray jsonArray = (JSONArray) resultArray.get(i);
                KingdeeEmployee employee = new KingdeeEmployee();
                employee.setName(jsonArray.getString(0));
                employee.setStaffNumber(jsonArray.getString(1));
                employee.setDescription(jsonArray.getString(2));
                employee.setMobile(jsonArray.getString(3));
                employee.setEmail(jsonArray.getString(4));
                employee.setCreateOrgId(jsonArray.getString(5));
                employee.setUseOrgId(jsonArray.getString(6));
                kingdeeEmployeeList.add(employee);
            }
            return kingdeeEmployeeList;
        } catch (Exception e) {
            log.error("接口调用失败: ", e);
        }
        return Collections.emptyList();
    }

    public List<KingdeeStock> queryJitInventoryList(KingdeeParam kingdeeParam, Long lineLimitNumber) {
        List<KingdeeStock> kingdeeStockList = new ArrayList<>();
        try {
            List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
            // 调用即时库存列表接口
            String apiStockJson = "{\n" +
                    "    \"format\":1,\n" +
                    "    \"useragent\":\"ApiClient\",\n" +
                    "    \"rid\":\"唯一码\",\n" +
                    "    \"parameters\":[{\n" +
                    "        \"fstockorgnumbers\":\"\",\n" +
                    "        \"fmaterialnumbers\":\"\",\n" +
                    "        \"fstocknumbers\":\"" + kingdeeParam.getNumber() + "\",\n" +
                    "        \"flotnumbers\":\"\",\n" +
                    "        \"isshowstockloc\":true,\n" +
                    "        \"isshowauxprop\":true,\n" +
                    "        \"pageindex\":1,\n" +
                    "        \"pagerows\":" + lineLimitNumber + "\n" +
                    "    }],\n" +
                    "    \"timestamp\":\"\",\n" +
                    "    \"v\":\"1.0\"\n" +
                    "}";
            String apiStockResult = HttpRequest.post(StringUtil.convertUrl(kingdeeParam.getKingdeeUrl()) + Constants.KINGDEE_INVENTORY)
                    .body(apiStockJson)
                    .cookie(httpCookies)
                    .execute().body();
            log.info("numbers: {}", kingdeeParam.getNumber());
            log.info("金蝶即时库存列表查询接口: {}", apiStockResult.length() > 100 ? apiStockResult.substring(0, 100) + "..." : apiStockResult);
            JSONObject jsonObject = JSONObject.parseObject(apiStockResult);
            if (jsonObject == null) {
                return Collections.emptyList();
            }
            JSONArray resultArrayNew = jsonObject.getJSONArray("data");
            if (resultArrayNew == null || resultArrayNew.isEmpty()) {
                return Collections.emptyList();
            }
            for (int i = 0; i < resultArrayNew.size(); i++) {
                KingdeeStock javaObject = JSONArray.toJavaObject(resultArrayNew.getJSONObject(i), KingdeeStock.class);
                if ("BD_Supplier".equals(javaObject.getOwnerTypeId())) {
                    javaObject.setOwnerTypeId("供应商");
                } else if ("BD_OwnerOrg".equals(javaObject.getOwnerTypeId())) {
                    javaObject.setOwnerTypeId("业务组织");
                } else if ("BD_Customer".equals(javaObject.getOwnerTypeId())) {
                    javaObject.setOwnerTypeId("客户");
                }
                if ("BD_KeeperOrg".equals(javaObject.getKeeperTypeId())) {
                    javaObject.setKeeperTypeId("业务组织");
                } else if ("BD_Customer".equals(javaObject.getKeeperTypeId())) {
                    javaObject.setKeeperTypeId("客户");
                } else if ("BD_Supplier".equals(javaObject.getKeeperTypeId())) {
                    javaObject.setKeeperTypeId("供应商");
                }
                kingdeeStockList.add(javaObject);
            }

            // 分页失效
            if (kingdeeStockList.size() >= lineLimitNumber) {
                kingdeeStockList = kingdeeStockList.stream().limit(lineLimitNumber).collect(Collectors.toList());
            }
            return kingdeeStockList;
        } catch (Exception e) {
            log.error("接口调用失败: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public TableMetaResp tableMeta(Integer formType) {
        TableMetaResp resp = new TableMetaResp();
        switch (FormTypeEnum.toType(formType)) {
            case DEPT:
                List<Field> fields = new ArrayList<>();
                fields.add(new Field().setFieldName("名称").setFieldId("id1").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("编码").setFieldId("id2").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("描述").setFieldId("id3").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("部门ID").setFieldId("id4").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("父部门ID").setFieldId("id5").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("创建组织").setFieldId("id6").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                fields.add(new Field().setFieldName("使用组织").setFieldId("id7").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                resp.setFields(fields);
                resp.setTableName("BD_Department");
                break;
            case EMPLOYEE:
                List<Field> employeefields = new ArrayList<>();
                employeefields.add(new Field().setFieldName("员工姓名").setFieldId("id1").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("员工编号").setFieldId("id2").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("描述").setFieldId("id3").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("移动电话").setFieldId("id4").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("电子邮箱").setFieldId("id5").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("创建组织").setFieldId("id6").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                employeefields.add(new Field().setFieldName("使用组织").setFieldId("id7").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                resp.setFields(employeefields);
                resp.setTableName("BD_Empinfo");
                break;
            case STOCK:
                List<Field> stockfields = new ArrayList<>();
                stockfields.add(new Field().setFieldName("ID").setFieldId("id1").setFieldType(Constants.biTableText).setIsPrimary(true).setDescription(""));
                stockfields.add(new Field().setFieldName("物料编码").setFieldId("id2").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("物料名称").setFieldId("id3").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("仓库编码").setFieldId("id4").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("仓库名称").setFieldId("id5").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("批号").setFieldId("id6").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("库存主单位").setFieldId("id7").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("库存量").setFieldId("id8").setFieldType(Constants.biTableNum).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("可用量").setFieldId("id9").setFieldType(Constants.biTableNum).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("库存状态").setFieldId("id10").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("库存组织").setFieldId("id11").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("保管者类型").setFieldId("id12").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("保管者编码").setFieldId("id13").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("保管者名称").setFieldId("id14").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("货主类型").setFieldId("id15").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("货主编码").setFieldId("id16").setFieldType(Constants.biTableText).setIsPrimary(false).setDescription(""));
                stockfields.add(new Field().setFieldName("货主名称").setFieldId("id17").setFieldType(Constants.biTableSingleSelect).setIsPrimary(false).setDescription(""));
                resp.setFields(stockfields);
                resp.setTableName("即时库存");
                break;
        }
        return resp;
    }

    @Override
    public RecordResp records(CommonReq req) {

        Long lineLimitNumber = 0L;
        log.info("req params: {}", req.getParams());
        BitCommonParam params = JSONUtil.toBean(req.getParams(), BitCommonParam.class);
        KingdeeParam kingdeeParam = JSONUtil.toBean(params.getDatasourceConfig(), KingdeeParam.class);
        BitCommonContext context = JSONUtil.toBean(req.getContext(), BitCommonContext.class);
        String tenantKey = context.getTenantKey();
        log.info("records tenantKey: {}", tenantKey);
        log.info("userTenantKey: {}", context.getUserTenantKey());
        TenantAuth tenantAuth = tenantAuthMapper.selectOne(
                new LambdaQueryWrapper<TenantAuth>().eq(TenantAuth::getTenantKey, context.getUserTenantKey()).last("limit 1"));
        if (tenantAuth != null) {
            lineLimitNumber = tenantAuthService.rowNumberLimit(tenantAuth.getTenantKey());
        } else {
            log.error("根据租户获取不到行数规则");
        }

        // formType = 3 默认只有一个即时库存需要同步
        Integer formType = 3;

        RecordResp recordResp = new RecordResp();
        switch (FormTypeEnum.toType(formType)) {
            case DEPT:
                // 封装字段和文本
                List<KingdeeDept> kingdeeDepts = queryDepartmentList(kingdeeParam, lineLimitNumber);
                List<Record> deptRecords = new ArrayList<>();
                for (int i = 0; i < kingdeeDepts.size(); i++) {
                    Record emptRecord = new Record();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id1", kingdeeDepts.get(i).getName());
                    map.put("id2", kingdeeDepts.get(i).getNumber());
                    map.put("id3", kingdeeDepts.get(i).getDescription());
                    map.put("id4", kingdeeDepts.get(i).getDeptId());
                    map.put("id5", kingdeeDepts.get(i).getParentId());
                    map.put("id6", kingdeeDepts.get(i).getCreateOrgId());
                    map.put("id7", kingdeeDepts.get(i).getUseOrgId());
                    emptRecord.setData(map);
                    emptRecord.setPrimaryID("fid_" + (i + 1));
                    deptRecords.add(emptRecord);
                }
                recordResp.setRecords(deptRecords);
                recordResp.setHasMore(false);
                break;
            case EMPLOYEE:
                // 封装字段和文本
                List<KingdeeEmployee> kingdeeEmployees = queryEmployeeList(kingdeeParam, lineLimitNumber);
                List<Record> employeeRecords = new ArrayList<>();
                for (int i = 0; i < kingdeeEmployees.size(); i++) {
                    Record employeeRecord = new Record();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id1", kingdeeEmployees.get(i).getName());
                    map.put("id2", kingdeeEmployees.get(i).getStaffNumber());
                    map.put("id3", kingdeeEmployees.get(i).getDescription());
                    map.put("id4", kingdeeEmployees.get(i).getMobile());
                    map.put("id5", kingdeeEmployees.get(i).getEmail());
                    map.put("id6", kingdeeEmployees.get(i).getCreateOrgId());
                    map.put("id7", kingdeeEmployees.get(i).getUseOrgId());
                    employeeRecord.setData(map);
                    employeeRecord.setPrimaryID("fid_" + (i + 1));
                    employeeRecords.add(employeeRecord);
                }
                recordResp.setRecords(employeeRecords);
                recordResp.setHasMore(false);
                break;
            case STOCK:
                // 封装字段和文本
                List<KingdeeStock> kingdeeStocks = queryJitInventoryList(kingdeeParam, lineLimitNumber);
                List<Record> stockRecords = new ArrayList<>();
                for (int i = 0; i < kingdeeStocks.size(); i++) {
                    Record employeeRecord = new Record();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id1", kingdeeStocks.get(i).getId());
                    map.put("id2", kingdeeStocks.get(i).getMaterialNumber());
                    map.put("id3", kingdeeStocks.get(i).getMaterialName());
                    map.put("id4", kingdeeStocks.get(i).getStockNumber());
                    map.put("id5", kingdeeStocks.get(i).getStockName());
                    map.put("id6", kingdeeStocks.get(i).getLotNumber());
                    map.put("id7", kingdeeStocks.get(i).getBaseUnitName());
                    map.put("id8", kingdeeStocks.get(i).getQty());
                    map.put("id9", kingdeeStocks.get(i).getAvbqty());
                    map.put("id10", kingdeeStocks.get(i).getStockStatusName());
                    map.put("id11", kingdeeStocks.get(i).getStockOrgNumber());
                    map.put("id12", kingdeeStocks.get(i).getKeeperTypeId());
                    map.put("id13", kingdeeStocks.get(i).getKeeperNumber());
                    map.put("id14", kingdeeStocks.get(i).getKeeperName());
                    map.put("id15", kingdeeStocks.get(i).getOwnerTypeId());
                    map.put("id16", kingdeeStocks.get(i).getOwnerNumber());
                    map.put("id17", kingdeeStocks.get(i).getOwnerName());
                    employeeRecord.setData(map);
                    employeeRecord.setPrimaryID("fid_" + (i + 1));
                    stockRecords.add(employeeRecord);
                }
                recordResp.setRecords(stockRecords);
                recordResp.setHasMore(false);
                break;
        }
        return recordResp;
    }

    @Override
    public List<StockEntity> stockList(CommonReq req) {
        try {
            BitCommonParam params = JSONUtil.toBean(req.getParams(), BitCommonParam.class);
            KingdeeParam kingdeeParam = JSONUtil.toBean(params.getDatasourceConfig(), KingdeeParam.class);
            String context = req.getContext();
            List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
//            String resultString = api.executeBillQueryJson("");
            String paramDetailJson = "{\n" +
                    "    \"data\": {\n" +
                    "        \"FormId\": \"BD_STOCK\",\n" +
                    "        \"FieldKeys\": \"FStockId,FName,FNumber,FCreateOrgId,FUseOrgId\",\n" +
                    "        \"FilterString\": \"" + StringUtil.convertCreateOrgId(kingdeeParam.getOrgId()) + "\",\n" +
                    "        \"OrderString\": \"\",\n" +
                    "        \"TopRowCount\": 0,\n" +
                    "        \"StartRow\": 0,\n" +
                    "        \"Limit\": 2000,\n" +
                    "        \"SubSystemId\": \"\"\n" +
                    "    }\n" +
                    "}";
            String resultString = HttpRequest.post(StringUtil.convertUrl(kingdeeParam.getKingdeeUrl()) + Constants.KINGDEE_QUERY)
                    .body(paramDetailJson)
                    .cookie(httpCookies)
                    .execute().body();

//            log.info("金蝶仓库列表查询参数: {}", paramDetailJson);
            log.info("金蝶仓库列表查询接口: {}", resultString);
            JSONArray resultArray = (JSONArray) JSONObject.parse(resultString);
            if (resultArray == null || resultArray.isEmpty()) {
                return Collections.emptyList();
            }
            List<StockEntity> stockEntities = new ArrayList<>();
            for (int i = 0; i < resultArray.size(); i++) {
                Integer stockId = resultArray.getJSONArray(i).getInteger(0);
                String name = resultArray.getJSONArray(i).getString(1);
                String stockNumber = resultArray.getJSONArray(i).getString(2);
                String createOrgId = resultArray.getJSONArray(i).getString(3);
                String useOrgId = resultArray.getJSONArray(i).getString(4);
                StockEntity stockEntity = new StockEntity();
                stockEntity.setStockId(stockId);
                stockEntity.setName(name);
                stockEntity.setNumber(stockNumber);
                stockEntity.setCreateOrgId(createOrgId);
                stockEntity.setUseOrgId(useOrgId);
                stockEntities.add(stockEntity);
            }
            return stockEntities;
        } catch (Exception e) {
            log.error("接口调用失败: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public R<RecordResp> kingdeeLogin(CommonReq req) {
        try {
            BitCommonParam params = JSONUtil.toBean(req.getParams(), BitCommonParam.class);
            KingdeeParam kingdeeParam = JSONUtil.toBean(params.getDatasourceConfig(), KingdeeParam.class);
            String context = req.getContext();
            String login = SignUtil.login(kingdeeParam);
            log.info("login: {}", login);

            if (login.contains("当前尝试登录的数据中心无法获取到，请重新检查，谢谢。")) {
                return R.fail("当前尝试登录的数据中心无法获取到，请重新检查，谢谢", null);
            } else if (login.contains("Error request, response status: 502")) {
                return R.fail("请检查金蝶URL参数", null);
            } else if (login.contains("<head><title>文档已移动</title></head>")) {
                return R.fail("请检查金蝶URL参数", null);
            } else if (login.contains("用户名或密码错误！")) {
                log.error("kingdeeParam: {}", kingdeeParam);
                return R.fail("用户名或密码错误", null);
            } else if (login.contains("\"LoginResultType\":1") && login.contains("\"IsSuccessByAPI\":true")) {
                return R.ok();
            }

        } catch (Exception e) {
            log.error("金蝶登录异常: ", e);
            return R.fail("登录失败", null);
        }
        return R.fail("未知错误，请联系管理员", null);

//        log.info("stockEntityList size: {}", stockEntityList.size());
    }

    @Override
    public List<OrgEntity> orgList(CommonReq req) {
        try {
            BitCommonParam params = JSONUtil.toBean(req.getParams(), BitCommonParam.class);
            KingdeeParam kingdeeParam = JSONUtil.toBean(params.getDatasourceConfig(), KingdeeParam.class);
            String context = req.getContext();
            List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
//            String resultString = api.executeBillQueryJson("");
            String paramDetailJson = "{\n" +
                    "    \"data\": {\n" +
                    "        \"FormId\": \"ORG_Organizations\",\n" +
                    "        \"FieldKeys\": \"FNAME,FORGID,FNUMBER\",\n" +
                    "        \"FilterString\": \"\",\n" +
                    "        \"OrderString\": \"\",\n" +
                    "        \"TopRowCount\": 0,\n" +
                    "        \"StartRow\": 0,\n" +
                    "        \"Limit\": 2000,\n" +
                    "        \"SubSystemId\": \"\"\n" +
                    "    }\n" +
                    "}";
            String resultString = HttpRequest.post(StringUtil.convertUrl(kingdeeParam.getKingdeeUrl()) + Constants.KINGDEE_QUERY)
                    .body(paramDetailJson)
                    .cookie(httpCookies)
                    .execute().body();

//            log.info("金蝶组织列表查询参数: {}", paramDetailJson);
            log.info("金蝶组织列表查询接口: {}", resultString);
            JSONArray resultArray = (JSONArray) JSONObject.parse(resultString);
            if (resultArray == null || resultArray.getJSONArray(0) == null) {
                return Collections.emptyList();
            }
            List<OrgEntity> orgEntities = new ArrayList<>();
            for (int i = 0; i < resultArray.size(); i++) {
                String name = resultArray.getJSONArray(i).getString(0);
                Integer orgId = resultArray.getJSONArray(i).getInteger(1);
                String number = resultArray.getJSONArray(i).getString(2);
                OrgEntity orgEntity = new OrgEntity();
                orgEntity.setName(name);
                orgEntity.setOrgId(orgId);
                orgEntity.setNumber(number);
                orgEntities.add(orgEntity);
            }
            return orgEntities;
        } catch (Exception e) {
            log.error("接口调用失败: ", e);
        }
        return Collections.emptyList();
    }

}
