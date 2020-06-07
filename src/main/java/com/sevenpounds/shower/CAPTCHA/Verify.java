package com.sevenpounds.shower.CAPTCHA;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;

import com.sevenpounds.shower.user.Login;
import com.sevenpounds.shower.util.HttpClientUtil;
import com.sevenpounds.shower.util.HttpContext;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.HttpClient;

public class Verify {
    public static String PHPSESSID;

    public Verify() {
    }

    public static byte[] getVerifyCode() throws Exception {
        Login.step2();
        LinkedHashMap<String, String> headers = new LinkedHashMap();
        headers.put("Host", "shower.jiangnan.edu.cn");
        headers.put("Origin", "https://shower.jiangnan.edu.cn");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Cookie", "jycms_username=%E8%96%9B%E4%B8%BD%E5%AA%9B; jycms_userid=86968; jycms_sig=e11593be5307b0b3c8e7d1ebf6c92b1cba8e667a");
        HttpClient httpClient = HttpClientUtil.getDefaultHttpClient();
        HttpContext response = HttpClientUtil.doGet(httpClient, "https://shower.jiangnan.edu.cn/h5/verify.php", new HashMap(), headers);
        Header[] headers1 = response.getResponse().getAllHeaders();
        HeaderElement[] elements = headers1[6].getElements();
        PHPSESSID = elements[0].getValue();
        System.out.println("--------------------------getVerifyCode()  PHPSESSID" + PHPSESSID);
        byte[] responseData = HttpClientUtil.getResponseContentBytes(response);
        ByteArrayInputStream in = new ByteArrayInputStream(responseData);
        BufferedImage image = ImageIO.read(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", out);
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        byte[] image = getVerifyCode();
        String code = JianJiaoDaMaClient.imgCaptcha(image);
        System.out.println("--------------------- 识别的验证码是：" + code);
    }

    public static void saveImage() throws IOException {
        File in = new File("/Users/cristovan/Documents/showertickect/src/main/resources/image.png");
        BufferedImage originalImage = ImageIO.read(in);
        File outputfile = new File("/Users/cristovan/Documents/showertickect/src/main/resources/test.png");
        ImageIO.write(originalImage, "png", outputfile);
    }
}

