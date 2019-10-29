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
}
