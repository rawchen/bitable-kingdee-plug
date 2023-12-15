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
//        String text = "{\"datasourceConfig\":\"{\\\"kingdeeUrl\\\":\\\"http://raw.natapp1.cc\\\",\\\"acctId\\\":\\\"642427270e9f87\\\",\\\"username\\\":\\\"demo\\\",\\\"password\\\":\\\"888888\\\",\\\"orgId\\\":\\\"\\\",\\\"number\\\":\\\"\\\",\\\"transactionID\\\":\\\"\\\",\\\"pageToken\\\":\\\"\\\",\\\"maxPageSize\\\":\\\"\\\"}\"}";
//        DES des = new DES(Mode.ECB, Padding.PKCS5Padding, Constants.SECRET_KEY.getBytes());
//        String result = des.encryptHex(text, StandardCharsets.UTF_8);
//        System.out.println("加密后的输出：" + result);
//        System.out.println(SignUtil.decrypt(result));
        String text = "{\"datasourceConfig\":\"{\\\"kingdeeUrl\\\":\\\"http://k3cloud.natapp1.cc\\\",\\\"acctId\\\":\\\"642427270e9f87\\\",\\\"username\\\":\\\"demo\\\",\\\"password\\\":\\\"888888\\\",\\\"orgId\\\":\\\"\\\",\\\"number\\\":\\\"\\\",\\\"transactionID\\\":\\\"\\\",\\\"pageToken\\\":\\\"\\\",\\\"maxPageSize\\\":\\\"\\\"}\"}";
        DES des = new DES(Mode.ECB, Padding.PKCS5Padding, Constants.SECRET_KEY.getBytes());
        String result = des.encryptHex(text, StandardCharsets.UTF_8);
        System.out.println("加密后的输出：" + result);
        System.out.println(SignUtil.decrypt(result));
    }

    @Test
    void test09() throws Exception {
        String a = "27345AC4D82E0F2C72C18CEC9A8E196DAAF50FEFA6B9FB8D0E38DF9079A41864CE7F7E76A01D74F59A4AD9113B77E960B436618E075ABC8CD34256B25F427AA97E8CC274381BF89B74022B4D21E1762E5FE24F7C18B06FA8405078F30AE353857A71E49786E2F33AF78E68B0A9A4C39F6B8C9EF6B1EFC3E45DCCF4094A81EFF154AC657273C1E43AA0A4A2F4403E75AF2D783AABBECAD3F367BE353549AFA7582A67701BDCB4BFA567BE353549AFA758B46D857B94EC17CDB0C740A2619645CD484CB9B4171FDD1BFECA4347F29174DFE2FF8DDDBB92551B358DD5804EB9A7CCDE8AD2CC35CEA3B878A42694020D011F";
        String decrypt = SignUtil.decrypt(a);
        System.out.println(decrypt);
    }
}
