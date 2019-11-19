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
    private ImageView mIvBack;
    private TextView mTvTitle;
    private LinearLayout mContainer;
    private boolean mAgent_back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void getIntent(Intent intent) {
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
        mAgent_back = intent.getBooleanExtra("agent_back", true);//是否再web返回上一页之后再结束
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mContainer = findViewById(R.id.container);
    }

    @Override
    protected void initListener() {
        mTvTitle.setText(mTitle == null ? "" : mTitle);
        mIvBack.setOnClickListener(v -> {
            if (mAgent_back) {
                if (mAgentWeb != null &&!mAgentWeb.back()) {
                    finish();
                }else {
                    finish();
                }
            } else {
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
        if (mAgent_back) {
            if (mAgentWeb != null &&!mAgentWeb.back()) {
                finish();
            }else {
                finish();
            }
        } else {
            finish();
        }
    }
}
