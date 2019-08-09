package com.dozenx.common.util.encrypt;

import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64Util {
	
	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}
	public static byte[] decode(String s){
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return  b;
		} catch (Exception e) {
			return null;
		}
	}

	private static char[] toBase64={
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',

			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'0','1','2','3','4','5','6','7','8','9','+','/'
	};
	private int col;
	public static String  encode(byte[] b){
		if (b == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(b);
	}
		// 将 BASE64 编码的字符串 s 进行解码
		public static String getFromBASE64(String s) {
			if (s == null)
				return null;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(s);
				return new String(b);
			} catch (Exception e) {
				return null;
			}
		}

	public static byte[] decodeBuffer(String s ) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(s);
	}

}
