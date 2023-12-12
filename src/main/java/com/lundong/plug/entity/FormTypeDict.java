package com.lundong.plug.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author shuangquan.chen
 * @date 2023-11-24 15:27
 */
@Data
@Accessors(chain = true)
public class FormTypeDict {

    private String formName;

    private Integer formType;
}
