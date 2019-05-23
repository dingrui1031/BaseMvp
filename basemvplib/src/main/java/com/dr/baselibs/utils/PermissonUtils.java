package cn.dr.mymvplibrary.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
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
            boolean hasPerm = (PermissionChecker.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }


    public interface OnPermissionAgreeListener {
        //同意
        void onAgree();
    }

//    public void setOnPermissionAgreeListener(OnPermissionAgreeListener onPermissionAgreeListener) {
//        this.mOnPermissionAgreeListener = onPermissionAgreeListener;
//    }

    //申请权限
    public static void requestPermission(@NonNull final AppCompatActivity activity, final String tips, @NonNull String... perms) {
        new RxPermissions(activity)
                .request(perms)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 用户已经同意该权限
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            // 去设置中心设置
                            showAppSettingDialog(activity, tips);
                        }
                    }
                });
    }

    //申请权限
    public static void requestPermission(@NonNull final AppCompatActivity activity, final String tips, final OnPermissionAgreeListener onPermissionAgreeListener, @NonNull String... perms) {
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
                            showAppSettingDialog(activity, tips);
                        }
                    }
                });
    }

    //显示拒绝后的dialog
    public static void showAppSettingDialog(final AppCompatActivity activity, String tips) {
        new MaterialDialog.Builder(activity)
                .title("提示")
                .content(tips)
                .negativeText("取消")
                .canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //设置通知栏权限
                        goToSet(activity);
                    }
                })
                .show();
    }

    //引导用户去设置通知
    public static void goToSet(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
            return;
        }
    }
}
