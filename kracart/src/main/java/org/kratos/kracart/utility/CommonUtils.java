package org.kratos.kracart.utility;

import java.util.Random;

import org.springframework.util.DigestUtils;

public abstract class CommonUtils {

	public static String encryptString(String plain) {
		String password = "";
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			password += String.valueOf(rand.nextInt(9));
		}
		String salt = md5Encrypt(password).substring(0, 2);
		password = md5Encrypt(salt + plain) + ":" + salt;
		return password;
	}
	
	public static String createRandomString(int length) {
		String pattern = "abcdefghijklmnopqrstuvwxyz01234567890";
		String randStr = "";
		Random rand = new Random();
		while(randStr.length() < length) {
			int pos = rand.nextInt(pattern.length());
			randStr += pattern.substring(pos, pos + 1);
		}
		return randStr;
	}
	
	private static String md5Encrypt(String plain) {
		return DigestUtils.md5DigestAsHex(plain.getBytes());
		
	}
	
}
