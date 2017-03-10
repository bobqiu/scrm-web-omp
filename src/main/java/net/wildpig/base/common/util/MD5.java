

package net.wildpig.base.common.util;

import java.security.MessageDigest;

/**
 * @FileName MD5.java
 * @Description: 
 *
 * @Date Apr 19, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	public static void main(String[] args) {
		System.out.println(md5("fsafs"+"123456"));
		System.out.println(md5("dfafds"));
	}
}
