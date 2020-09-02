package com.dove.authorization.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 生成私钥和公钥
 *
 * @author dove
 * @date 2020.09.02
 */
public class GeneratorKey {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSAUtils.KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String privateKeyCode = Base64Utils.encode(privateKey.getEncoded());
        String publicKeyCode = Base64Utils.encode(publicKey.getEncoded());
        System.out.println("私钥：" + privateKeyCode);
        System.out.println("公钥：" + publicKeyCode);
    }
}
