package com.ndy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author nidayu
 * @Description: ʱ���ʽ��ת��
 * @date 2015/6/18
 */
public class DateUtil {
    public static final int PERIOD_TYPE_YEAR = 0;
    public static final int PERIOD_TYPE_MONTH = 1;
    public static final int PERIOD_TYPE_HALFMONTH = 2;
    public static final int PERIOD_TYPE_WEEK = 3;

    /**
     * ��ȡ��ʼ�·ݺͽ����·��м�������·ݣ�������ʼ�ºͽ�����
     * @param start yyyy-MM
     * @param end yyyy-MM
     * @return yyyy-MM������
     */
    public static String[] getAllMonths(String start, String end){
        String splitSign="-";
        String regex="\\d{4}"+splitSign+"(([0][1-9])|([1][012]))"; //�ж�YYYY-MMʱ���ʽ��������ʽ
        if(!start.matches(regex) || !end.matches(regex)){
            return new String[0];
        }
        List<String> list=new ArrayList<String>();
        if(start.compareTo(end)>0){
            //start����end����ʱ������
            String temp=start;
            start=end;
            end=temp;
        }
        String temp=start; //����С�·ݿ�ʼ
        while(temp.compareTo(start)>=0 && temp.compareTo(end)<=0){
            list.add(temp); //���ȼ�����С�·�,���ż�����һ���·�
            String[] arr=temp.split(splitSign);
            int year=Integer.valueOf(arr[0]);
            int month=Integer.valueOf(arr[1])+1;
            if(month>12){
                month=1;
                year++;
            }
            if(month<10){//��0����
                temp=year+splitSign+"0"+month;
            }else{
                temp=year+splitSign+month;
            }
        }
        int size=list.size();
        String[] result=new String[size];
        for(int i=0;i<size;i++){
            result[i]=list.get(i);
        }
        return result;
    }

    /**
     * ����ӱ��µ�������ǰ������num���·�
     * @param num
     * @return yyyyMM�ļ���
     */
    public static List<String> getMonth(int num){
        List<String> objectTmp = new ArrayList<String>();
        DateFormat format2 = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        for (int i = 0; i < num; i++) {
            c.add(Calendar.MONTH, -1);
            Date date = c.getTime();
            String date2 = format2.format(date);
            objectTmp.add(date2);
        }
        return objectTmp;
    }

    /**
     * ����format�ĸ�ʽ����ӱ��µ�������ǰ������num���·�
     * @param num
     * @param format
     * @return
     */
    public static List<String> getMonths(int num,String format){
        List<String> objectTmp = new ArrayList<String>();
        DateFormat format2 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        for (int i = 0; i < num; i++) {
            c.add(Calendar.MONTH, -1);
            Date date = c.getTime();
            String date2 = format2.format(date);
            objectTmp.add(date2);
        }
        return objectTmp;
    }

    /**
     * ����format�ĸ�ʽ������ϸ��µ��ϸ�����ǰ������num���·ݣ����������£�
     * @param num
     * @param format
     * @return
     */
    public static List<String> getMonthsNotInclude(int num,String format){
        List<String> objectTmp = new ArrayList<String>();
        DateFormat format2 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        for (int i = 0; i < num; i++) {
            c.add(Calendar.MONTH, -1);
            Date date = c.getTime();
            String date2 = format2.format(date);
            objectTmp.add(date2);
        }
        return objectTmp;
    }

    /**
     * �����dateStr������������
     * @param dateStr
     * @param format �����ʽ
     * @return
     * @throws Exception
     */
    public static int dayDist(String dateStr, String format) throws Exception{
        SimpleDateFormat df=new SimpleDateFormat(format);
        Date date=df.parse(dateStr);
        long timeMillion=new Date().getTime()-date.getTime();
        return (int)(timeMillion/(24l*60*60*1000));
    }

    /**
     * ����format�ĸ�ʽ����ϸ��µĽ���
     * @param format
     * @return
     */
    public static String getLMDay(String format){
        DateFormat format2 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();
        String date2 = format2.format(date);
        return date2;
    }

    /**
     * ����format�ĸ�ʽ���ȥ��Ľ���
     * @param format
     * @return
     */
    public static String getLYDay(String format){
        DateFormat format2 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        Date date = c.getTime();
        String date2 = format2.format(date);
        return date2;
    }

