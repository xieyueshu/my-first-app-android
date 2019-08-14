package com.example.myfirstapp;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.provider.Contacts.SettingsColumns.KEY;

public class DemoJCAJCE {

    /**
     * AES加密解密演示过程
     *
     * @throws Exception
     */
    public static void demoAES() throws Exception {

        String content = "test data";

        //生成密钥
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //加密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypt = cipher.doFinal(content.getBytes("UTF-8"));

        //解密
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipherDecrypt.getBlockSize()]));
        byte[] decrypt = cipherDecrypt.doFinal(encrypt);

        boolean same = content.equals(new String(decrypt, "UTF-8"));

        System.out.println("明文：" + content);
        System.out.println("密钥：" + bytes2HexString(skeySpec.getEncoded()));
        System.out.println("密文：" + bytes2HexString(encrypt));
        System.out.println("解密结果：" + new String(decrypt, "UTF-8"));
        System.out.println("是否一致：" + same);
    }


    public static void demoAES2() throws Exception {

        String content = "test data";

        //已经生成的密钥
        byte[] secretKey = hex2byte("EDCBDC888AB539795F1466E93258B4BC");

        SecretKeySpec skeySpec = new SecretKeySpec(secretKey, "AES");

        //加密的密文
        byte[] encrypt = hex2byte("428425CF9EC99B6F5905D9D2FB13A87A");

        //解密
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipherDecrypt.getBlockSize()]));
        byte[] decrypt = cipherDecrypt.doFinal(encrypt);

        boolean same = content.equals(new String(decrypt, "UTF-8"));

        System.out.println("明文：" + content);
        System.out.println("密钥：" + bytes2HexString(skeySpec.getEncoded()));
        System.out.println("密文：" + bytes2HexString(encrypt));
        System.out.println("解密结果：" + new String(decrypt, "UTF-8"));
        System.out.println("是否一致：" + same);
    }



    /**
     * 消息摘要MD算法
     *
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static void demoMD() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String content = "test data";
        String content2 = "test data2";
        String content3 = "test data 123456789 abcdefghijklmn 123456789 abcdefghijklmn 123456789 abcdefghijklmn";

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] mdBytes = md.digest(content.getBytes());
        byte[] mdBytes2 = md.digest(content2.getBytes());
        byte[] mdBytes3 = md.digest(content3.getBytes());

        System.out.println("摘要1:" + bytes2HexString(mdBytes));
        System.out.println("摘要2:" + bytes2HexString(mdBytes2));
        System.out.println("摘要3:" + bytes2HexString(mdBytes3));

    }

    /**
     * MAC演示Demo
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static void demoMAC() throws NoSuchAlgorithmException, InvalidKeyException {

        String content = "test data";

        //生成密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        SecretKey secretKey = keyGenerator.generateKey();
        //该密钥字节数据共享给接受者
        byte[] key = secretKey.getEncoded();

        //发送者对消息进行认证
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(secretKey);
        byte[] hmacMD5Bytes = mac.doFinal(content.getBytes());

        //接受者使用相同的密钥对消息进行认证
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacMD5");
        Mac macRecive = Mac.getInstance("HmacMD5");
        macRecive.init(secretKeySpec);
        byte[] hmacMD5BytesRecive = mac.doFinal(content.getBytes());

        boolean same = bytes2HexString(hmacMD5BytesRecive).equals(bytes2HexString(hmacMD5Bytes));


        System.out.println("密钥:\t" + bytes2HexString(key));
        System.out.println("发送者生成的认证消息:\t" + bytes2HexString(hmacMD5Bytes));
        System.out.println("接收者生成的认证消息:\t" + bytes2HexString(hmacMD5Bytes));
        System.out.println("接收者与发送者认证消息结果一致:\t" + same);
    }

    /**
     * RSA演示Demo
     */
    public static void demoRSA() throws Exception {

        String content = "test data";

        //生成公钥和私钥/密钥对
        KeyPairGenerator keyPairGeneratyor = KeyPairGenerator.getInstance("RSA");
        keyPairGeneratyor.initialize(512);
        KeyPair keyPair = keyPairGeneratyor.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        //私钥加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        byte[] encrypt1 = cipher.doFinal(content.getBytes());

        //公钥解密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        byte[] descrypt1 = cipher.doFinal(encrypt1);

        //公钥加密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encrypt2 = cipher.doFinal(content.getBytes());

        //私钥解密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] descrypt2 = cipher.doFinal(encrypt2);

        boolean same1 = content.equals(new String(descrypt1));
        boolean same2 = content.equals(new String(descrypt2));

        System.out.println("公钥: " + bytes2HexString(rsaPublicKey.getEncoded()));
        System.out.println("私钥: " + bytes2HexString(rsaPrivateKey.getEncoded()));

        System.out.println("明文：" + content);
        System.out.println("私钥加密:" + bytes2HexString(encrypt1));
        System.out.println("公钥解密:" + new String(descrypt1));
        System.out.println("公钥加密：" + bytes2HexString(encrypt2));
        System.out.println("私钥解密：" + new String(descrypt2));

        System.out.println("公钥解密结果是否一致：" + same1);
        System.out.println("私钥解密结果是否一致：" + same2);
    }

    /**
     * RSA演示Demo
     */
    public static void demoRSA2() throws Exception {

        String content = "test data";

        //1.生成公钥和私钥/密钥对
        byte[] publicKeyBytes = hex2byte("305C300D06092A864886F70D0101010500034B003048024100A5AA8DDB9CCF90B4D97CC075F4A05C354F281DB591C323DFB4820144B24A4306C33F1205B290C4A688C0C744B4ABB1696D8B973BFA74DBF59339DF1E085847870203010001");
        byte[] privateKeyBytes = hex2byte("30820154020100300D06092A864886F70D01010105000482013E3082013A020100024100A5AA8DDB9CCF90B4D97CC075F4A05C354F281DB591C323DFB4820144B24A4306C33F1205B290C4A688C0C744B4ABB1696D8B973BFA74DBF59339DF1E08584787020301000102403C14D6CDC6D92049F6765FF66779A0F75475E0107184AC05FD99088CB97C65420105994B7A9E88E91D4F6755EF388C95B9C80C75F512F86881F89923F34B3DC1022100D95A1A3FB95D1DD6CC3289EC7B2D90C98EBC9167997A7DCD61F14AD9F27A81FB022100C31FB553D8DE22FB18EB98052D03D1068310C95CC374C0434F2BDA2F196866E502206E7C30CFA7C83FBCCA7BFE4469B115E27F5E3783B42EE1F81F0B6B033311373502205A028E8B1747A1AB635B8ACD186EE245B6C04FA35326D06A3C63664AC3D5BD61022100D922FCF9F17A1C8C37DCDFE1E67DD33C3501B7852469DCBEE6D1AAA43505F19A");
        //私钥对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey rsaPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey rsaPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //私钥加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        byte[] encrypt1 = cipher.doFinal(content.getBytes());

        //公钥解密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        byte[] descrypt1 = cipher.doFinal(encrypt1);

        //公钥加密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encrypt2 = cipher.doFinal(content.getBytes());

        //私钥解密
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] descrypt2 = cipher.doFinal(encrypt2);

        boolean same1 = content.equals(new String(descrypt1));
        boolean same2 = content.equals(new String(descrypt2));

        System.out.println("公钥: " + bytes2HexString(rsaPublicKey.getEncoded()));
        System.out.println("私钥: " + bytes2HexString(rsaPrivateKey.getEncoded()));

        System.out.println("明文：" + content);
        System.out.println("私钥加密:" + bytes2HexString(encrypt1));
        System.out.println("公钥解密:" + new String(descrypt1));
        System.out.println("公钥加密：" + bytes2HexString(encrypt2));
        System.out.println("私钥解密：" + new String(descrypt2));

        System.out.println("公钥解密结果是否一致：" + same1);
        System.out.println("私钥解密结果是否一致：" + same2);
    }

    /**
     * 数字签名RSA演示demo
     */
    public static void demoSignRSA() throws Exception {

        String content = "test data";

        //准备密钥（公钥私钥）
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        //私钥（特别当私钥为原始字节数组时必须用到）
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //公钥（特别当公钥为原始字节数组时必须用到）
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //执行签名
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        byte[] result = signature.sign();

        //验证签名
        signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes());
        boolean bool = signature.verify(result);

        System.out.println("公钥: " + bytes2HexString(rsaPublicKey.getEncoded()));
        System.out.println("私钥: " + bytes2HexString(rsaPrivateKey.getEncoded()));
        System.out.println("明文：" + content);
        System.out.println("RSA数字签名 : " + bytes2HexString(result));
        System.out.println("RSA签名验证结果: " + bool);
    }

    /**
     * 数字签名DSA演示demo
     */
    public static void demoSignDSA() throws Exception {

        String content = "test data";

        //准备密钥（公钥私钥）
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
        ////私钥（特别当私钥为原始字节数组时必须用到）
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        ////公钥（特别当公钥为原始字节数组时必须用到）
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //执行签名
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        byte[] result = signature.sign();

        //验证签名
        signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes());
        boolean bool = signature.verify(result);

        System.out.println("公钥: " + bytes2HexString(dsaPublicKey.getEncoded()));
        System.out.println("私钥: " + bytes2HexString(dsaPrivateKey.getEncoded()));
        System.out.println("明文：" + content);
        System.out.println("DSA数字签名 : " + bytes2HexString(result));
        System.out.println("DSA签名验证结果: " + bool);
    }

    /**
     * 数字签名ECDSA演示demo
     */
    public static void demoSignECDSA() throws Exception {

        String content = "test data";

        //准备密钥（公钥私钥）
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
        ////私钥（特别当私钥为原始字节数组时必须用到）
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        ////公钥（特别当公钥为原始字节数组时必须用到）
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //执行签名
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        byte[] result = signature.sign();

        //验证签名
        signature = Signature.getInstance("SHA1withECDSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes());
        boolean bool = signature.verify(result);

        System.out.println("公钥: " + bytes2HexString(ecPublicKey.getEncoded()));
        System.out.println("私钥: " + bytes2HexString(ecPrivateKey.getEncoded()));
        System.out.println("明文：" + content);
        System.out.println("ECDSA数字签名 : " + bytes2HexString(result));
        System.out.println("ECDSA签名验证结果: " + bool);
    }

    /**
     * PEB演示demo 密钥生成
     *
     * @throws Exception
     */
    public static void demoPBE() throws Exception {

        //需要加密的私钥
        byte[] privateKeybytes = hex2byte("06416FD848FEF70BE45CED6E52385A64");

        //用户输入的密码字符串
        String password = "userPassword";
        // 密钥的比特位数AES支持128、192和256比特长度的密钥
        int dklen = 256;
        //迭代次数，geth的默认值为262144
        int iterationCount = 26214;
        // 盐值的字节数组长度，其长度值需要和最终输出的密钥字节数组长度一致
        byte[] salt = new byte[32];
        new SecureRandom().nextBytes(salt);
        //生成密钥
        long startTimestamp = System.currentTimeMillis();
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, dklen);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        long endTimestamp = System.currentTimeMillis();

        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        //加密私钥
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] encrypt = cipher.doFinal(privateKeybytes);

        //解密私钥
        Cipher cipherDecrypt = Cipher.getInstance("AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        byte[] decrypt = cipherDecrypt.doFinal(encrypt);

        boolean bool = bytes2HexString(privateKeybytes).equals(bytes2HexString(decrypt));

        System.out.println("私钥：" + bytes2HexString(privateKeybytes));
        System.out.println("password：" + password);
        System.out.println("dklen：" + dklen);
        System.out.println("iterationCount：" + iterationCount);
        System.out.println("salt：" + bytes2HexString(salt));
        System.out.println("生成密钥：" + bytes2HexString(secretKey.getEncoded()));
        System.out.println("耗时（毫秒）："+(endTimestamp-startTimestamp));
        System.out.println("加密私钥：" + bytes2HexString(encrypt));
        System.out.println("解密私钥：" + bytes2HexString(decrypt));
        System.out.println("比对结果：" + bool);
    }


    /**
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] array) {
        StringBuilder builder = new StringBuilder();

        for (byte b : array) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            builder.append(hex);
        }

        return builder.toString().toUpperCase();
    }

    /**
     * 将十六进制串转化为byte数组
     */
    public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }


}
