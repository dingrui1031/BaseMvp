package cn.dr.basemvp.update;

import android.app.Activity;
import android.util.Log;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.dr.basemvp.BuildConfig;
import cn.dr.basemvp.R;
import cn.dr.basemvp.app.AppConfig;
import cn.dr.basemvp.utils.CommonUtils;
import cn.dr.basemvp.utils.ToastUtils;


/**
 * dr
 * 2019/5/24
 */

public class AppUpdateUtils {

    private static String isUpdate = "No";
    public static AppUpdateUtils instance = null;

    public static AppUpdateUtils getInstance() {
        if (instance == null) {
            instance = new AppUpdateUtils();
        }
        return instance;
    }

    public void update(final Activity activity, final boolean isShowTip) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone_type", "0");//0 安卓 1 ios
        params.put("user_type", "1");//0 患者端 1 医生端
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(activity)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(AppConfig.BASE_URL + AppConfig.VERSION_UPDATE_URL)
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(true)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
//                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                .setTopPic(R.drawable.update_top_pic)
                //为按钮，进度条设置颜色。
                .setThemeColor(CommonUtils.getColor(R.color.themeColor))
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject info = jsonObject.optJSONObject("data");
                            Log.e("info", info.toString());
                            if (!info.optString("code").equals("")) {
                                int code = Integer.parseInt(info.optString("code"));
                                int versionCode = com.vector.update_app.utils.AppUpdateUtils.getVersionCode(activity);
                                if (code > versionCode) {
                                    isUpdate = "Yes";
                                } else {
                                    isUpdate = "No";
                                }
                                updateAppBean
                                        //（必须）是否更新Yes,No
                                        .setUpdate(isUpdate)
                                        //（必须）新版本号，
                                        .setNewVersion(info.optString("name"))
                                        //（必须）下载地址 返回的是id
                                        .setApkFileUrl(info.optString("file"))
                                        //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
                                        .setUpdateLog(info.optString("content"))
                                        //大小，不设置不显示大小，可以不设置
//                                    .setTargetSize(jsonObject.optString("target_size"))
                                        //是否强制更新，可以不设置
                                        .setConstraint(true);
                                //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md51"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    protected void noNewApp(String error) {
                        super.noNewApp(error);
                        if (isShowTip) {
                            ToastUtils.showShort("当前版本是" + BuildConfig.VERSION_NAME + ",不需要更新！");
                        } else {
                            Log.e(activity + "", "没有新版本");
                        }
                    }

                });
    }

}