    /**
     * ����format�ĸ�ʽ���ȥn����ǰ�Ľ���
     * @param n
     * @param format
     * @return
     */
    public static String getLMDay(int n,String format){
        DateFormat format2 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -n);
        Date date = c.getTime();
        String date2 = format2.format(date);
        return date2;
    }


    /**
     * ��Date����format�ĸ�ʽת��ΪString
     * @param d
     * @param format Ĭ�� yyyy-MM-dd HH:mm:ss ���Զ���
     * @return
     */
    public static String formatDate(Date d,String format){
        if (d == null) {
            return null;
        }
        if(format==null||"".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     * ��Stringת��ΪDate
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date stringToDate(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        if (dateStr!=null&&!"".equals(dateStr)) {
            try {
                date = dd.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * �Ƿ��Ǳ���
     * @param month
     * @return
     */
    public static Boolean isEqual(String month){
        Date d = new Date();
        String data = formatDate(d, "yyyyMM");
        Boolean flag = false;
        if(data.equals(month)){
            flag = true;
        }
        return flag;
    }

    /**
     * ���ڵļӼ���
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    /**
     * ��ȡ��βʱ��
     * @param currentDate
     * @param type
     * @return
     */
    public static Date[] getPeriodByType(Date currentDate, int type) {
        Date fromDate = currentDate;
        Date toDate = currentDate;
        Calendar cal;
        switch (type) {
            //��ȡ���׺���β��0
            case PERIOD_TYPE_YEAR:
                cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DATE, 1);
                fromDate = cal.getTime();
                cal.add(Calendar.YEAR, 1);
                toDate = add(cal.getTime(), Calendar.DATE, -1);
                break;
            //��ȡ���׺���β��1
            case PERIOD_TYPE_MONTH:
                cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.set(Calendar.DATE, 1);
                fromDate = cal.getTime();
                cal.add(Calendar.MONTH, 1);
                toDate = add(cal.getTime(), Calendar.DATE, -1);
                break;
            //��ȡ���£�2
            case PERIOD_TYPE_HALFMONTH:
                int dayOfMonth = getDayOfMonth(currentDate);
                fromDate = add(currentDate, Calendar.DATE, -1 * dayOfMonth + 1);
                if (dayOfMonth > 15) {
                    cal = Calendar.getInstance();
                    cal.setTime(fromDate);
                    cal.add(Calendar.MONTH, 1);
                    toDate = add(cal.getTime(), Calendar.DATE, -1);
                    fromDate = add(fromDate, Calendar.DATE, 15);
                } else {
                    toDate = add(fromDate, Calendar.DATE, 14);
                }
                break;
            //�����ܣ�����ĩ��ʼ������������3
            case PERIOD_TYPE_WEEK:
                int dayOfWeek = getWeekDay(currentDate);
                int seg = -1 * dayOfWeek;
                fromDate = add(currentDate, Calendar.DATE, seg);
                toDate = add(fromDate, Calendar.DATE, 6);
                break;
            default:
                break;
        }

        Date[] period = new Date[2];
        period[0] = fromDate;
        period[1] = toDate;
        return period;
    }

    /**
     * ��ȡ����
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * �鿴���ܼ�
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static int getWeekDay(int weekNumber) {
        switch (weekNumber) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 0;
            default:
                return -1;
        }
    }

    /**
     * ���ؼ���ǰ�������ַ���   ��201408 ǰ�����¾���201406
     * @param d  ����
     * @param format ���ظ�ʽ
     * @param month  ����ǰ
     * @return
     */
    public static String getBeforeMonth(Date d,String format,int month){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -month);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }


    /**
     * ������������ڷ��ظ����е����һ�������
     * @param d
     * @return
     */
    public static String lastDayOfMonth(String d) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMM").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * ������������ڷ��ظ����е����һ�������
     * @param inputFormat ���� yyyy-MM
     * @param outFormat   ���� yyyy-MM-dd
     * @return
     */
    public static String lastDayOfMonth(String d,String inputFormat,String outFormat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(inputFormat).parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(outFormat);
        return sdf.format(time);
    }

    /**
     * ������������ڷ��ظ����еĵ�һ�������
     * @param d
     * @return
     */
    public static String firstDayOfMonth(String d) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM").parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * ������������ڷ��ظ����еĵ�һ�������
     * @param inputFormat ���� yyyy-MM
     * @param OutputFormat   ���� yyyy-MM-dd
     * @return
     */
    public static String firstDayOfMonth(String d, String inputFormat, String OutputFormat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(inputFormat).parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(OutputFormat);
        return sdf.format(time);
    }


    public static String getDependCycle(String d, String inputFormat){
        String format = "yyyy-MM";
        if(inputFormat!=null){
            format = inputFormat;
        }
        String start = DateUtil.firstDayOfMonth(d, format, "yyyy-MM-dd");
        String end = DateUtil.lastDayOfMonth(d, format, "yyyy-MM-dd");
        return  start+"��"+end;
    }

    /**
     * ���ָ�����ڵ���һ�µĵ�һ��
     * @param d
     * @param format
     * @return
     */
    public static String nextMonthFirstDay(String d, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * ���ָ�����ڵ���һ�µ����һ��
     * @param d
     * @param format
     * @return
     */
    public static String nextMonthLastDay(String d, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        Date time = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * ��HH:mm:ss��ʽ��ʱ��ת��Ϊ��
     * @param time
     * @return
     */
    public static int transform(String time) {
        String temp[] = time.split(":");
        int allSeconds=0;
        if (temp.length==3) {
            int hours = Integer.valueOf(temp[0]);
            int minutes = Integer.valueOf(temp[1]);
            int seconds = Integer.valueOf(temp[2]);
            allSeconds = hours * 60 * 60 + minutes * 60 + seconds;
        }else if (temp.length==2) {
            int minutes = Integer.valueOf(temp[0]);
            int seconds = Integer.valueOf(temp[1]);
            allSeconds =  minutes * 60 + seconds;
        }else if (temp.length==1) {
            int seconds = Integer.valueOf(temp[0]);
            allSeconds =  seconds;
        }
        //System.out.println("������" + allSeconds);
        return allSeconds;
    }


    /**
     * ��ȡ��������ڣ�����ʽ��Ϊ��ָ�������ڸ�ʽ
     * @param format
     * @return
     */
    public static String getToday(String format){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = sdf.format(date);
        return result;
    }


    /**
     * ��Tue Oct 21 12:24:26 CST 2014��ʽ���ַ�������ת��ΪyyyyMMdd��ʽ���ַ�������
     * @param strDate ����Tue Oct 21 12:24:26 CST 2014
     * @param fm ����yyyyMMdd
     * @return
     */
    public static String strDateToStr(String strDate, String fm){
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date date = sdf.parse(strDate);

            SimpleDateFormat dd = new SimpleDateFormat(fm);
            result = dd.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {

//        String[] list = getAllMonths("2012-11", "2015-03");
//        for(String i : list){
//            System.out.println(i);
//        }

//        List<String> list = getMonth(6);
//        for(String i : list){
//            System.out.println(i);
//        }

//        List<String> list = getMonths(6, "yyyy-MM");
//        for(String i : list){
//            System.out.println(i);
//        }

//        List<String> list = getMonthsNotInclude(6, "yyyy-MM");
//        for(String i : list){
//            System.out.println(i);
//        }

//        try {
//            int dist = dayDist("20140604", "yyyyMMdd");
//            System.out.println(dist);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println(getLMDay("yyyy/MM/dd"));
//        System.out.println(getLYDay("yyyy/MM/dd"));
//        System.out.println(getLMDay(3, "yyyy/MM/dd"));

//        System.out.println(formatDate(new Date(), "yyyyMMdd HHmmssss"));

//        System.out.println(stringToDate("2015-06-12", "yyyy-MM-dd"));

//        System.out.println(isEqual("201501"));

//        System.out.println(add(new Date(), Calendar.MONTH, -2));

//        Date[] getPeriodByType = getPeriodByType(stringToDate("2015-06-11", "yyyy-MM-dd"), 3);
//        for(Date i : getPeriodByType){
//            System.out.println(formatDate(i, "yyyy-MM-dd HH:mm:ss"));
//        }

//        System.out.println(getDayOfMonth(new Date()));

//        System.out.println(getWeekDay(new Date()));

//        System.out.println(getWeekDay(12));

//        System.out.println(getBeforeMonth(new Date(), "yyyy-MM-dd", 2));

//        System.out.println(lastDayOfMonth("201506", "yyyyMM", "yyyy/MM/dd"));

    }
}
