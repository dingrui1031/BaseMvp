package cn.dr.basemvp.widget;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.dr.basemvp.R;

/**
 * Created by dingrui 2019/10/30
 */

public class PickViewHelper {

    private static TimePickerView pvCustomTime;
    private static OptionsPickerView<OptionModel> pvCustomOptions;

    private interface OnDataReturnListener {
        //默认时间选择器
        void onReturnDate(Date date);

        //默认三级选择器
        void onReturnThirdOption(String option1, String option2, String option3);

        //一级选择器
        void onReturnOption(String option1);
    }

    /**
     * 默认时间选择器
     *
     * @param context
     * @param listener
     */
    public static void showDefaultTimePicker(Activity context, OnDataReturnListener listener) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                listener.onReturnDate(date);
            }
        }).build();
    }

    /**
     * 默认三级条件选择器
     *
     * @param context
     * @param options1Items
     * @param options2Items
     * @param options3Items
     * @param listener
     */
    public static void showDefaultThreeOptionPicker(Activity context, List<OptionModel> options1Items, List<OptionModel> options2Items, List<OptionModel> options3Items, OnDataReturnListener listener) {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String result = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(option2).getPickerViewText()
                        + options3Items.get(options3).getPickerViewText();
                listener.onReturnThirdOption(options1Items.get(options1).getPickerViewText(),
                        options1Items.get(options1).getPickerViewText(),
                        options1Items.get(options1).getPickerViewText());
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    /**
     * 自定义时间选择器
     *
     * @param context
     * @param startYear
     * @param endYear
     * @param title
     * @param listener
     */
    public static void showCustomTimePicker(Activity context, int startYear, int endYear, String title, OnDataReturnListener listener) {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, 11, 31);
        //时间选择器
        pvCustomTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                listener.onReturnDate(date);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                        final TextView tvConfirm = (TextView) v.findViewById(R.id.tv_confirm);
                        final TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvTitle.setText(title);
                        tvConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})//是否显示"年", "月", "日", "时", "分", "秒"
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }


    /**
     * 自定义条件选择器
     *
     * @param context
     * @param options1Items
     * @param listener
     */
    public static void showCustomOneOptionPicker(Activity context, List<OptionModel> options1Items, String title, OnDataReturnListener listener) {
        pvCustomOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String result = options1Items.get(options1).getPickerViewText();
                listener.onReturnOption(result);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                        final TextView tvConfirm = (TextView) v.findViewById(R.id.tv_confirm);
                        final TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvTitle.setText(title);
                        tvConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(true)
                .setOutSideCancelable(true)
                .build();
        pvCustomOptions.setPicker(options1Items);//添加数据
    }


}
