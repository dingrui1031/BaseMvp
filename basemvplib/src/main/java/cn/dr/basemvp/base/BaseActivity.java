package cn.dr.basemvp.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dr.basemvp.update.AppUpdateUtils;
import cn.dr.basemvp.utils.DialogUtils;

/**
 * @desc 基类 BaseMvpActivity
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder unBinder;
    private Dialog mDialog;
    public BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        Intent intent = getIntent();
        if (intent != null)
            getIntent(intent);
        unBinder = ButterKnife.bind(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册eventBus
        }
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUpdateUtils.getInstance().update(this, false, false);
    }

    /**
     * 是否使用eventBus
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (useEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);//注销eventBus
            }
        }

    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog(title);
    }

    /**
     * 创建LoadingDialog
     */
    private void createLoadingDialog(String title) {
        mDialog = DialogUtils.showLoadingDialog(this, title);
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        DialogUtils.closeDialog(mDialog);
    }


    /**
     * 获取布局 Id
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 获取 Intent 数据
     **/
    protected abstract void getIntent(Intent intent);

    /**
     * 初始化View的代码写在这个方法中
     */
    protected abstract void initView();

    /**
     * 初始化监听器的代码写在这个方法中
     */
    protected abstract void initListener();

    /**
     * 初始数据的代码写在这个方法中，用于从服务器获取数据
     */
    protected abstract void initData();


}
