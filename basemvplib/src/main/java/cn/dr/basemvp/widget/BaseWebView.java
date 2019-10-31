package cn.dr.basemvp.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;

import cn.dr.basemvp.R;
import cn.dr.basemvp.base.BaseActivity;
import cn.dr.basemvp.utils.CommonUtils;
import cn.dr.basemvp.utils.StringUtils;
import cn.dr.basemvp.utils.ToastUtils;

/**
 * Created by dingrui 2019/10/31
 */

public class BaseWebView extends BaseActivity {

    private String mTitle;
    private String mUrl;
    private AgentWeb mAgentWeb;
    private ImageView mLeft;
    private TextView mTvTitle;
    private LinearLayout mContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void getIntent(Intent intent) {
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLeft = findViewById(R.id.left);
        mTvTitle = findViewById(R.id.tv_title);
        mContainer = findViewById(R.id.container);
    }

    @Override
    protected void initListener() {
        mTvTitle.setText(mTitle == null ? "" : mTitle);
        mLeft.setOnClickListener(v -> {
            if (!mAgentWeb.back()) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog("加载中...");
        new Handler().postDelayed(() -> {
            hideLoadingDialog();
            //清空缓存
            if (!StringUtils.isEmpty(mUrl)) {
                AgentWebConfig.clearDiskCache(mActivity);
                mAgentWeb = AgentWeb.with(mActivity)
                        .setAgentWebParent((LinearLayout) mContainer, new LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator(CommonUtils.getColor(R.color.themeColor), 0)
                        .createAgentWeb()
                        .ready()
                        .go(mUrl);
            } else {
                ToastUtils.showShort("您访问的地址不存在~");
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            finish();
        }
    }
}
