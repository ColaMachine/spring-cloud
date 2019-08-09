package com.dozenx.common.util;

//import org.springframework.util.DigestUtils;

import com.dozenx.common.util.ByteUtil;
import com.dozenx.common.util.FileUtil;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author 张智威
 *         2017年4月10日 上午10:07:58
 */
public class StringUtil {
    public static String EMPTY = "";

    /**
     * 判断是否是url
     *
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:08:51
     */
    public static boolean isUrl(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.matches("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是为空
     *
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:09:11
     */
    private static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是为空
     *
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:09:11
     */
    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为null
     *
     * @param obj
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:09:50
     */
    public static boolean isNull(Object obj) {
        if (obj == null)
            return true;

        if (obj.getClass() == Integer.class || obj.getClass() == double.class || obj.getClass() == Float.class) {

            return (int) obj == 0;
        }
        return false;
    }

    /**
     * 判断是否不为空
     *
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:10:21
     */
    public static boolean isNotBlank(String str) {

        return !isEmpty(str);
    }
    /**
     * 判断是否不为空
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:10:21
     */
    /*public static boolean  isNotEmpty(String str){
		
		return !isEmpty(str);
	}*/

    /**
     * 判断是否为邮箱地址
     *
     * @param email
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:10:48
     */
    public static boolean checkEmail(String email) {
        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     * @author 张智威
     * @date 2017年4月10日 上午10:10:59
     */

    public static boolean checkNumeric(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否日期字符串
     *
     * @param str
     * @param format
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19* @return
     */
    public static boolean checkDateStr(String str, String format) {
        format = format.replaceAll("[yMdhHms]", "\\\\d");
        return str.matches(format);
    }

    /**
     * 验证用户名称有效性
     *
     * @param username
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static boolean checkUserNameValid(String username) {
        String regex = "/^[0-9A-Za-z]*[a-zA-Z]+[0-9A-Za-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    /**
     * 检查是否是float 类型数据
     *
     * @param value    被检查字符串
     * @param integer  整数位长度
     * @param fraction 小数位长度
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static boolean checkFloat(String value, int integer, int fraction) {
        String regex = String.format("^[+|-]?\\d{1,%d}(\\.\\d{1,%d})?$", integer, fraction);
        if (fraction == 0) {
            regex = String.format("^[+|-]?\\d{1,%d}$", integer);
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * 转成首字母大写
     *
     * @param abc
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static String getAbc(String abc) {
        return abc.substring(0, 1).toUpperCase() + abc.substring(1);
    }

    /**
     * 转成首字母小写
     *
     * @param abc
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static String getabc(String abc) {
        return abc.substring(0, 1).toLowerCase() + abc.substring(1);
    }

    /**
     * 根据间隔符合连起来
     *
     * @param join
     * @param strAry
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static String join(String join, Object[] strAry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.length; i++) {
            if (i == (strAry.length - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(join);
            }
        }
        return new String(sb);
    }

    /**
     * 从字符串中提取对应开始结束字符串中间值
     *
     * @param content
     * @param a
     * @param b
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static String getContentBetween(String content, String a, String b) {
        int index = content.indexOf(a) + a.length();
        int last = content.lastIndexOf(b);
        return content.substring(index, last);
    }

    /**
     * 判断是否手机号码
     *
     * @param value
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static boolean isPhone(String value) {
        String regex = "^[1][0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * 判断是否是邮箱地址
     *
     * @param value
     * @return
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static boolean isEmail(String value) {
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean isID(String value) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static boolean splitStrContains(String longStr, String bean) {
        if (longStr.indexOf("," + bean + ",") >= 0 || longStr.startsWith(bean + ",") || longStr.endsWith("," + bean) || longStr.equals(bean)) {
            return true;
        }
        return false;
    }


    /**
     * 随机数字池
     **/
    public static String randDigitString = "0123456789";//随机产生的字符串

    /**
     * 随机字母池
     **/
    private static String randAlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    private static String randAlphaDigitString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串

    public static String getRandomString(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            code += (randDigitString + randAlphaString).charAt((int) (Math.random() * 10));
        }
        return code;
    }

    /**
     * 说明:得到随机字母字符串
     *
     * @param len
     * @return String
     * @author dozen.zhang
     * @date 2016年3月18日下午9:07:32
     */
    public static String getRandomAlphaString(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            code += randAlphaString.charAt((int) (Math.random() * randAlphaString.length()));
        }
        return code;
    }

    /**
     * 说明:得到随机字母字符串
     *
     * @param len
     * @return String
     * @author dozen.zhang
     * @date 2016年3月18日下午9:07:32
     */
    public static String getRandomAlphaDigitString(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            code += randAlphaDigitString.charAt((int) (Math.random() * randAlphaDigitString.length()));
        }
        return code;
    }

    /**
     * 说明:获得随机数字字符串
     *
     * @param len
     * @return String
     * @author dozen.zhang
     * @date 2016年3月18日下午9:07:00
     */
    public static String getRandomDigitString(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            code += randDigitString.charAt((int) (Math.random() * randDigitString.length()));
        }
        return code;
    }

