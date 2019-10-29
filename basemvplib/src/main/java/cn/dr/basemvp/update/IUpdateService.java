package cn.dr.basemvp.update;

import java.util.Map;

import cn.dr.basemvp.net.BaseHttpResult;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * dr
 * 2019/5/24
 */

public interface IUpdateService {

    /**
     * 更新
     *
     * @return
     */
    @POST("sj/doctor/versions")
    @FormUrlEncoded
    Observable<String> updatePost(@FieldMap Map<String, String> params);

    /**
     * 更新
     *
     * @return
     */
    @GET("sj/doctor/versions")
    Observable<String> updateGet(@QueryMap Map<String, String> params);
}
