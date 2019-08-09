package com.dozenx.web.util;


import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: NetWorkUtil
 * @Description: 网络相关工具类
 * @author: Dingxc
 * @date: 2015年7月24日 上午11:07:24
 * @version: 0.0.1
 */
public class NetWorkUtil {

    /**
     * @Title: getLocalIPAddress
     * @Description: 获得本地机器IP地址
     * @return  String
     * @throws
     */
    public static String getLocalIPAddress() {
        InetAddress addr = null;
        String ipAddrStr = "";
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (null != addr) {
            byte[] ipAddr = addr.getAddress();

            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
        }
        return ipAddrStr;
    }

    /**
     * @Title: getRequestIPAddress
     * @Description: 获得请求方的IP地址
     * @param request HttpServletRequest
     * @return  String
     * @throws
     */
    public static String getRequestIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
