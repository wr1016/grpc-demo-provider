package cn.vpclub.grpc.demo.provider.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mr.Fan on 2017/3/22.
 */
@Slf4j
public class DateUtil {
    private static final String DEFAULT_SHORT_FORMAT = "yyyy-MM-dd";

    private static final String DEFAULT_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_FULL_FORMAT_1 = "yyyy-MM-dd HH:mm:ss.s";
    private static final String DEFAULT_SEED_FORMAT = "yyyyMMdd HHmmss";
    private static final String DEFAULT_SEED_FORMAT_1 = "yyyyMMdd HH:mm:ss";
    private static final String DEFAULT_YEAR_FORMAT = "yyyyMMdd";
    private static final String DEFAULT_MONTH_FORMAT = "yyyyMM";
    private static final String DEFAULT_SEED_FORMAT1 = "yyyyMMddHHmmss";

    /**
     * yyyyMMdd HHmmss
     * Long转String
     */
    public static String getLongToString(Long time){
        SimpleDateFormat sdf =  new SimpleDateFormat(DEFAULT_SEED_FORMAT);
        return sdf.format(time);
    }
    /**
     * yyyyMMdd
     * Long时间戳转String
     */
    public static String getString(Long time){
        SimpleDateFormat sdf =  new SimpleDateFormat(DEFAULT_YEAR_FORMAT);
        return sdf.format(time);
    }
    /**
     * yyyyMM
     * Long时间戳转String
     */
    public static String getString1(Long time){
        SimpleDateFormat sdf =  new SimpleDateFormat(DEFAULT_MONTH_FORMAT);
        return sdf.format(time);
    }
    /**
     * yyyyMMdd
     * Long时间戳转String
     */
    public static String getString2(Long time){
        SimpleDateFormat sdf =  new SimpleDateFormat(DEFAULT_SEED_FORMAT1);
        return sdf.format(time);
    }
    /**
     *String 转long 无空格
     * @author : fanxiang
     * @Date : 2017/4/2
     */
    public static long getSeedDate1(String formatTime){
        return getDate(formatTime, DEFAULT_SEED_FORMAT1).getTime();
    }


    /**
     * long 转String
     * @param timeStamps
     * @return
     */
    public static String getTime(Long timeStamps){
        SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_SHORT_FORMAT);
        return  sdf.format(getDate(timeStamps));
    }
    /**
     *String 转long
     * @author : fanxiang
     * @Date : 2017/4/2
     */
    public static long getlongTime(String timeStamps){
        long time =0L;
        try {
            time =   getDate(timeStamps, DEFAULT_FULL_FORMAT).getTime();
        }catch (Exception e){
            time =   getDate(timeStamps, DEFAULT_FULL_FORMAT_1).getTime();
        }
        return  time;
    }
    public static long getlongTime_1(String timeStamps){
        long time =0L;
        try {
            time =   getDate(timeStamps, DEFAULT_FULL_FORMAT_1).getTime();
        }catch (Exception e){
            time =   getDate(timeStamps, DEFAULT_FULL_FORMAT).getTime();
        }
        return  time;
    }
    /**
     *String 转long
     * @author : fanxiang
     * @Date : 2017/4/2
     */
    public static long getSeedDate(String formatTime){
        return getDate(formatTime, DEFAULT_SEED_FORMAT).getTime();
    }
    public static long getSeedDate_1(String formatTime){
        return getDate(formatTime, DEFAULT_SEED_FORMAT_1).getTime();
    }

    /**
     *String 转long
     * @author : fanxiang
     * @Date : 2017/4/2
     */
    public static long getYearDate(String formatTime){
        return getDate(formatTime, DEFAULT_YEAR_FORMAT).getTime();
    }
    /**
     * long 转date
     * @param timeStamps
     * @return
     */
    public static Date getDate(Long timeStamps){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamps);
        return c.getTime();
    }

    public static Date timestamp2Date(String timestamp){
        return new Date(Long.valueOf(timestamp.trim()));
    }

    /**
     * String 转data
     * 解析日期
     * @param formatTime
     * @param datePattern
     * @return
     */
    public static Date getDate(String formatTime,String datePattern){
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(formatTime, datePattern);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
            log.warn("格式化时间【{}】失败:{}",formatTime,datePattern);
        }
        return null;
    }

    /**
     * 解析日期
     * @param formatTime yyyy-MM-dd格式
     * @return
     */
    public static Date getShortDate(String formatTime){
        return getDate(formatTime, DEFAULT_SHORT_FORMAT);
    }
    /**

     * @return
     */
    public static Date getFullDate(String formatTime){
        return getDate(formatTime, DEFAULT_FULL_FORMAT);
    }

    /**
     * 获取当前日期,时分秒置0
     * @return
     */
    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取距离当前日期指定天数的日期时间，时分秒置0
     * @param days
     * @return
     */
    public static Date getDate(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+days, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取距离当前日期指定天数的日期时间，并设置小时数
     * @param
     * @return
     */
    public static Date getDate(int diffdays,int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+diffdays, hour, 0, 0);
        return calendar.getTime();
    }

    /**
     *
     *
     * 功能描述：判断起始时间和结束时间是否在指定的范围内
     *
     * @param startTime
     * @param endTime
     * @param unit
     * @param range
     *            void
     *
     */
    public static boolean timeIsInRange(Date startTime, Date endTime, int unit, int range) {
        /**
         * 时间判断
         */
        Calendar startC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();
        startC.clear();
        endC.clear();
        startC.setTime(startTime);
        endC.setTime(endTime);
        startC.add(unit, range); // 获取开始时间3个月后的时间
        if (endC.compareTo(startC) > 0) { // 结束日期大于开始日期3个月
            return false;
        }
        return true;
    }

}
