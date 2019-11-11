package cn.dr.basemvp.widget.pdf;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.jaeger.library.StatusBarUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import butterknife.BindView;
import cn.dr.basemvp.R;
import cn.dr.basemvp.R2;
import cn.dr.basemvp.base.BaseActivity;
import cn.dr.basemvp.utils.CommonUtils;
import cn.dr.basemvp.utils.PermissonUtils;

/**
 * Created by dingrui 2019/11/4
 */
public class PdfActivity extends BaseActivity {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.pdfView)
    PDFView pdfView;
    private String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void getIntent(Intent intent) {
        mUrl = intent.getStringExtra("url");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtil.setColor(mActivity, CommonUtils.getColor(R.color.white_ff), 0);
        StatusBarUtil.setLightMode(mActivity);
        tvTitle.setText("报告");
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        if (mUrl != null) {
            if (PermissonUtils.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                downFile();
            } else {
                PermissonUtils.requestPermission(this, "加载PDF需要使用读写存储的权限", new PermissonUtils.OnPermissionAgreeListener() {
                    @Override
                    public void onAgree() {
                        downFile();
                    }

                    @Override
                    public void onRefuse() {

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

    }

    private void downFile() {
        FileDownloader.setup(mActivity);
        FileDownloader.getImpl().create(mUrl)
                .setPath(getExternalFilesDir("base360_pdf_report") + mUrl + ".pdf")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        String targetFilePath = task.getTargetFilePath();
                        File file = new File(targetFilePath);
                        pdfView.fromFile(file)
                                .enableSwipe(true)
                                .enableDoubletap(true)
                                .defaultPage(1)
                                .load();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().clearAllTaskData();
    }

}