    /**
     * 说明:检查是否只有字母和数字
     *
     * @param str
     * @return boolean
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:40
     */
    public static boolean checkAlphaNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isAlphabetic(str.charAt(i)) == false && Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 说明:检查是否是只有字母
     *
     * @param str
     * @return boolean
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:01
     */
    public static boolean checkAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isAlphabetic(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 说明:检验年月日格式
     *
     * @param type
     * @return String
     * @author dozen.zhang
     * @date 2016年3月18日下午9:06:19
     */
    public static String getYMDStr(String type) {
        String ymd = "";
        if (type.startsWith("date")) {
            ymd = "yyyy-MM-dd";
        } else {
            ymd = "yyyy-MM-dd HH:mm:ss";
        }
        return ymd;
    }


    /**
     * md5加密
     * @param str
     * @return
     */
//	public static String getMd5Str(String str) {
//		if(str == null) return "";
//		byte[] pb = null;
//		try {
//			pb = str.getBytes("utf-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return DigestUtils.md5DigestAsHex(pb);
//	}

    /**
     * 获取异常堆栈信息
     *
     * @param e
     * @return
     */
    public static String getExceptionStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }

    /**
     * 转义get方式提交的中文字符
     *
     * @param str
     * @return
     */
    public static String fromGetRequest(String str) {
        String result = "";
        try {
            if (str == null || str.equals("")) return "";
            result = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串转成byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        int hexLength = length;
        while (hexLength % 8 != 0) {
            hexLength++;
        }
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[hexLength];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符串转16进制字符串
     *
     * @param str
     * @return
     */
    public static String stringToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转字符串
     *
     * @param str
     * @return
     */
    public static String hexStringToString(String str) {
        byte[] baKeyword = new byte[str.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            str = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return str;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 加密算法
     *
     * @param data 加密数据
     * @param key  秘钥
     * @return 加密结果
     */
    public static byte[] desEnCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            // 创建Cipher对象
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            // 加解密
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密算法
     *
     * @param data 解密数据
     * @param key  秘钥
     * @return 解密结果
     */
    public static byte[] desDeCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DES加密方法
     *
     * @param message   加密数据
     * @param keyString 密钥
     * @return 加密结果
     */
    public static String encryptByDes(String message, String keyString) {
        String dataHexString = stringToHexString(message);
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesToHexString(result);
    }

    /**
     * DES解密方法
     *
     * @param dataHexString 解密数据
     * @param keyString     密钥
     * @return
     */
    public static String decryptByDes(String dataHexString, String keyString) {
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStringToString(bytesToHexString(result)).trim();
    }

    /**
     * AES解密方法
     *
     * @param dataHexString 解密数据
     * @param decryptKey    密钥
     * @return
     */
    public static String aesDecryptByBytes(String dataHexString, String decryptKey) {
        if (StringUtil.isEmpty(dataHexString)) {
            return "";
        }
        byte[] decryptBytes = null;
        try {
            byte[] encryptBytes = StringUtil.base64Decode(dataHexString);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(decryptKey.getBytes());
            kgen.init(128, secureRandom);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            decryptBytes = cipher.doFinal(encryptBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStringToString(bytesToHexString(decryptBytes)).trim();
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return new BASE64Decoder().decodeBuffer(base64Code);
    }

    static HashMap<Character, Character> gbkMap = null;

    //http://www.cnblogs.com/human/p/3517607.html
    public static String byte2Str(byte[] bytes, String charset) throws Exception {
        StringBuffer returnStr = new StringBuffer();
        int unicode = 0;
        // byte[] bytes =new byte[4];
        String unicodeBiteStr = "";
        if (charset.equals("gbk")) {
            if (gbkMap == null) {
                gbkMap = new HashMap<Character, Character>();
                List<String> lists = new ArrayList<String>();
                lists = FileUtil.readFile2List("/Users/luying/Documents/workspace/calendar/gbk2unicode");
                for (String line : lists) {
                    if (!StringUtil.isBlank(line)) {
                        String[] ary = line.split(" ");

                        // byte gbkByte =Byte.parseByte(ary[0],16);
                        // byte unicodeByte  =Byte.parseByte(ary[1],16);
                        byte[] gbkBytes = com.dozenx.common.util.ByteUtil.hexStr2Bytes(ary[0]);
                        char gbkByte = com.dozenx.common.util.ByteUtil.getChar(gbkBytes);//char)(gbkBytes[0]<<4+gbkBytes[1]);

                        byte[] unicodeBytes = com.dozenx.common.util.ByteUtil.hexStr2Bytes(ary[2]);
                        char unicodeByte = com.dozenx.common.util.ByteUtil.getChar(unicodeBytes);//(char)(unicodeBytes[0]<<4+unicodeBytes[1]);


                        gbkMap.put(gbkByte, unicodeByte);
                    }
                }
            }
            //Byte.parseByte("")
            //newByte= Byte.parseByte("B8F6",16);
            //byte[] newBytes= ByteUtil.hexStr2Bytes("B8F6");
            //char newChar = ByteUtil.getChar(newBytes);
            //gbkMap.get(newChar);*/
            //char unicodeChar1 = gbkMap.get(newChar);
            //System.out.println(unicodeChar1);


            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] > 0 && bytes[i] <= 107) {
                    returnStr.append((char) bytes[i]);
                    //s+=;
                } else {
                    byte[] byteAry = new byte[2];
                    byteAry[0] = bytes[i];
                    byteAry[1] = bytes[i + 1];
                    char ch = com.dozenx.common.util.ByteUtil.getChar(byteAry);
                    //int ch = (bytes[i]<<4+bytes[i+1]);
                    i++;
                    char unicodeChar = gbkMap.get(ch);
                    //s+=unicodeChar;
                    returnStr.append((char) bytes[i]);
                }
            }

        } else {//什么是utf-8 utf-8 有明显的特征他的第一位有几个1说明她有几位 后面的几位都是01开头的s
            //boolean start=false;
            int weishu = 0;
            for (int i = 0; i < bytes.length; i++) {
                int nowIndex = i;
                weishu = 0;
                int[] biteAry = com.dozenx.common.util.ByteUtil.to8BinaryInt(bytes[i]);
                //开始计算有几个头部1
                String newUnicodeBiteStr = "";
                for (int j = 0; j < biteAry.length; j++) {
                    if (biteAry[j] == 1) {
                        weishu++;
                    } else {
                        break;
                    }

                }
                //得到剩余的比特
                for (int j = weishu + 1; j < 8; j++) {
                    newUnicodeBiteStr += biteAry[j];

                }
                //将剩余的数值转换成unicode值
                for (int biteLeft = 2; biteLeft <= weishu; biteLeft++) {
                    i++;
                    int[] nextBiteAry = com.dozenx.common.util.ByteUtil.to8BinaryInt(bytes[i]);
                    if (nextBiteAry[0] != 1 || nextBiteAry[1] != 0) {
                        throw new Exception("it's not utf-8 charset encode utf-8 must begin with 10");
                    }
                    for (int k = 2; k < 8; k++) {
                        newUnicodeBiteStr += nextBiteAry[k];
                    }

                }

                /*if(ByteUtil.to8BinaryInt(bytes[i])[0]==0){
                    //说明是什么呢 说明是单字节字符
                    char chars=(char) bytes[i];
                    s+=String.valueOf(chars);
                    start=false;
                }else{
                    if(!start){
                        String binaryStr = ByteUtil.to8BinaryStr(bytes[i]);
                        int oneNum =1;
                        int zeroIndex=1;
                        while(binaryStr.charAt(zeroIndex)=='1'){
                            zeroIndex++;
                        }
                        weishu=zeroIndex+1;
                        if(weishu>4){
                            System.out.println("该位数超过了4为");
                        }

                    }
                }*/

                //把那个转成unicode代表的字符

                // System.out.println((char)ByteUtil.binaryStr2int(newUnicodeBiteStr));
                returnStr.append((char) ByteUtil.binaryStr2int(newUnicodeBiteStr));
            }
        }
        return returnStr.toString();

    }

    public static void main(String args[]) {
        System.out.println(getKuoHaoValue("ssdfasdf(123)asdf"));
        try {
            String s = StringUtil.buling(3, 11);
            System.out.println(s);
//        String s = "http://61.154.14.180:18001/auth/welcome.jsp?dev_info={%22belongTo%22:%22fujian%22,%22corp%22:%22ZTE%22,%22devId%22:%22FatAP_31_20160405b52922b8-5559-4785-886a-c7cdd490b585%22,%22devName%22:%22FatAp-228-3941-8C7967044A74%22,%22mac%22:%228C7967044A74%22,%22merCascade%22:%22210980%22,%22merId%22:%22210980%22,%22model%22:%22ZXWL-WR100%22,%22region%22:%22330105%22,%22ssid%22:%22aWiFi-YYT%22,%22type%22:%22A%22}&gw_port=2060&global_value=8114E757A2_FatAP_31_20160405b52922b8-5559-4785-886a-c7cdd490b585_B4B676787CBA&global_key=portal_with_auth&dev_id=FatAP_31_20160405b52922b8-5559-4785-886a-c7cdd490b585&user_mac=B4B676787CBA&gw_address=192.168.10.1&url=http://www.baidu.com/&login_type=unauth&";
//
//			s= URLDecoder.decode(s);
//			System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param num
     * @param length
     * @return
     * @throws Exception
     */
    public static String buling(int num, int length) throws Exception {
        String code = num + "";
        if (code.length() > length) {
            throw new Exception("补零时发现数字长度大于要求长度" + code.length() + " length" + length);

        }
        return String.format("%0" + length + "d", Integer.parseInt(code));
    }


    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    public  static String getKuoHaoValue(String s){
        if(StringUtil.isBlank(s)){
            return null;
        }
        int firstKuoHao = s.indexOf("(");
        int sendKuoHao =s.indexOf(")");
        String value = s.substring(firstKuoHao+1,sendKuoHao);
        return value;
    }
    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtil.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }
    public static String getStringValue(String s){
        if(s==null){
            return "";
        }else{
            return s;
        }
    }
    public static String defaultString(String str) {
        return str == null ? "" : str;
    }
    public static String defaultString(String str,String zzz) {
        return str == null ? zzz : str;
    }
}
