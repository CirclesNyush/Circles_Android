package com.example.anpu.circles.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by eric on 2018/2/2.
 */

public class MD5Util {
        public static String getMD5Str(String str) {
            MessageDigest messageDigest = null;

            try {
                messageDigest = MessageDigest.getInstance("MD5");

                messageDigest.reset();

                messageDigest.update(str.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("NoSuchAlgorithmException caught!");
                System.exit(-1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] byteArray = messageDigest.digest();

            StringBuilder md5StrBuff = new StringBuilder();

            for (byte aByteArray : byteArray) {
                if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
            }
            return md5StrBuff.substring(8, 24).toUpperCase();
        }


        public static String getMD5(String str) {
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                md5.update((str).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte b[] = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer("");

            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        }
}
