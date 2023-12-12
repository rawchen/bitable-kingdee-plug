package com.lundong.plug.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-28 11:25
 */
@Data
public class StockEntity {

    @JSONField(name = "FStockId")
    private Integer stockId;

    @JSONField(name = "FName")
    private String name;

    @JSONField(name = "FNumber")
    private String number;

    @JSONField(name = "FCreateOrgId")
    private String createOrgId;

    @JSONField(name = "FUseOrgId")
    private String useOrgId;

}
