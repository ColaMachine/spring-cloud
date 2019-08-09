package com.dozenx.web.core.sms.bean;

import java.util.LinkedList;

/**
 * 主要存放在缓存用来比较验证码的正确性及有效性
 * Created by dozen.zhang on 2016/3/11.
 */
public class SmsHistory {
    String phone;
    private String code;

    public int getErrTrys() {
        return errTrys;
    }

    public void setErrTrys(int errTrys) {
        this.errTrys = errTrys;
    }

    private int errTrys;


    public Long getLast() {
        return last;
    }

  public void setLast(Long last) {       this.last = last;
    }

    Long last;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LinkedList getTimes() {
        return times;
    }

    public void setTimes(LinkedList times) {
        this.times = times;
    }

    LinkedList times = new LinkedList();

/*
    public static void main(String[] args) {
        SmsHistory history = new SmsHistory();
        history.phone="13958173965";
        history.times.offer(new Date().getTime());
        history.times.offer(new Date().getTime());
        history.times.offer(new Date().getTime());
        history.times.offer(new Date().getTime());
        history.times.offer(new Date().getTime());

        String s = JSON.toJSONString(history);
        SmsHistory history1 =(SmsHistory)JSON.parseObject(s,SmsHistory.class);
        System.out.print(JSON.toJSONString(history1));
    }
    private static void printStack(Stack<Integer> stack ){
        if (stack.empty())
            System.out.println("堆栈是空的，没有元素");
        else {
            System.out.print("堆栈中的元素：");
            Enumeration items = stack.elements(); // 得到 stack 中的枚举对象
            while (items.hasMoreElements()) //显示枚举（stack ） 中的所有元素
                System.out.print(items.nextElement()+" ");
        }
        System.out.println(); //换行
    }*/
}

