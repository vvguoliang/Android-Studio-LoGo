package logo.vvguoliang.com.logo.Utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
* @Title: TransformTimeUtil.java 
* @Package com.sxsfinance.sxsfinance_android_libs_Utils 
* @Description: TODO 时间节点
* @author A18ccms A18ccms_gmail_com   
* @date 2016-3-8 下午4:37:32 
* @version V1.0
 */
public class TransformTimeUtil {
	Context     mContext;
	String[] 	itemdate={ "", ""};
	String 		str_month;
	String 		str_day;
	/**
	 * 得到今天的日期，格式为yyyyMMdd
	 * @return 获取今日日期yyyyMMdd
	 */
	public static String GetDay_1()
	{	
		DateFormat day = new SimpleDateFormat("yyyyMMdd");
		String str_day = day.format(new Date());
		return str_day;
	}
	
	/**
	 * 得到今天的日期，格式为yyyy-MM-dd HH:mm:ss
	 * @return 获取今日日期yyyy-MM-dd HH:mm:ss
	 */
	public static String GetDay_2()
	{	
		DateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str_day = day.format(new Date());
		return str_day;
	}
	/**
	 * 得到今天此刻是几点，格式为HH
	 * @return 获取今日日期HH
	 */
	public static String GetDayHour()
	{	
		DateFormat day = new SimpleDateFormat("HH");
		String str_day = day.format(new Date());
		return str_day;
	}
	/**
	 * 得到今天此刻是时分，格式为mm
	 * @return 获取今日日期mm
	 */
	public static String GetDayMinute()
	{	
		DateFormat day = new SimpleDateFormat("mm");
		String str_day = day.format(new Date());
		return str_day;
	}
	/**
	 * 得到本月日期，格式为yyyyMM
	 * @return 获取本月日期yyyyMM
	 */
	public static String GetMonth()
	{	
		DateFormat day = new SimpleDateFormat("yyyyMM");
		String str_day = day.format(new Date());
		return str_day;
	}
	/**
	 * 得到今年，格式为yyyy
	 * @return 获取今年yyyy
	 */
	public static String GetYear()
	{	
		DateFormat day = new SimpleDateFormat("yyyy");
		String str_day = day.format(new Date());
		return str_day;
	}
	/**
	 * 获取今日是几号
	 */
	public static int GetDayOfMonth()
	{
		DateFormat day = new SimpleDateFormat("dd");
		String str_day = day.format(new Date());
		return Integer.valueOf(str_day);
	}
	/**
	 * 根据定时任务的时间、周期，与当前时间对比。获得调整后的时间
	 * @param entityTimingTask
	 * @return
	 */
	public static String myChangeTime(String frequency, String time){
		//获取当前是周几
		Calendar c=Calendar.getInstance();

		int dayOfWeek=c.get(Calendar.DAY_OF_WEEK) - 1;	
		if(dayOfWeek==0){
			dayOfWeek=7;
		}
		//获取现在时间
		//		DateFormat system_time = new java.text.SimpleDateFormat("yyyyMMddHHmm");
		//		String str_time = system_time.format(new Date());
		//获取设置的频率
		String task_period=frequency;
		String task_time=time;
		boolean b = TransformTimeUtil.isBeforeSystem(Integer.valueOf(task_time.substring(8, 10)),
				Integer.valueOf(task_time.substring(10, 12)));
		if(task_period.equals("o")||task_period.contains("e")||task_period.equals("O")||task_period.contains("E"))
		{
			if(!b)
			{
				task_time = TransformTimeUtil.getNextDay() + task_time.substring(8,14);
			}
		}
		else
		{
			int middle = 0;//最靠近今日的频率在task_period中的位置
			for(int i = 0; i < task_period.length(); i++)
			{
				if(Integer.valueOf(task_period.substring(i, i+1)) >= dayOfWeek)
				{
					middle = i;
					break;
				}
				middle = 0;
			}	
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			try {
				c.setTime(dateFormat.parse(TransformTimeUtil.GetDay_1()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String nextTime;

			int value = Integer.valueOf(task_period.substring(middle, middle+1));//用户输入的利近日最近的是周几
			if(value == dayOfWeek)//周期包括今日
			{
				if(!b)
				{
					if((middle+1) == task_period.length())
					{
						value = Integer.valueOf(task_period.substring(0,1));
					}
					else
					{
						value = Integer.valueOf(task_period.substring(middle+1,middle+2));
					}
					if(value > dayOfWeek)
					{
						c.add(Calendar.DAY_OF_MONTH, value - dayOfWeek);
					}
					else
					{
						c.add(Calendar.DAY_OF_MONTH, 7-dayOfWeek + value);				
					}
					nextTime=dateFormat.format(c.getTime());
					task_time = nextTime + task_time.substring(8,14);
				}
			}
			else//周期不包括今日
			{		
				if(value > dayOfWeek)
				{
					c.add(Calendar.DAY_OF_MONTH, value - dayOfWeek);
				}
				else
				{
					c.add(Calendar.DAY_OF_MONTH, 7-dayOfWeek + value);						
				}
				nextTime=dateFormat.format(c.getTime());
				task_time = nextTime + task_time.substring(8,14);
			}	
		}
		return task_time;		
	}
	//	/**
	//	 * 得到HOMEID
	//	 */
	//	public static String getHomeID()
	//	{
	//		return str_day;
	//	}
	//	
	
	
	/**
	 * 判断输入的时间是否在系统时间之前
	 * @param date 要判断的时间        格式必须yyyy-MM-dd
	 * @param systemTime 系统时间   格式必须yyyy-MM-dd
	 * @return true：要判断的时间在系统时间之前；false：要判断的时间在系统时间之后或等于今天
	 * @Author:shuntong.cheng
	 * 
	 */
	public static boolean isBeforeSystem(String date,String systemTime){
		
		boolean flag = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			flag = sdf.parse(date).before(sdf.parse(systemTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	
	/**
	 * 判断输入的时间是否在系统时间之前
	 * @param int_hour
	 * @param int_minute
	 * @return true：输入时间在系统时间之前；false：输入时间在系统时间之后
	 */
	public static boolean isBeforeSystem(int int_hour, int int_minute)
	{
		DateFormat system_time = new SimpleDateFormat("mm");
		String str_minute = system_time.format(new Date());
		system_time = new SimpleDateFormat("HH");
		String str_hour = system_time.format(new Date());
		if((Integer.valueOf(str_hour)>int_hour))
		{//判断输入的小时是否在0~23之间且大于系统时间
			return false;
		}
		else if(Integer.valueOf(str_hour) == int_hour)
		{//判断当输入的小时等于系统时间的小时时，分钟数是否大于系统时间的分钟
			if(int_minute <= Integer.valueOf(str_minute))
			{
				return false;
			}
		}
		return true;	
	}
	/**
	 * 得到下一天的日期，格式为yyyyMMdd
	 * @return 返回下一天的日期
	 */
	public static String getNextDay()
	{
		DateFormat day = new SimpleDateFormat("yyyyMMdd");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.DAY_OF_MONTH, 1);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到上个月的月份，格式为yyyyMM
	 * @return 返回上个月的月份
	 */
	public static String getLastMonth()
	{
		DateFormat day = new SimpleDateFormat("yyyyMM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -1);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到上个月的月份，格式为MM
	 * @return 返回上个月的月份
	 */
	public static String getOnlyLastMonth()
	{
		DateFormat day = new SimpleDateFormat("MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -1);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚6个月的月份，格式为MM
	 * @return 返回上个月的月份
	 */
	public static String getOnlySixMonth()
	{
		DateFormat day = new SimpleDateFormat("MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -6);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚6个月的月份，格式为yyyyMM
	 * @return 返回上个月的月份
	 */
	public static String getOnlySixMonthyyyyMM()
	{
		DateFormat day = new SimpleDateFormat("yyyyMM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -6);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚7个月的月份，格式为MM
	 * @return 返回上个月的月份
	 */
	public static String getOnlyPreSevenMonth()
	{
		DateFormat day = new SimpleDateFormat("MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -7);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚12个月的月份，格式为MM
	 * @return 返回上个月的月份
	 */
	public static String getOnlyYearMonth()
	{
		DateFormat day = new SimpleDateFormat("MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -12);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚12个月的月份，格式为yyyyMM
	 * @return 返回上个月的月份
	 */
	public static String getOnlyYearMonthyyyyMM()
	{
		DateFormat day = new SimpleDateFormat("yyyyMM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -12);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}
	/**
	 * 得到前滚24个月的月份，格式为MM
	 * @return 返回上个月的月份
	 */
	public static String getTwoYearMonth()
	{
		DateFormat day = new SimpleDateFormat("MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -24);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}

	/**
	 * 得到前滚24个月的月份，格式为yyyyMM
	 * @return 返回上个月的月份
	 */
	public static String getTwoYearAndMonth()
	{
		DateFormat day = new SimpleDateFormat("yyyyMM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");

		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, -24);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}




	/** 
	 * 获得某一天的前一天日期
	 * @param String类型，某一天日期，例如：20101022
	 * @return  String类型，前一天日期
	 */
	public String getlastDay(String time) throws Exception {
		Calendar         c          = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//设置日期为输入日期
		c.setTime(dateFormat.parse(time));

		// 前一天的日期
		c.add(Calendar.DAY_OF_MONTH, -1);
		String           lastTime   = dateFormat.format(c.getTime());
		return lastTime;
	}

	/**
	 * 获得某一月的前一月
	 * @param String类型，某一月，例如：201010
	 * @return String类型，前一月
	 */
	public String getlastMonth(String time) throws Exception {
		Calendar         c          = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		//设置日期为输入月
		c.setTime(dateFormat.parse(time));

		// 前一个月的日期
		c.add(Calendar.MONTH, -1);
		String lastTime = dateFormat.format(c.getTime());
		return lastTime;
	}

	/**
	 * 获得某一月的总天数
	 * @param String类型，某一月，例如：2010103
	 * @return int类型，天数
	 */
	public static int getMonthSize(String time) {
		int     size    = 0;
		int     year    = Integer.valueOf(time.substring(0, 4));
		int     month   = Integer.valueOf(time.substring(4));
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			size = 31;
			break;
		case 2:
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				size = 29;
			} else {
				size = 28;
			}
			break;
		default:
			size = 30;
			break;
		}
		return size;
	}



	/**
	 * String data 基础时间 yyyyMM
	 * 与基础时间相隔月份数
	 * @return 返回指定月份字符串
	 */
	public static String getStrMonthByBaseMonth(String data,int i)
	{

		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");

		try {
			c.setTime(dateFormat.parse(data));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 下一天的日期
		c.add(Calendar.MONTH, i);
		String nextTime=dateFormat.format(c.getTime());
		return nextTime;
	}

	/**
	 * 检查输入的字符串是否符合月份输入格式
	 * @param 字符串
	 * @return int类型，输入格式超出当前月份：0，输入格式不正确：1，输入格式正确：2
	 */
	public int checkMonth(String time) {
		if (time.length() != 7) {
			return 0;
		}
		if ((!String.valueOf(time.charAt(4)).equals("-"))) {
			return 0;
		}
		DateFormat           year            = new SimpleDateFormat("yyyyMM");
		String               current         = year.format(new Date());
		Pattern              Pattern_gettime = Pattern.compile("-");
		String[]             item            = Pattern_gettime.split(time);
		String               str_year        = item[0] + item[1];
		if (Integer.valueOf(current) < Integer.valueOf(str_year)) {
			return 0;
		}
		String reg_minute = "((^((2\\d{3})"
				+ "|([2-9]\\d{3}))([-])(10|11|12|0?[123456789])$))";
		Pattern p_date = Pattern.compile(reg_minute);
		Matcher m_date = p_date.matcher(time);
		if (m_date.matches()) {
			return 1;
		} else {
			return 0;
		}
	}

	//	/**----------------------系统设置（得到当前日期）------------------*/
	//	public String GetDate()
	//	{	
	//		DateFormat day = new SimpleDateFormat("yyyy-MM-dd");
	//	    String str_day = day.format(new Date());
	//	    return str_day;
	//	}
	//	/**----------------------系统设置（得到当前时间）------------------*/
	public static String GetTimeHour()
	{	
		DateFormat day = new SimpleDateFormat("HH:mm");
		String str_day = day.format(new Date());
		return str_day;
	}
	//	public String GetTimeMinute()
	//	{	
	//		DateFormat day = new SimpleDateFormat("mm");
	//	    String str_day = day.format(new Date());
	//	    return str_day;
	//	}
	//获取今天是星期几
	public static int GetDayOfWeek()
	{
		Calendar calendar = Calendar.getInstance();
		//Date date = new Date();
		//calendar.setTime(date);
		return (calendar.get(Calendar.DAY_OF_WEEK)-1);

	}
	/**
	 * 得到一年的月份，格式为yyyyMM
	 * @return 获取一年的月份
	 */
	public static void GetOneYearMonth(ArrayList<String> month)
	{	
		DateFormat day = new SimpleDateFormat("yyyy-MM");
		String str_day = day.format(new Date());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
		try {
			c.setTime(dateFormat.parse(str_day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int sum = 0;
		String monthItem;
		month.add(str_day);
		for(int i = 0; i < 11; i++)
		{
			c.add(Calendar.MONTH, -1);
			monthItem=dateFormat.format(c.getTime());
			if(Integer.parseInt(monthItem.substring(5))==1)
			{
				month.add(monthItem);
				sum++;
				break;
			}		    
			month.add(monthItem);
			sum++;
		}
		if(sum!=12)
		{			
			int year = Integer.parseInt(str_day.substring(0, 4))-1;
			for(int i = 12; i > sum; i--)
			{
				month.add(String.valueOf(year)+"-"+String.valueOf(i));
			}
		}
		return;
	}

	/**
	 * 四舍五入方法
	 * @param v 被操作值
	 * @param scale 小数点后位数
	 * @return
	 */
	public   static   String   round(double v,int scale){     
		String   temp= "#,##0.";     
		for   (int   i=0;i <scale   ;i++   )     
		{     
			temp+= "0";     
		}     
		if(scale == 0)
		{
			return  (new DecimalFormat(temp).format(v)).replace('.', ' ').trim();    
		}
		else
		{
			return  (new DecimalFormat(temp).format(v));    
		} 
	}  

	/**
	 * 
	 * @param  yyyy-MM-dd HH:mm:ss
	 * @return  yyyyMMdd
	 */
	public static String dateToSring(String strdate){

		SimpleDateFormat   from=new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat   to=new   SimpleDateFormat( "yyyyMMdd"); 
		Date temp = null;
		try {
			temp = from.parse(strdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return to.format(temp);

	}

	/**
	 * 
	 * @param  HH:mm
	 * @return  yyyy-MM-dd HH:mm
	 */
	public static Date sringToDate1(String strdate){
		String t = "2013-08-01 " + strdate;
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd HH:mm"); 
			Date temp = null;
			try {
				temp = to.parse(t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return temp;
		
	}
	/**
	 * 
	 * @param  HH:mm
	 * @return  yyyy-MM-dd HH:mm
	 */
	public static Date sringToDate(String strdate){
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd HH:mm"); 
			Date temp = null;
			try {
				temp = to.parse(strdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return temp;
		
	}
	/**
	 * 
	 * @param  yyyyMM
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String sringToMonth(String strdate){

		try {
			SimpleDateFormat   from=new   SimpleDateFormat( "yyyyMM"); 
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); 
			
			Date temp = null;

			temp = from.parse(strdate);


			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
	
	/**
	 * 
	 * @param  yyyyMM
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String sringToYear(String strdate){

		try {
			SimpleDateFormat   from=new   SimpleDateFormat( "yyyy"); 
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); 
			
			Date temp = null;

			temp = from.parse(strdate);


			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
	
	
	/**
	 * 
	 * @param  yyyyMMdd
	 * @return  yyyy-MM-dd
	 */
	public static String yyyyMMddToyyyy_MM_dd(String strdate){

		try {
			
			SimpleDateFormat   from=new   SimpleDateFormat( "yyyyMMdd"); 
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd"); 
			
			Date temp = null;

			temp = from.parse(strdate);


			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
	
	/**
	 * 
	 * @param  yyyyMMdd
	 * @return  yyyy-MM-dd
	 */
	public static String yyyyMMddToyyyy_MM_dd(String stdate,String sysdate){

		try {
			String strdate=null;
			if(stdate.length()==4){
				strdate = stdate+sysdate.substring(5,7)+ sysdate.substring(8,10);
			}else if(stdate.length()==6){
				strdate = stdate+sysdate.substring(8,10);
			}else{
				strdate=stdate;
			}
			
			SimpleDateFormat   from=new   SimpleDateFormat( "yyyyMMdd"); 
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyy-MM-dd"); 
			
			Date temp = null;

			temp = from.parse(strdate);


			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
	/**
	 * 
	 * @param  yyyyMMdd
	 * @return  yyyy-MM-dd || yyyy-MM || yyyy
	 */
	public static String yyyyMMddTo_date(String strdate){
		
		Date temp = null;
		SimpleDateFormat   from=null;
		SimpleDateFormat   to = null;
		try {
			switch (strdate.length()) {
			case 8:
				from = new   SimpleDateFormat( "yyyyMMdd"); 
			    to = new   SimpleDateFormat( "yyyy-MM-dd"); 
				temp = from.parse(strdate);
				break;
			case 6:
				from = new   SimpleDateFormat( "yyyyMM"); 
			    to = new   SimpleDateFormat( "yyyy-MM"); 
				temp = from.parse(strdate);
				break;
			case 4:
				from = new   SimpleDateFormat( "yyyy"); 
			    to = new   SimpleDateFormat( "yyyy"); 
				temp = from.parse(strdate);
				break;

			default:
				break;
			}
			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
	
	/**
	 * 
	 * @param  yyyy-MM-dd
	 * @return  yyyyMMdd
	 */
	public static String yyyy_MM_ddToyyyyMMdd(String strdate){

		try {
			
			SimpleDateFormat   from=new   SimpleDateFormat( "yyyy-MM-dd"); 
			SimpleDateFormat   to=new   SimpleDateFormat( "yyyyMMdd"); 
			Date temp = null;

			temp = from.parse(strdate);


			return to.format(temp);
		} catch (Exception e) {
			// TODO: handle exception
			return "";//如果异常返回空串

		}
	}
}
