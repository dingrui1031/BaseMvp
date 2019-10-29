package cn.dr.basemvp.utils;

import androidx.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @description 时间转换工具类
 */
public class TimeUtils {
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 得到仿微信日期格式输出
     *
     * @param msgTimeMillis
     * @return
     */
    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static String getNewChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat="M月d日 HH:mm";
        String yearTimeFormat="yyyy年M月d日 HH:mm";
        String am_pm="";
        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=0&&hour<6){
            am_pm="凌晨";
        }else if(hour>=6&&hour<12){
            am_pm="早上";
        }else if(hour==12){
            am_pm="中午";
        }else if(hour>12&&hour<18){
            am_pm="下午";
        }else if(hour>=18){
            am_pm="晚上";
        }
        timeFormat="M月d日 "+ am_pm +"HH:mm";
        yearTimeFormat="yyyy年M月d日 "+ am_pm +"HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR)==otherCalendar.get(Calendar.YEAR);
        if(yearTemp){
            int todayMonth=todayCalendar.get(Calendar.MONTH);
            int otherMonth=otherCalendar.get(Calendar.MONTH);
            if(todayMonth==otherMonth){//表示是同一个月
                int temp=todayCalendar.get(Calendar.DATE)-otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth=todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if(dayOfMonth==todayOfMonth){//表示是同一周
                            int dayOfWeek=otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if(dayOfWeek!=1){//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
                            }else{
                                result = getTime(timesamp,timeFormat);
                            }
                        }else{
                            result = getTime(timesamp,timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp,timeFormat);
                        break;
                }
            }else{
                result = getTime(timesamp,timeFormat);
            }
        }else{
            result=getYearTime(timesamp,yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time,String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time,String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }


    @NonNull
    private static String getTime(DateTime msgTime) {
        int hourOfDay = msgTime.getHourOfDay();
        String hour = "";
        if (hourOfDay < 10) {
            hour = "0" + msgTime.getHourOfDay();
        } else {
            hour = "" + msgTime.getHourOfDay();
        }
        int minuteOfDay = msgTime.getMinuteOfHour();
        String minute = "";
        if (minuteOfDay < 10) {
            minute = "0" + msgTime.getMinuteOfHour();
        } else {
            minute = "" + msgTime.getMinuteOfHour();
        }
//        if (hourOfDay >= 18) {//18-24
//            when = "晚上";
//        } else if (hourOfDay >= 13) {//13-18
//            when = "下午";
//        } else if (hourOfDay >= 11) {//11-13
//            when = "中午";
//        } else if (hourOfDay >= 5) {//5-11
//            when = "早上";
//        } else {//0-5
//            when = "凌晨";
//        }
        return hour + ":" + minute;
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为 format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time 格式为 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date 类型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date 类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date 类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date Date 类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis 毫秒时间戳
     * @return Date 类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    // 格式化 录音时长为 时:分:秒
    public static String FormatMiss(int miss) {
        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60 + "" : "0" + (miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }


    public static String getChineseWeek(final Date date) {
        String week = new SimpleDateFormat("E", Locale.CHINA).format(date);
        Log.e("week",week);
        return week;
    }

    /**
     * <pre>
     * 根据指定的日期字符串获取星期几
     * </pre>
     *
     * @param strDate 指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd) yyyy-M-d
     * @return week
     * 星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
     */
    public static String getWeekByDateStr(String strDate) {
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(5, 6));
        int day = Integer.parseInt(strDate.substring(7));

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getTimeCompareSize(String startTime, String endTime) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 当前时间是否在n天之内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isDaysInner(Date addtime, Date now, int n) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -n);  //设置为7天前
        Date beforeNdays = calendar.getTime();   //得到7天前的时间
        if (beforeNdays.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

}
