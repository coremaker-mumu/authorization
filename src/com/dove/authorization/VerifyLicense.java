package com.dove.authorization;

import com.dove.authorization.utils.FileUtil;
import com.dove.authorization.utils.RSAUtils;

import java.io.File;

/**
 * 解析授权文件
 * @author dove
 * @date 2020.09.02
 */
public class VerifyLicense {

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRh9gZRdqDdTVB4i26hJLq74Rz5RehP3jIsj9g\n" +
            "IlxLM5fC7zGUpi9exir1CF+XXYUE3n6dHvv/Ctz32LK/C0lN3InQ/6lQaxXKH8eNrlq4edPbeNzo\n" +
            "YMfOIGYKshchdnFRvh7Wfpc4nYeR1pwnHbWU6rW7+Oeo7AZj7aY/R+2DBQIDAQAB";

    public static void main(String[] args) throws Exception {
        //读取授权文件
        File file = new File(FileUtil.getBasePath()+ File.separator+"license.lic");
        byte[] licBytes = FileUtil.readBytes(file);
        byte[] result = RSAUtils.decryptByPublicKey(licBytes, publicKey);
        //解析出来的机器码如果和本机的机器码一致则为通过
        System.out.println(new String(result));
    }
}
