package cn.dr.basemvp.update;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.vector.update_app.HttpManager;

import java.io.File;
import java.util.Map;

import cn.dr.basemvp.net.BaseRetrofit;
import cn.dr.basemvp.net.download.DownLoadManager;
import cn.dr.basemvp.net.download.ProgressCallBack;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * dr
 * 2019/5/24
 */
public class UpdateHttpUtil implements HttpManager {

    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        BaseRetrofit.getRetrofit().create(IUpdateService.class).updateGet(params)
                .subscribeOn(Schedulers.io())//指定网络请求在io后台线程中进行
                .observeOn(AndroidSchedulers.mainThread())//指定observer回调在UI主线程中进行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String bean) {
                        callBack.onResponse(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.onError("异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        BaseRetrofit.getRetrofit().create(IUpdateService.class).updatePost(params)
                .subscribeOn(Schedulers.io())//指定网络请求在io后台线程中进行
                .observeOn(AndroidSchedulers.mainThread())//指定observer回调在UI主线程中进行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String bean) {
                        callBack.onResponse(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.onError("异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    @Override
    public void download(@NonNull String url, @NonNull final String path, @NonNull String fileName, @NonNull final FileCallback callback) {
        DownLoadManager.getInstance().load(url, new ProgressCallBack(path, fileName) {

            @Override
            public void onSuccess(Object o) {
                callback.onResponse(new File(path));
            }

            @Override
            public void progress(long progress, long total) {
                callback.onProgress((float) progress / total, total);
            }

            @Override
            public void onStart() {
                super.onStart();
                callback.onBefore();
            }

            @Override
            public void onError(Throwable e) {
                callback.onError("异常");
            }
        });
    }
}
