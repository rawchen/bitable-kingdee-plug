package com.lundong.plug.service;

import com.lundong.plug.entity.*;
import com.lundong.plug.entity.param.CommonReq;
import com.lundong.plug.entity.param.KingdeeParam;
import com.lundong.plug.entity.result.R;
import com.lundong.plug.entity.result.RecordResp;
import com.lundong.plug.entity.result.TableMetaResp;

import java.util.List;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:35
 */
public interface KingdeeService {

    /**
     * 获取Kingdee部门列表
     *
     * @return
     */
    List<KingdeeDept> queryDepartmentList(KingdeeParam kingdeeParam, Long lineLimitNumber);

    /**
     * 获取Kingdee员工列表
     *
     * @return
     */
    List<KingdeeEmployee> queryEmployeeList(KingdeeParam kingdeeParam, Long lineLimitNumber);

    /**
     * 获取Kingdee即时库存列表
     *
     * @param kingdeeParam
     * @return
     */
    List<KingdeeStock> queryJitInventoryList(KingdeeParam kingdeeParam, Long lineLimitNumber);

    /**
     * 结构接口
     *
     * @param formType
     * @return
     */
    TableMetaResp tableMeta(Integer formType);

    /**
     * 记录接口
     *
     * @param req
     * @return
     */
    RecordResp records(CommonReq req);

    /**
     * 仓库列表
     *
     * @param req
     * @return
     */
    List<StockEntity> stockList(CommonReq req);

    /**
     * 金蝶登录
     *
     * @param req
     * @return
     */
    R<RecordResp> kingdeeLogin(CommonReq req);

    /**
     * 组织列表
     *
     * @param req
     * @return
     */
    List<OrgEntity> orgList(CommonReq req);
}
