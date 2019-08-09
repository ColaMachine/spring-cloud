package com.dozenx.common.util;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTime
  implements Comparable<Object>
{
  protected long value = 0L;

  protected boolean dateOnly = false;

  protected Integer tzShift = null;
  public static final Pattern dateTimePattern;
  public static final Pattern datePattern;
  public static final Pattern dateTimeChoicePattern;
  private static final SimpleDateFormat dateTimeFormat822;
  private static final TimeZone GMT;

  public DateTime()
  {
  }

  public DateTime(long value)
  {
    this.value = value;
  }

  public DateTime(Date value)
  {
    this.value = value.getTime();
  }

  public DateTime(long value, int tzShift)
  {
    this.value = value;
    this.tzShift = new Integer(tzShift);
  }

  public DateTime(Date value, TimeZone zone)
  {
    this.value = value.getTime();
    this.tzShift = Integer.valueOf(zone.getOffset(value.getTime()) / 60000);//以分钟为单位
  }

  public static DateTime now()
  {
    return new DateTime(new Date(), GMT);
  }

  public long getValue()
  {
    return this.value; } 
  public void setValue(long v) { this.value = v; }


  public boolean isDateOnly()
  {
    return this.dateOnly; } 
  public void setDateOnly(boolean v) { this.dateOnly = v; }


  public Integer getTzShift()
  {
    return this.tzShift; } 
  public void setTzShift(Integer v) { this.tzShift = v; }


  public int hashCode()
  {
    return Long.valueOf(this.value).hashCode();
  }

  public boolean equals(Object o)
  {
    if ((o instanceof DateTime))
    {
      return this.value == ((DateTime)o).value;
    }
    if ((o instanceof Date))
    {
      return this.value == ((Date)o).getTime();
    }

    return false;
  }

  public int compareTo(Object o)
  {
    if ((o instanceof DateTime))
      return new Long(this.value).compareTo(new Long(((DateTime)o).value));
    if ((o instanceof Date)) {
      return new Long(this.value).compareTo(new Long(((Date)o).getTime()));
    }
    throw new RuntimeException("Invalid type.");
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    Calendar dateTime = new GregorianCalendar(GMT);
    long localTime = this.value;
    if (this.tzShift != null) {
      localTime += this.tzShift.longValue() * 60000L;
    }

    dateTime.setTimeInMillis(localTime);
    try
    {
      appendInt(sb, dateTime.get(1), 4);
      sb.append('-');
      appendInt(sb, dateTime.get(2) + 1, 2);
      sb.append('-');
      appendInt(sb, dateTime.get(5), 2);

      if (!this.dateOnly)
      {
        sb.append('T');
        appendInt(sb, dateTime.get(11), 2);
        sb.append(':');
        appendInt(sb, dateTime.get(12), 2);
        sb.append(':');
        appendInt(sb, dateTime.get(13), 2);

        if (dateTime.isSet(14)) {
          sb.append('.');
          appendInt(sb, dateTime.get(14), 3);
        }
      }

      if (this.tzShift != null)
      {
        if (this.tzShift.intValue() == 0)
        {
          sb.append('Z');
        }
        else
        {
          int absTzShift = this.tzShift.intValue();
          if (this.tzShift.intValue() > 0) {
            sb.append('+');
          } else {
            sb.append('-');
            absTzShift = -absTzShift;
          }

          int tzHours = absTzShift / 60;
          int tzMinutes = absTzShift % 60;
          appendInt(sb, tzHours, 2);
          sb.append(':');
          appendInt(sb, tzMinutes, 2);
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new RuntimeException(e);
    }

    return sb.toString();
  }

  public String toStringRfc822()
  {
    assert (!this.dateOnly);
    synchronized (dateTimeFormat822) {
      return dateTimeFormat822.format(Long.valueOf(this.value));
    }
  }

  public static DateTime parseRfc822(String str)
    throws  java.text.ParseException
  {
    Date date;
    synchronized (dateTimeFormat822) {
      try {
        date = dateTimeFormat822.parse(str);
      } catch (java.text.ParseException e) {
        throw e;
      }
    }

    return new DateTime(date);
  }

  public String toUiString()
  {
    StringBuilder sb = new StringBuilder();

    Calendar dateTime = new GregorianCalendar(GMT);
    long localTime = this.value;
    if (this.tzShift != null) {
      localTime += this.tzShift.longValue() * 60000L;
    }

    dateTime.setTimeInMillis(localTime);
    try
    {
      appendInt(sb, dateTime.get(1), 4);
      sb.append('-');
      appendInt(sb, dateTime.get(2) + 1, 2);
      sb.append('-');
      appendInt(sb, dateTime.get(5), 2);

      if (!this.dateOnly)
      {
        sb.append(' ');
        appendInt(sb, dateTime.get(11), 2);
        sb.append(':');
        appendInt(sb, dateTime.get(12), 2);
      }
    }
    catch (ArrayIndexOutOfBoundsException e) {
      throw new RuntimeException(e);
    }

    return sb.toString();
  }

  public static DateTime parseDateTime(String str)
    throws NumberFormatException
  {
    Matcher m = str == null ? null : dateTimePattern.matcher(str);

    if ((str == null) || (!m.matches())) {
      throw new NumberFormatException("Invalid date/time format.");
    }

    DateTime ret = new DateTime();
    ret.dateOnly = false;

    if (m.group(9) != null)
    {
      if (m.group(9).equalsIgnoreCase("Z")) {
        ret.tzShift = new Integer(0);
      } else {
        ret.tzShift = new Integer(Integer.valueOf(m.group(12)).intValue() * 60 + Integer.valueOf(m.group(13)).intValue());

        if (m.group(11).equals("-")) {
          ret.tzShift = new Integer(-ret.tzShift.intValue());
        }
      }
    }
    Calendar dateTime = new GregorianCalendar(GMT);

    dateTime.clear();
    dateTime.set(Integer.valueOf(m.group(1)).intValue(), Integer.valueOf(m.group(2)).intValue() - 1, Integer.valueOf(m.group(3)).intValue(), Integer.valueOf(m.group(4)).intValue(), Integer.valueOf(m.group(5)).intValue(), Integer.valueOf(m.group(6)).intValue());

    if ((m.group(8) != null) && (m.group(8).length() > 0)) {
      BigDecimal bd = new BigDecimal("0." + m.group(8));

      dateTime.set(14, bd.movePointRight(3).intValue());
    }

    ret.value = dateTime.getTimeInMillis();
    if (ret.tzShift != null) {
      ret.value -= ret.tzShift.intValue() * 60000;
    }

    return ret;
  }

  public static DateTime parseDate(String str)
    throws NumberFormatException
  {
    Matcher m = str == null ? null : datePattern.matcher(str);

    if ((str == null) || (!m.matches())) {
      throw new NumberFormatException("Invalid date format.");
    }

    DateTime ret = new DateTime();
    ret.dateOnly = true;

    if (m.group(4) != null)
    {
      if (m.group(4).equalsIgnoreCase("Z")) {
        ret.tzShift = new Integer(0);
      } else {
        ret.tzShift = new Integer(Integer.valueOf(m.group(7)).intValue() * 60 + Integer.valueOf(m.group(8)).intValue());

        if (m.group(6).equals("-")) {
          ret.tzShift = new Integer(-ret.tzShift.intValue());
        }
      }
    }
    Calendar dateTime = new GregorianCalendar(GMT);

    dateTime.clear();
    dateTime.set(Integer.valueOf(m.group(1)).intValue(), Integer.valueOf(m.group(2)).intValue() - 1, Integer.valueOf(m.group(3)).intValue());

    ret.value = dateTime.getTimeInMillis();
    if (ret.tzShift != null) {
      ret.value -= ret.tzShift.intValue() * 60000;
    }

    return ret;
  }

  public static DateTime parseDateTimeChoice(String value)
    throws NumberFormatException
  {
    try
    {
      return parseDateTime(value);
    } catch (NumberFormatException e) {
      NumberFormatException exception = e;
      try
      {
        return parseDate(value);
      } catch (NumberFormatException e1) {
        exception = e1;
      }

      throw exception;
    }
  }

  private static void appendInt(StringBuilder sb, int num, int numDigits)
  {
    if (num < 0) {
      sb.append('-');
      num = -num;
    }

    char[] digits = new char[numDigits];
    for (int digit = numDigits - 1; digit >= 0; digit--) {
      digits[digit] = ((char)(48 + num % 10));
      num /= 10;
    }

    sb.append(digits);
  }

  static
  {
    dateTimePattern = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)(\\.(\\d+))?([Zz]|((\\+|\\-)(\\d\\d):(\\d\\d)))?");

    datePattern = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)([Zz]|((\\+|\\-)(\\d\\d):(\\d\\d)))?");

    dateTimeChoicePattern = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)([Tt](\\d\\d):(\\d\\d):(\\d\\d)(\\.(\\d+))?)?([Zz]|((\\+|\\-)(\\d\\d):(\\d\\d)))?");

    dateTimeFormat822 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    GMT = TimeZone.getTimeZone("GMT");

    dateTimeFormat822.setTimeZone(GMT);
  }
  
  public static void main(String args[]){
	  
	/*  Matcher m1 =  dateTimePattern.matcher("2014-04-16t14:06:00");
	  System.out.println(m1.matches());
	  System.out.println(m1.group(1));*/
	  //查看 TimeZone为参数的构造方法
	 /* DateTime dt =new DateTime(new Date(),TimeZone.getTimeZone("GMT+9"));
	  System.out.println(dt.toString());*/
	  //2014-04-13T15:17:36.522+09:00
	  
	  DateTime dt =new DateTime(new Date());
	  System.out.println("查看tzshift究竟是什么东西:"+dt.tzShift+
			  "\r\n toString是什么内容:"+dt.toString());
	  //说明任何时间在里面都是以格林尼治时间保存的
	  //
	//  DateTime dateTime = DateTime.parseDateTime("2014-04-13t11:57:00");
	 // dateTime.set
  }
}