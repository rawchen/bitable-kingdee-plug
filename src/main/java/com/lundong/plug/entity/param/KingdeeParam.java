package com.lundong.plug.entity.param;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 17:39
 */
@Data
public class KingdeeParam {

    // 自定义过滤
//    private String filterString;

    private String orgId;

    private String number;

    private String kingdeeUrl;

    private String acctId;

    private String username;

    private String password;

    @Alias("TransactionID")
    private String transactionID;

    @Alias("pageToken")
    private String pageToken;

    @Alias("maxPageSize")
    private String maxPageSize;

}
