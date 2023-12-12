package com.lundong.plug.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 14:15
 */
@Getter
@AllArgsConstructor
public enum FormTypeEnum {
    DEPT		    (1, "部门"),
    EMPLOYEE	    (2, "员工"),
    STOCK	        (3, "即时库存");

    private Integer code;
    private String desc;

    public static FormTypeEnum getType(Integer code) {
        for (FormTypeEnum enums : FormTypeEnum.values()) {
            if (Objects.equals(enums.getCode(), code)) {
                return enums;
            }
        }
        return null;
    }

    public static FormTypeEnum toType(int code) {
        return Stream.of(FormTypeEnum.values()) .filter(p -> p.getCode() == code).findAny().orElse(null);
    }
}