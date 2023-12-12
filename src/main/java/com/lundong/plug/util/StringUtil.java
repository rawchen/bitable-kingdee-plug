package com.lundong.plug.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.lundong.plug.entity.StockEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author shuangquan.chen
 * @date 2023-11-24 14:40
 */
@Slf4j
public class StringUtil {

    public static String convertUrl(String url) {
        if (StrUtil.isEmpty(url)) {
            return "";
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url + "/k3cloud";
    }

    public static String convertFilterString(String str) {
        if (StrUtil.isEmpty(str)) {
            return "[]";
        } else {
            return str;
        }
    }

    public static String convertCreateOrgId(String s) {
        if (StrUtil.isEmpty(s)) {
            return "[]";
        } else {
            return "FcreateOrgId='" + s + "'";
        }
    }

    public static String convertStockIds(List<StockEntity> stockEntityList) {
        if (ArrayUtil.isEmpty(stockEntityList)) {
            return "";
        } else {
            String stockNumbers = "";
            for (int i = 0; i < stockEntityList.size(); i++) {
//                System.out.println(resultArray.getJSONArray(i).getString(2));
                stockNumbers += stockEntityList.get(i).getNumber();
                if (i < stockEntityList.size() - 1) {
                    stockNumbers += ",";
                }
            }
            log.info("stockNumbers: {}", stockNumbers);
            return stockNumbers;
        }
    }
}
