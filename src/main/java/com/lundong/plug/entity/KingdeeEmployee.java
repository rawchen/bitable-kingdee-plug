package com.lundong.plug.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:35
 */
@Data
public class KingdeeEmployee {

    @JSONField(name = "FNAME")
    private String name;

    @JSONField(name = "FStaffNumber")
    private String staffNumber;

    @JSONField(name = "FDescription")
    private String description;

    @JSONField(name = "FMobile")
    private String mobile;

    @JSONField(name = "FEmail")
    private String email;

    @JSONField(name = "FCreateOrgId")
    private String createOrgId;

    @JSONField(name = "FUseOrgId")
    private String useOrgId;

}

