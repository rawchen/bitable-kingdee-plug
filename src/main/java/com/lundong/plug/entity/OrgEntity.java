package com.lundong.plug.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-28 11:25
 */
@Data
public class OrgEntity {

    @JSONField(name = "FORGID")
    private Integer orgId;

    @JSONField(name = "FNAME")
    private String name;

    @JSONField(name = "FNUMBER")
    private String number;

}
