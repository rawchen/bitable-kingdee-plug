package com.lundong.plug.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:35
 */
@Data
public class KingdeeStock {



    @JSONField(name = "FID")
    private String id;

    @JSONField(name = "FMATERIALNUMBER")
    private String materialNumber;

    @JSONField(name = "FMATERIALNAME")
    private String materialName;

    @JSONField(name = "FSTOCKNUMBER")
    private String stockNumber;

    @JSONField(name = "FSTOCKNAME")
    private String stockName;

    @JSONField(name = "FLOTNUMBER")
    private String lotNumber;

    @JSONField(name = "FBASEUNITNAME")
    private String baseUnitName;

    @JSONField(name = "FQTY")
    private Integer qty;

    @JSONField(name = "FAVBQTY")
    private Integer avbqty;

    @JSONField(name = "FSTOCKSTATUSNAME")
    private String stockStatusName;

    @JSONField(name = "FSTOCKORGNUMBER")
    private String stockOrgNumber;

    @JSONField(name = "FKEEPERTYPEID")
    private String keeperTypeId;

    @JSONField(name = "FKEEPERNUMBER")
    private String keeperNumber;

    @JSONField(name = "FKEEPERNAME")
    private String keeperName;

    @JSONField(name = "FOWNERTYPEID")
    private String ownerTypeId;

    @JSONField(name = "FOWNERNUMBER")
    private String ownerNumber;

    @JSONField(name = "FOWNERNAME")
    private String ownerName;

}

