package cn.dr.basemvp.widget;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * Created by dingrui 2019/10/30
 */

public class OptionModel implements IPickerViewData {

    private String str;

    public OptionModel(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String getPickerViewText() {
        return str;
    }
}
