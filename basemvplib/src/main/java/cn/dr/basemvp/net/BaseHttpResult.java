package cn.dr.basemvp.net;

import java.io.Serializable;

/**
 * @desc 抽取的一个基类的bean, 直接在泛型中传data就行
 */
public class BaseHttpResult<T> implements Serializable {
    private static final long serialVersionUID = 2690553609250007325L;
    public static final int SUCCESS_CODE = 200;

    private int http_code;
    private String message;
    private T data;

    public int getHttpcode() {
        return http_code;
    }

    public void setHttpcode(int httpcode) {
        this.http_code = httpcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 正常返回
     *
     * @return
     */
    public boolean isSuccessFul() {
        return getHttpcode() == SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "httpcode=" + http_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


    /** test**/
//    private boolean error;
//
//    private T results;
//
//    public boolean isError() {
//        return error;
//    }
//
//    public void setError(boolean error) {
//        this.error = error;
//    }
//
//    public T getData() {
//        return results;
//    }
//
//    public void setData(T data) {
//        this.results = data;
//    }
//
//    @Override
//    public String toString() {
//        return "BaseHttpResult{" +
//                "error=" + error +
//                ", results=" + results +
//                '}';
//    }
//
//    /**
//     * 正常返回
//     *
//     * @return
//     */
//    public boolean isSuccessFul() {
//        return !isError();
//    }
}
