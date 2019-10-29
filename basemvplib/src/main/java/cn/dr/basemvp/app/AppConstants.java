package cn.dr.basemvp.app;

import java.io.File;

/**
 * @desc app 常量
 */
public class AppConstants {

    /**
     * Path
     */
    public static final String PATH_DATA = BaseApplication.getContext().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String BUGLY_ID = "16e54f8921";

    //odoo提供的字符串，不参与传输，只用作计算签名
    public static final String TOKEN = "f7cb8640a53d6bb118ca32120bf40112";
    //APP提供的字符串，不参与传输，只用作计算签名
    public static final String APPID = "EF91CE6002D7679412633CF913257598";

}
