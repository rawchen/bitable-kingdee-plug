package com.lundong.plug.config;

import com.lundong.plug.entity.FormTypeDict;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shuangquan.chen
 * @date 2023-11-22 09:57
 */
public class Constants {

    public final static Integer biTableText             = 1;    //多行文本
    public final static Integer biTableNum              = 2;    //数字
    public final static Integer biTableSingleSelect     = 3;    //单选
    public final static Integer biTableMultipleSelect   = 4;    //多选
    public final static Integer biTableDate             = 5;    //日期
    public final static Integer biTableBarcode          = 6;    //条码
    public final static Integer biTableCheckBox         = 7;    //复选框
    public final static Integer biTableCurrency         = 8;    //货币
    public final static Integer biTablePhone            = 9;    //电话号码
    public final static Integer biTableLink             = 10;   //超链接

    public final static String SECRET_KEY = "ldkj6666";

    public final static String KINGDEE_URL = "http://192.168.110.223/k3cloud";

    // 登录 ValidateUser
    public final static String KINGDEE_LOGIN = "/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";

    // 增、改 Save
    public final static String KINGDEE_SAVE = "/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc";

    // 删 Delete
    public final static String KINGDEE_DELETE = "/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Delete.common.kdsvc";

    // 单据查询 View
    public final static String KINGDEE_VIEW = "/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc";

    // 列表查询 ExecuteBillQuery
    public final static String KINGDEE_QUERY = "/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc";

    public final static String KINGDEE_INVENTORY = "/Kingdee.K3.SCM.WebApi.ServicesStub.InventoryQueryService.GetInventoryData.common.kdsvc";

    public static List<FormTypeDict> formTypeDictList = new ArrayList<>();

}
