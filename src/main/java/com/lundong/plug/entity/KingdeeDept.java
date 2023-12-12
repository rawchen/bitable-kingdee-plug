package com.lundong.plug.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:35
 */
@Data
public class KingdeeDept {

    @JSONField(name = "FNAME")
    private String name;

    @JSONField(name = "FNUMBER")
    private String number;

    @JSONField(name = "FDescription")
    private String description;

    /**
     * 实体主键
     */
    @JSONField(name = "FDEPTID")
    private String deptId;

    @JSONField(name = "FPARENTID")
    private String parentId;

    @JSONField(name = "FCreateOrgId")
    private String createOrgId;

    @JSONField(name = "FUseOrgId")
    private String useOrgId;

}

