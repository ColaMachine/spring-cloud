package com.dozenx.common.util.encrypt;

import com.dozenx.common.util.CommonUtils;
import com.dozenx.common.util.encrypt.SHA1;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;


/**
 * 字符串的加密和解密
 * @author awifi-core
 * @date 2015年1月6日 下午6:07:31
 */
public final class EncryptUtil {
	/**
	 * 
	 */
	private static final String ALGORITHM = "DES";

	/**
	 * The Default Key.
	 */
	public static final String DEFAULT_KEY = "asdfsadf@#$%^$%^%^&*&asdf24243423234";

	/**
	 * 说明:加密
	 * @param originalString 原始字符串（待加密）
	 * @return 
	 * @throws Exception
	 */
	public static String encrypt(final String originalString) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
		return CommonUtils.parseHexStringFromBytes(bEn);
	}

	/**
	 * 说明:加密
	 * @param originalString 原始字符串（待加密）
	 * @param key 按自定义键值加密
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(final String originalString, final String key) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
		return CommonUtils.parseHexStringFromBytes(bEn);
	}

	/**
	 * 加密实际方法
	 * @param originalByte 原始字符数组（待加密）
	 * @param key 加密键值
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源 
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象 
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成 
		// 一个SecretKey对象 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作 
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
		//执行加密操作
		return cipher.doFinal(originalByte);

	}

	/**
	 * 解密
	 * @param encryptedString 密文字符串（待解密）
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedString) throws Exception {
		byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
		return new String(orginal);
	}

	/**
	 * 解密
	 * @param encryptedString 密文字符串（待解密）
	 * @param key 解密键值
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedString, final String key) throws Exception {
		byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, key.getBytes());
		return new String(orginal);
	}

	/**
	 * 解密实际方法
	 * @param encryptedByte 密文字符数组（待解密）
	 * @param key 解密键值
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//加密和解密的区别在第一个参数
		cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
		return cipher.doFinal(encryptedByte);
	}
	
	public static void main(String[] args) {
		String username_id = "石鹏皮皮@126.com";
		try { 
			String cookieValue = EncryptUtil.encrypt(username_id);
			System.out.println(cookieValue);
			String value = EncryptUtil.decrypt(cookieValue);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * byte数组转换成16进制字符串
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
	 * @param
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString){
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		int hexLength = length;
		while(hexLength % 8 != 0){
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
	 * @param data	加密数据
	 * @param key	秘钥
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
	 * @param data	解密数据
	 * @param key	秘钥
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
	 * @param message	加密数据
	 * @param keyString	密钥
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
	 * @param dataHexString	解密数据
	 * @param keyString	密钥
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

	public static String encryptBySHA1(String content){
		com.dozenx.common.util.encrypt.SHA1 sha1 = new SHA1();
		return sha1.getDigestOfString(content.getBytes());
	}

}
