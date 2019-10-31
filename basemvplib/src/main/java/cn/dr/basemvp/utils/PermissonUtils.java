package cn.dr.basemvp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by Ray
 * 权限管理类
 */

public class PermissonUtils {

    //检查权限
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w("PermissonUtils", "hasPermissions: API version < M, returning true by default");

            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (PermissionChecker.checkSelfPermission(context, perm) == PermissionChecker.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    public interface OnPermissionAgreeListener {
        //同意
        void onAgree();
        //拒绝
        void onRefuse();
    }

    //申请权限
    public static void requestPermission(@NonNull final Activity activity, final String tips, final OnPermissionAgreeListener onPermissionAgreeListener, @NonNull String... perms) {
        new RxPermissions(activity)
                .request(perms)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 用户已经同意该权限
                            onPermissionAgreeListener.onAgree();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            // 去设置中心设置
                            showAppSettingDialog(activity, tips,onPermissionAgreeListener);
                        }
                    }
                });
    }

    //显示拒绝后的dialog
    public static void showAppSettingDialog(final Activity activity, String tips,final OnPermissionAgreeListener onPermissionAgreeListener) {
        new MaterialDialog.Builder(activity)
                .title("提示")
                .content(tips)
                .negativeText("取消")
                .canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //拒绝
                        onPermissionAgreeListener.onRefuse();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //设置通知栏权限
                        AppUtils.applicationDetailsSettings(activity);
                    }
                })
                .show();
    }

}
