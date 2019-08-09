package com.dozenx.common.util;

import java.util.HashMap;

/**
 * Created by luying on 16/12/3.
 */
public class HttpHeader {
    public String Origin="";
    public String Host="";
    public String Referer="";
    public String Cache_Control="max-age=0";
    public String Connection="keep-alive";
    public int  Content_Length;
    public String Accept_Encoding="identity";//gzip, deflate
    //public String Accept_Encoding="gzip, deflate";
    public String Accept_Language="zh-CN";//q代表用户喜好度
    public String User_Agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    public String Content_Type ="application/x-www-form-urlencoded";
    public String Accept= "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,**/*//*;q=0.8";
    public String Cookie="";
    public HashMap<String,String> cookies =new HashMap<String,String>() ;

    public HashMap<String,String> params =new HashMap<String,String>();
//    /*curl 'http://www.mayadisc.com/logging.php?action=login&'
//            -H 'Cookie: is_use_cookiex=yes; cdb_cookietime=2592000; cdb_oldtopics=D2094663D; cdb_fid5=1480639303; cdb_sid=FwV7KV; is_use_cookied=yes'
//            -H 'Origin: http://www.mayadisc.com'
//            -H 'Accept-Encoding: gzip, deflate'
//            -H 'Accept-Language: zh-CN,zh;q=0.8'
//            -H 'Upgrade-Insecure-Requests: 1'
//            -H 'User-Agent:
//            -H 'Content-Type: application/x-www-form-urlencoded'
//              -H 'Accept: '
//            -H 'Cache-Control: max-age=0' -H 'Referer: http://www.mayadisc.com/logging.php?action=login'
//            -H 'Connection: keep-alive'
//            --data 'formhash=afabcdb9&referer=index.php&loginfield=username&username=zjlgdxfj&password=123456fj&questionid=0&answer=&cookietime=2592000&loginmode=&styleid=&loginsubmit=%CC%E1+%26%23160%3B+%BD%BB'
//            --compressed*/
}
