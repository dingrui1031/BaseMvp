package cn.dr.basemvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.dr.basemvp.R;
import cn.dr.basemvp.mvp.BasePresenter;
import cn.dr.basemvp.mvp.IView;

/**
 * @desc 基类 BaseMvpActivity
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements IView {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    public void showLoading() {
        showLoadingDialog("加载中");
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }


    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
