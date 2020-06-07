package com.sevenpounds.shower.job;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sevenpounds.shower.CAPTCHA.JianJiaoDaMaClient;
import com.sevenpounds.shower.CAPTCHA.Verify;
import com.sevenpounds.shower.user.Login;
import com.sevenpounds.shower.util.HttpClientUtil;
import com.sevenpounds.shower.util.HttpContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
@Slf4j
public class Operation {
    public Operation() {
    }

    public static void cancel(int mid) throws IOException {
        Login.step2();
        LinkedHashMap<String, String> headers = new LinkedHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("Host", "shower.jiangnan.edu.cn");
        headers.put("Connection", "Keep-Alive");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Origin", "https://shower.jiangnan.edu.cn");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Cookie", "jycms_username=%E8%96%9B%E4%B8%BD%E5%AA%9B; jycms_userid=86968; jycms_sig=e11593be5307b0b3c8e7d1ebf6c92b1cba8e667a");
        Map<String, Object> postData = new LinkedHashMap();
        postData.put("mid", mid);
        HttpClient httpClient = HttpClientUtil.getDefaultHttpClient();
        HttpClientUtil.doPost(httpClient, "https://shower.jiangnan.edu.cn/h5/action/ajax_signup_join_cancel.php", postData, headers);
    }

    public static String order1(int mid) throws Exception {
        byte[] image = Verify.getVerifyCode();
        String verifyCode = JianJiaoDaMaClient.imgCaptcha(image);
        System.out.println("--------------------------识别的验证码是：" + verifyCode);
        LinkedHashMap<String, String> headers = new LinkedHashMap();
        String ss = Verify.PHPSESSID;
        headers.put("Host", "shower.jiangnan.edu.cn");
        headers.put("Connection", "Keep-Alive");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Origin", "https://shower.jiangnan.edu.cn");
        headers.put("Referer", "https://shower.jiangnan.edu.cn/h5/meeting_signup_list.php");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Cookie", "jycms_username=%E8%96%9B%E4%B8%BD%E5%AA%9B; jycms_userid=86968; jycms_sig=e11593be5307b0b3c8e7d1ebf6c92b1cba8e667a; PHPSESSID=" + Verify.PHPSESSID);
        Map<String, Object> postData = new LinkedHashMap();
        postData.put("mid", mid);
        postData.put("yzm", verifyCode);
        HttpClient httpClient = HttpClientUtil.getDefaultHttpClient();
        HttpContext response = HttpClientUtil.doPost(httpClient, "https://shower.jiangnan.edu.cn/h5/action/ajax_signup_join.php", postData, headers);
        String responseData = HttpClientUtil.getResponseContent(response);
        JSONObject resultJson = JSON.parseObject(responseData);
        String mess = resultJson.getString("msg");
        log.debug("消息详情 "+resultJson.getString("detail"));
        if(HttpClientUtil.getResponseCode(response.getResponse())==200){
            return mess;
        }
        return "err";
    }

    public static void main(String[] args) throws Exception {
        order1(1120);
    }
}

