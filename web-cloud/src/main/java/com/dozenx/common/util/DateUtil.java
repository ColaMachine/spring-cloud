package com.dozenx.common.util;

import com.dozenx.common.util.StringUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期操作工具类
 *
 * @ClassName DateUtil
 * @CreateDate 2014-3-19
 * @Version V1.0
 */
public final class DateUtil {

    private DateUtil() {
    }

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_DASH = "yyyy-MM-dd_HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 根据指定格式转换成日期字符串
     */
    public static String formatToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取今天日期yyyy-MM-dd
     *
     * @return
     */
    public static String getTodayDate() {
        return formatToString(new Date(), YYYY_MM_DD);
    }

    /**
     * 说明:得到当前日期 年月日 不含时分秒
     *
     * @return
     * @author dozen.zhang
     * @date 2015年6月4日上午11:56:13
     */
    public static Date getTodayDateDate() {
        return parseToDate(formatToString(new Date(), YYYY_MM_DD), YYYY_MM_DD);
    }


    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNow() {
        return formatToString(new Date(), YYYY_MM_DD_HH_MM_SS);
    }


    /**
     * 说明: 得到当前时间 去掉 毫秒
     *
     * @return
     */
    public static Date getNowDate() {
        return parseToDate(formatToString(new Date(), YYYY_MM_DD_HH_MM_SS),
                YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 返回昨天日期
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 说明: 字符串转日期
     *
     * @param s
     * @param format
     * @return
     */
    public static Date parseToDate(String s, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            // e.printStackTrace();
            //System.err.println(s + "日期格式不对");
            return null;
        }
    }

    /**
     * 说明:自动匹配日期格式 把字符串转成日期
     *
     * @param s
     * @return Date
     */
    public static Date parseToDateTry(String s) {
        Date v = null;
        if (s.length() == DateUtil.YYYY_MM_DD.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYY_MM_DD);
        } else if (s.length() == DateUtil.YYYY_MM_DD_HH_MM_SS.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYY_MM_DD_HH_MM_SS);
        } else if (s.length() == DateUtil.YYYYMMDDHHMMSS.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYYMMDDHHMMSS);
        } else if (s.length() == DateUtil.YYYYMMDD.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYYMMDD);
        } else if (s.length() == "yyyy/MM/dd HH:mm".length()) {
            v = DateUtil.parseToDate(s, "yyyy/MM/dd HH:mm");
        } else {
            System.err.println("Unsupported date string format: " + s);
            return v;
        }
        return v;
    }

    /**
     * 格式化毫秒数
     *
     * @param time 毫秒数
     * @return HH:MM:ss
     */
    public static String formatMillisecond(long time) {
        time = time / 1000;
        long hh = time / 60 / 60;
        long mm = time / 60 % 60;
        long ss = time % 60;
        return (hh < 10 ? "0" + hh : hh) + ":" + (mm < 10 ? "0" + mm : mm)
                + ":" + (ss < 10 ? "0" + ss : ss);
    }

    public static String toString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            return df.format(date);

        } catch (Exception e) {
            // 如果不能转换,肯定是错误格式
            return null;
        }


    }

    // 判断日期格式是否正确
    public static boolean checkDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (Exception e) {
            // 如果不能转换,肯定是错误格式
            return false;
        }
        String s1 = df.format(d);
        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
        // 逻辑上不对.
        return date.equals(s1);
    }

    // 判断字符串是否是合法日期格式
    public static boolean checkStrDate(String format) {
        try {
            parseToDateTry(format);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * excel日期解析 返回Date
     */
    public static Date convertDate4JXL(Date jxlDate) {
        if (jxlDate == null) {
            return null;
        }
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        dateFormat.setTimeZone(gmt);
        String str = dateFormat.format(jxlDate);
        TimeZone local = TimeZone.getDefault();
        dateFormat.setTimeZone(local);
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * excel日期解析 返回String
     */
    public static String strDate4JXL(Date jxlDate) {
        if (jxlDate == null) {
            return "";
        }
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        dateFormat.setTimeZone(gmt);
        return dateFormat.format(jxlDate);
    }

    /**
     * 返回一个月份的最后一天
     *
     * @param time
     * @return
     */
    public static String getMonthLastDay(String time) {
        int yy = Integer.parseInt(time.substring(0, 4));
        int mm = Integer.parseInt(time.substring(5));

        String endTime = "";
        boolean r = yy % 4 == 0 && yy % 100 != 0 || yy % 400 == 0;
        if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10
                || mm == 12) {
            endTime = time + "-31";
        } else if (mm != 2) {
            endTime = time + "-30";
        } else {
            if (r) {
                endTime = time + "-29";
            } else {
                endTime = time + "-28";
            }
        }
        return endTime;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Date> T parse(String dateString, String dateFormat, Class<T> targetResultType) {
        if (StringUtil.isBlank(dateString))
            return null;
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            long time = df.parse(dateString).getTime();
            Date t = targetResultType.getConstructor(long.class).newInstance(time);
            return (T) t;
        } catch (ParseException e) {
            String errorInfo = "cannot use dateformat:" + dateFormat + " parse datestring:" + dateString;
            throw new IllegalArgumentException(errorInfo, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("error targetResultType:" + targetResultType.getName(), e);
        }
    }

    /**
     * 说明:日期增加
     *
     * @param dateStr
     * @param plus
     * @return
     */
    public static String dayAdd(String dateStr, int plus) {
        Date date = parseToDate(dateStr, YYYY_MM_DD);
        Date resultDate = new Date(date.getTime() + plus * 24 * 60 * 60 * 1000);
        return formatToString(resultDate, YYYY_MM_DD);

//		Calendar calendar = new GregorianCalendar();
//		Date date = parseToDate(dateStr, DateUtil.YYYY_MM_DD);
//        calendar.setTime(date);
//		calendar.add(Calendar.DATE, plus);
//		return formatToString(calendar.getTime(), YYYY_MM_DD);
    }

    /**
     * 说明:根据timestamp 精确到分钟的精度
     *
     * @param datetime
     * @return void
     * @author dozen.zhang
     * @date 2015年7月2日上午10:05:34
     */
    public static void printTimestampby60(long datetime) {
        Date date = new Date();
        date.setTime(datetime * 60000);
        System.out.println(DateUtil.formatToString(date, DateUtil.YYYY_MM_DD_HH_MM));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));

    }

    public static String toDateStr(long vlaue) {
        Date date = new Date(vlaue);
        return DateUtil.toDateStr(date, "yyyy-MM-dd HH:mm");
    }

    public static String toDateStr(Date date, String format) {
        return DateUtil.formatToString(date, format);

    }

    public static void main(String[] args) {

        List list = DateUtil.getWeekDayList();
        String s ="2019-07-17T16:00:00.000Z";
        System.out.println(UTCStringtODefaultString(s));
        System.out.println(DateUtil.getNowTimeStampSeconds());

        System.out.println(DateUtil.getNowTimeStampSeconds() - 60);

        System.out.println(DateUtil.toDateStr(1534721800l * 1000));
        System.out.println(DateUtil.toDateStr(1496997617l * 1000));
        System.out.println(DateUtil.toDateStr(1491038114l * 1000));
        System.out.println(DateUtil.toDateStr(1490862198l * 1000));
        DateUtil.printTimestampby60(24355980);
        System.out.println(24355980 / 24 / 60);
        System.out.println(24355980 / 24 / 60);
        System.out.println(24355980 / 24 / 60);
        System.out.println(1260 / 60);
        System.out.println(System.currentTimeMillis());
        TimeZone tz = TimeZone.getTimeZone("GMT-8:00");
        Calendar ca = Calendar.getInstance();
        ca.setTimeZone(tz);
        System.out.println(ca.getTimeInMillis());
        System.out.println(new Date().getTime());
        System.out.println(DateUtil.formatToString(new Date(1480695398105l), YYYY_MM_DD_HH_MM_SS));
    }

    public final static int DATE_INTERVAL_DAY = 1; // 日
    public final static int DATE_INTERVAL_WEEK = 2; // 周
    public final static int DATE_INTERVAL_MONTH = 3; // 月
    public final static int DATE_INTERVAL_YEAR = 4; // 年
    public final static int DATE_INTERVAL_HOUR = 5; // 小时
    public final static int DATE_INTERVAL_MINUTE = 6; // 分钟
    public final static int DATE_INTERVAL_SECOND = 7; // 秒

    /**
     * 加时间
     *
     * @param interval 增加项，可以是天数、月份、年数、时间、分钟、秒
     * @param date     时间
     * @param num      加的数目
     * @return 时间加后的时间
     * @author sunju
     * @creationDate. 2010-8-27 下午05:28:06
     */
    public static Date dateAdd(int interval, Date date, int num) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (interval) {
            case DATE_INTERVAL_DAY:
                calendar.add(Calendar.DATE, num);
                break;
            case DATE_INTERVAL_WEEK:
                calendar.add(Calendar.WEEK_OF_MONTH, num);
                break;
            case DATE_INTERVAL_MONTH:
                calendar.add(Calendar.MONTH, num);
                break;
            case DATE_INTERVAL_YEAR:
                calendar.add(Calendar.YEAR, num);
                break;
            case DATE_INTERVAL_HOUR:
                calendar.add(Calendar.HOUR, num);
                break;
            case DATE_INTERVAL_MINUTE:
                calendar.add(Calendar.MINUTE, num);
                break;
            case DATE_INTERVAL_SECOND:
                calendar.add(Calendar.SECOND, num);
                break;
            default:
        }
        return calendar.getTime();
    }


    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 获取当前时间字符串(年月日)
     *
     * @return 时间字符串
     * @author sunju
     * @creationDate. 2011-5-4 下午08:22:34
     */
    public static String getNowStringDate() {
        return DateUtil.formatToString(new Date(), "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    //获取昨天的结束时间
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    //获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    //获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }

    //两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    //获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    //获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    //返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    //返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    //返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    //获取某年某月到某年某月按天的切片日期集合（间隔天数的日期集合）
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    public static int getTodayLeftSeconds() {

        return (int) ((DateUtil.getDayEnd().getTime() - DateUtil.getNowDate().getTime()) / 1000);
    }


    /**
     * @Author: dozen.zhang
     * @Description:获得当前时间戳保留到毫秒
     * @Date: 2018/1/5
     */
    public static long getNowTimeStampMills() {
        return System.currentTimeMillis();
    }

    /**
     * @Author: dozen.zhang
     * @Description:获得当前时间错 保留到秒
     * @Date: 2018/1/5
     */
    public static long getNowTimeStampSeconds() {
        return getNowTimeStampMills() / 1000;
    }

    /**
     * 获取当前事件戳
     *
     * @return
     */
    public static Timestamp getNowTimeStamp() {
        return new Timestamp(new Date().getTime());
    }


    public static Long[] getTodayStartMills() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long startTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        Long endTime = calendar.getTimeInMillis();
        return new Long[]{startTime, endTime};
    }

    /**
     * 本月第一天
     *
     * @return
     */
    public Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
        //	String startDate = DateUtil.toDateStr(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

//    /**
//     * 本月第一天
//     * @return
//     */
//    public Date getFirstDayOfMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//
//        return calendar.getTime();
//        //	String startDate = DateUtil.toDateStr(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
//    }
//
//    /**
//     * 本月第一天字符串
//     * @return
//     */
//    public String getFirstDayOfMonthStr() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        String startDate = DateUtil.toDateStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//        return startDate;
//    }
//
//
//    /**
//     * 本月第一天
//     * @return
//     */
//    public Date getEndDayOfMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//
//        return calendar.getTime();
//        //	String startDate = DateUtil.toDateStr(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
//    }
//
//    /**
//     * 本月第一天字符串
//     * @return
//     */
//    public String getFirstDayOfMonthStr() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        String startDate = DateUtil.toDateStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//        return startDate;
//    }


    public static List<Date> getWeekDayList() {
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date first = cal.getTime();
        List<Date> weekdays = new ArrayList<>();
        weekdays.add(first);
        for (int i = 0; i < 6; i++) {
            cal.add(Calendar.DATE, 1);
            weekdays.add(cal.getTime());
        }
        return weekdays;
    }



    public static String UTCStringtODefaultString(String UTCString) {
        try
        {
            if (StringUtil.isNotBlank(UTCString) && UTCString.indexOf("T")>0) {
                UTCString = UTCString.replace("Z", " UTC");
                SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = utcFormat.parse(UTCString);
                return defaultFormat.format(date);
            }else{
                return null;
            }

        } catch(ParseException pe)
        {
            pe.printStackTrace();
            return null;
        }
    }



}



