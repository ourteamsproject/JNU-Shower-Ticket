package com.sevenpounds.shower.CAPTCHA;


import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sevenpounds.shower.util.HttpClientUtil;
import com.sevenpounds.shower.util.HttpContext;
import com.sevenpounds.shower.util.StringUtils;
import org.apache.commons.codec.binary.Base64;

public class JianJiaoDaMaClient {
    private static final String host = "http://apigateway.jianjiaoshuju.com";
    private static final String imageBase64Header = "data:image/jpeg;base64,";
    private static final Map<String, String> headers = new HashMap<String, String>() {
        {
            this.put("appcode", "8BF8597686438D9E471A78D1A591BC58");
            this.put("appKey", "AKID2ec1eaea54bfc3fc9dd3a9937f9f616b");
            this.put("appSecret", "db5e8b071eb72f849e0534b289d99be0");
            this.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        }
    };

    public JianJiaoDaMaClient() {
    }

    public static String imgCaptcha(byte[] bytes) {
        return imgCaptcha(bytes, "ne4");
    }

    public static String imgCaptcha(byte[] bytes, String type) {
        String path = null;
        path = "/api/v_1/yzmCustomized.html";

        try {
            Map<String, Object> bodys = new HashMap();
            bodys.put("v_pic", "data:image/jpeg;base64," + Base64.encodeBase64String(bytes));
            bodys.put("v_type", type);
            HttpContext response = HttpClientUtil.doPost(HttpClientUtil.getDefaultHttpClient(), "http://apigateway.jianjiaoshuju.com" + path, bodys, headers);
            int httpStatus = HttpClientUtil.getResponseCode(response.getResponse());
            String responseData = null;
            if (httpStatus == 200) {
                responseData = HttpClientUtil.getResponseContent(response);
                if (!StringUtils.isEmpty(responseData)) {
                    JSONObject resultJson = JSON.parseObject(responseData);
                    Integer errCode = resultJson.getInteger("errCode");
                    if (errCode != null && errCode == 0) {
                        return resultJson.getString("v_code");
                    }
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src/main/resources/image.png");
        byte[] fileByte = Files.readAllBytes(file.toPath());
        System.out.println(imgCaptcha(fileByte, "ne4"));
    }
}

