package cn.dr.basemvp.mvp;

/**
 * @desc
 */
public interface IView {


    //显示loading
    void showLoading();

    //隐藏loading
    void hideLoading();

    //显示吐司
    void showError(String msg);

    //显示网络请求失败布局
//    void showErrorView();
}
