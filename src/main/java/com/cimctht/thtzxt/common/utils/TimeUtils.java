package com.cimctht.thtzxt.common.utils;

import com.cimctht.thtzxt.common.exception.UnimaxException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class TimeUtils {

    /**
     * @comment 將傳入日期按照指定格式進行轉換，通常用於詳細日期時間格式，取日期，取時間等
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static Date convertDateToDate(Date input, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date output;
        try
        {
            output = sdf.parse(sdf.format(input));
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }
	
    /**
     * @comment 日期转换为时间
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static String convertDateToShortString(Date input)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(input==null) {
        	return "";
        }

        String output;
        try
        {
            output = sdf.format(input);
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }
	
    /**
     * @comment 日期转换为时间
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static String convertDateToString(Date input)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(input==null) {
        	return "";
        }

        String output;
        try
        {
            output = sdf.format(input);
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }
	
    /**
     * @comment 日期转换为时间
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static String convertDateToString(Date input, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(input==null) {
        	return "";
        }

        String output;
        try
        {
            output = sdf.format(input);
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }
	
    /**
     * @comment 将timestamp格式转换成正常的时间格式，参数String类型
     * 比如timestamp格式时间："2018-01-05T03:03:05Z"
     * 转换后的DateString时间："2018-01-05 11:03:05"
     * 参数格式timestamp
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
    public static String timestampToDateString(String timestamp){
        String resultDate = "";
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//          String timestamp1 = timestamp.substring(0,timestamp.indexOf("."))+"Z";
            Object obj = sdf.parse(timestamp).getTime();
            String ooo = obj.toString();
            long stimes = Long.parseLong(ooo);
            stimes = stimes + 28800000;
        /*  Calendar qcalendar = Calendar.getInstance();
            qcalendar.setTimeInMillis(stimes);*/
            Date da = new Date(stimes);
            resultDate = sim.format(da.getTime());

        } catch (Exception e1) {
            return "";
        }
        return resultDate;
    }
    
    
    /**
     * @comment 时间戳转换成日期格式字符串
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
    public static String timeStamp2String(Timestamp timestamp,String format) {  
    	long time = timestamp.getTime();
    	String fff = String.valueOf(time).substring(String.valueOf(time).length()-3);
        String seconds = String.valueOf(time/1000);
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+fff)));  
    }  
    
    /**
     * @comment 日期转换为时间
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static Date convertStringToDate(String input, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date output;
        try
        {
            output = sdf.parse(input);
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }

    /**
     * @comment 将Calendar转换为date
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static Date convertCalendarToDate(Calendar cal)
    {
        Date output;
        try
        {
            output = cal.getTime();
        } catch (Exception e)
        {
            throw new UnimaxException("转换出错" + e.getMessage());
        }

        return output;
    }
	
    /**
     * @comment 将date转换为Calendar
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static Calendar convertDateToCalendar(Date date)
    {
		Calendar calendar=Calendar.getInstance();
        try
        {
        	calendar.setTime(date);
        } catch (Exception e)
        {
            throw new UnimaxException("转换出错" + e.getMessage());
        }

        return calendar;
    }
      
    /**
     * @comment 将html5控件传来的带时、分的时间字符串转为日期
     * @author Walter(翟笑天)
     * @date 2020/10/10
     */
	public static Date convertHtml5DateStringToDate(String input)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date output;
        try
        {
        	input = input.replace("T", " ");
        	input = input + ":00";
            output = sdf.parse(input);
        } catch (Exception e)
        {
            throw new UnimaxException("时间格式错误" + e.getMessage());
        }

        return output;
    }
	
}
