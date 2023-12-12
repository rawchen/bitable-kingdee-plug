package com.lundong.plug;

import com.lundong.plug.entity.*;
import com.lundong.plug.entity.param.KingdeeParam;
import com.lundong.plug.entity.result.TableMetaResp;
import com.lundong.plug.service.KingdeeService;
import com.lundong.plug.service.TenantAuthService;
import com.lundong.plug.util.SignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpCookie;
import java.util.List;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 10:25
 */
@SpringBootTest
public class AppTest {

    @Autowired
    KingdeeService kingdeeService;

    @Autowired
    TenantAuthService tenantAuthService;

    @Test
    void test01() {
//        List<HttpCookie> httpCookies = SignUtil.loginCookies();
//        for (HttpCookie httpCookie : httpCookies) {
//            System.out.println(httpCookie);
//        }
    }

//    @Test
//    void test02() {
//        List<KingdeeDept> kingdeeDepts = kingdeeService.queryDepartmentList();
//        System.out.println(kingdeeDepts);
//    }

    @Test
    void test03() {
        TableMetaResp tableMetaResp = kingdeeService.tableMeta(1);
        System.out.println(tableMetaResp);
    }

    @Test
    void test04() {
        KingdeeParam kingdeeParam = new KingdeeParam();
        kingdeeParam.setKingdeeUrl("http://192.168.110.223/k3cloud/");
        kingdeeParam.setAcctId("642427270e9f87");
        kingdeeParam.setUsername("demo");
        kingdeeParam.setPassword("888888");
        List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
        for (HttpCookie httpCookie : httpCookies) {
            System.out.println(httpCookie);
        }
    }

    @Test
    void test05() {
        KingdeeParam kingdeeParam = new KingdeeParam();
        kingdeeParam.setKingdeeUrl("http://192.168.110.223/k3cloud/");
        kingdeeParam.setAcctId("642427270e9f87");
        kingdeeParam.setUsername("demo");
        kingdeeParam.setPassword("888888");
        List<HttpCookie> httpCookies = SignUtil.loginCookies(kingdeeParam);
        for (HttpCookie httpCookie : httpCookies) {
            System.out.println(httpCookie);
        }

        List<KingdeeStock> kingdeeStocks = kingdeeService.queryJitInventoryList(kingdeeParam, 100L);
        for (KingdeeStock kingdeeStock : kingdeeStocks) {
            System.out.println(kingdeeStock);
        }
        System.out.println(kingdeeStocks.size());
    }

    @Test
    void test06() {
        Long result01 = tenantAuthService.rowNumberLimit("ou_1");
        System.out.println(result01);
        Long result02 = tenantAuthService.rowNumberLimit("ou_2");
        System.out.println(result02);
        Long result03 = tenantAuthService.rowNumberLimit("ou_3");
        System.out.println(result03);
        Long result04 = tenantAuthService.rowNumberLimit("ou_4");
        System.out.println(result04);
        Long result05 = tenantAuthService.rowNumberLimit("ou_5");
        System.out.println(result05);
        Long result06 = tenantAuthService.rowNumberLimit("ou_6");
        System.out.println(result06);

    }
}
