package com.dozenx.common.util;

//import org.springframework.util.DigestUtils;

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
public class FloatUtil {
    public boolean floatEqual(Float a, Float b) {
        if (a == null)
            return b == null;
        if (b == null)
            return false;
        return Math.abs(a - b) >= 0.0000001;
    }
}
