package com.dove.authorization;

import com.dove.authorization.utils.Base64Utils;
import com.dove.authorization.utils.FileUtil;
import com.dove.authorization.utils.RSAUtils;

import java.io.File;
import java.util.Scanner;

/** 
 * 生成license 
 * @author happyqing https://www.iteye.com/blog/happyqing-2083360
 *
 */  
public class LicenseGenerator {  

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRh9gZRdqDdTVB4i26hJLq74Rz5RehP3jIsj9g\n" +
            "IlxLM5fC7zGUpi9exir1CF+XXYUE3n6dHvv/Ctz32LK/C0lN3InQ/6lQaxXKH8eNrlq4edPbeNzo\n" +
            "YMfOIGYKshchdnFRvh7Wfpc4nYeR1pwnHbWU6rW7+Oeo7AZj7aY/R+2DBQIDAQAB";
      
    /** 
     * RSA算法 
     * 公钥和私钥是一对，此处只用私钥加密 
     */  
    public static final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANGH2BlF2oN1NUHiLbqEkurvhHPl\n" +
            "F6E/eMiyP2AiXEszl8LvMZSmL17GKvUIX5ddhQTefp0e+/8K3PfYsr8LSU3cidD/qVBrFcofx42u\n" +
            "Wrh509t43Ohgx84gZgqyFyF2cVG+HtZ+lzidh5HWnCcdtZTqtbv456jsBmPtpj9H7YMFAgMBAAEC\n" +
            "gYEAxgcAWuplGccFnY3ZMCWqAPZ8FViYW6J0UiH8uavf1IcfUHHmRxpFPOpHoSvXxGPzmuV5fp/R\n" +
            "BHtM2cVpMhvFm2RU3f8D9/DUYSJEC46q9LAvTr4TlhcSoYk5McpzOyGRlsVigdSGERnJ6UkaMiLj\n" +
            "J1q0Ttfpt8zUOIfDlFZ62WUCQQD9mU4NXT6r9XY/KWgyDhZMlLTFdqU9tgRJZqRKp6oAcSxVBTVm\n" +
            "YcMeI+w3IT0KxdCv32Vm3HzsCuGxq5YoLjv/AkEA04O5DaiNPYLPr5Pq8P7KDFSo/bHW427t7042\n" +
            "yb/VFKFLU+RcazsV7sWmk+Fzj6fg5+ZW2cJD5CASoNfDtCdQ+wJBAL4AtJgSurf/yr57+ZM3NsHd\n" +
            "0Kr5v8hCrWeJPaKpiBeYs4xnwKCasqPMaljL0H5Xw4lhqQmuPPJlHMAPPQuRyGUCQAcQlkTDhXwx\n" +
            "e1hk+2rzR7JjvYVDxGayVNYyaZgRhT98J7T0orWeMpxYE7lKxX5d4CA+zFXXrEbZX2Xp7wp8abMC\n" +
            "QQCu55QJDSpo1AsDs0mzNJ1qSEZAoevHxnmRelnDXJmGZtwb7wi7Te7QzsWwRLKzrAbzlyRmoLP5\n" +
            "gc4c3ne5EDZj";
      
    public static void generator(String serial, String filePath) {
        try {
            System.err.println("私钥加密——公钥解密");
            System.out.println("原文字：\r\n" + serial);
            byte[] data = serial.getBytes();
            byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
            System.out.println("加密后：\r\n" + new String(encodedData)); //加密后乱码是正常的

            Base64Utils.byteArrayToFile(encodedData, filePath+File.separator+"license.lic");
            System.out.println("license.lic：\r\n" + filePath+File.separator+"license.lic");
            //解密
            byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
            String target = new String(decodedData);
            System.out.println("解密后: \r\n" + target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
      
    public static void main(String[] args) throws Exception {
        System.out.println("请输入数字：1.自动；2.手动");
        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();
        String filePath = FileUtil.getBasePath();
        switch (choose) {
            case 1:
                String serial = getSerial();
                generator(serial, filePath);
                break;
            case 2:
                System.out.println("请输入机器码");
                String mechineCode = sc.next();
                generator(mechineCode, filePath);
                break;
            default:
                break;
        }
    }

    public static String getSerial() {
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[] { "wmic", "cpu", "get", "ProcessorId" });
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            sc.next();
            String mechineCode = sc.next();
            System.out.println("机器码:" + mechineCode);
            return mechineCode;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}  