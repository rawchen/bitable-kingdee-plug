package com.lundong.plug;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import com.lundong.plug.config.Constants;
import com.lundong.plug.entity.KingdeeStock;
import com.lundong.plug.entity.param.KingdeeParam;
import com.lundong.plug.entity.result.TableMetaResp;
import com.lundong.plug.service.KingdeeService;
import com.lundong.plug.service.TenantAuthService;
import com.lundong.plug.util.SignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
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

    @Test
    void test07() throws UnsupportedEncodingException {
        String content = "888888";
        String key = DigestUtil.md5Hex(Constants.SECRET_KEY);
        System.out.println(key);

        // 构建
        AES aes = SecureUtil.aes(key.getBytes());

        // 加密
        byte[] encrypt = aes.encrypt(content);
        // 解密
        byte[] decrypt = aes.decrypt(encrypt);

        // 加密为16进制表示
        String res = aes.encryptHex(content);
        System.out.println(res);

        // 解密为字符串
        String decryptStr = aes.decryptStr(res, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }

    @Test
    void test08() {
//        System.out.println("123"));
//        System.out.println(Base64.decodeStr("e1wiZGF0YXNvdXJjZUNvbmZpZ1wiOntcIm9yZ0lkXCI6XCIxMDAwMzhcIixcInN0b2NrSWRzXCI6XCJcIixcImtpbmdkZWVVcmxcIjpcImh0dHA6Ly8xOTIuMTY4LjExMC4yMjNcIiwgXCJhY2N0SWRcIjpcIjY0MjQyNzI3MGU5Zjg3XCIsXCJ1c2VybmFtZVwiOlwiZGVtb1wiLFwicGFzc3dvcmRcIjpcIjg4ODg4OFwifSwgXCJ0cmFuc2FjdGlvbklEXCI6XCIxMjMxM1wiLFwicGFnZVRva2VuXCI6XCJcIixcIm1heFBhZ2VTaXplXCI6XCJcIn0="));

        // 前端传进来base64(json)
        String text = "{\"datasourceConfig\":{\"orgId\":\"100038\",\"stockIds\":\"\",\"kingdeeUrl\":\"http://192.168.110.223\", \"acctId\":\"642427270e9f87\",\"username\":\"demo\",\"password\":\"888888\"}, \"transactionID\":\"12313\",\"pageToken\":\"\",\"maxPageSize\":\"\"}";
        String key = DigestUtil.md5Hex(Constants.SECRET_KEY);
        System.out.println(key);
        DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        String result = des.encryptHex(text, StandardCharsets.UTF_8);
        System.out.println("加密后的输出：" + result);

        System.out.println(SignUtil.decrypt(result));
    }
}
