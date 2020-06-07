package com.sevenpounds.shower.user;

import com.sevenpounds.shower.util.HttpClientUtil;
import com.sevenpounds.shower.util.HttpContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.HttpClient;

public class Login {
    public Login() {
    }

    public static String step1() throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("Host", "i.jiangnan.edu.cn");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Connection", "Keep-Alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Sec-Fetch-Site", "cross-site");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        Map<String, Object> postData = new LinkedHashMap();
        postData.put("action", "login");
        postData.put("oginmode", "web");
        postData.put("username", "12341234");
        postData.put("password", "234234");
        postData.put("auth", "9%3B0%3B3%3B10");
        postData.put("sign_value", "");
        postData.put("signfield", "cn");
        postData.put("personfield", "uid");
        postData.put("randomNumber", "374029");
        postData.put("logintype", "0");
        HttpClient httpClient = HttpClientUtil.getDefaultHttpClient();
        HttpContext response = HttpClientUtil.doGet(httpClient, "https://i.jiangnan.edu.cn/ssoserver/login", postData, headers);
        Header[] headers1 = response.getResponse().getAllHeaders();
        HeaderElement[] elements = headers1[9].getElements();
        String value = elements[0].getValue();
        return value;
    }

    public static void step2() throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("Host", "i.jiangnan.edu.cn");
        headers.put("Connection", "Keep-Alive");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Origin", "https://i.jiangnan.edu.cn");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-User", "?1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        String cookie = step1();
        System.out.println("----------------------------------------step 1 cookie :" + cookie);
        headers.put("Cookie", cookie);
        Map<String, Object> postData = new LinkedHashMap();
        postData.put("action", "login");
        postData.put("oginmode", "web");
        postData.put("username", "12341234");
        postData.put("password", "234234");
        postData.put("auth", "9%3B0%3B3%3B10");
        postData.put("sign_value", "");
        postData.put("signfield", "cn");
        postData.put("personfield", "uid");
        postData.put("randomNumber", "374029");
        postData.put("logintype", "0");
        HttpClient httpClient = HttpClientUtil.getDefaultHttpClient();
        HttpContext response = HttpClientUtil.doPost(httpClient, "https://i.jiangnan.edu.cn/ssoserver/login", postData, headers);
        int httpStatus = HttpClientUtil.getResponseCode(response.getResponse());
    }

    public static void main(String[] args) throws IOException {
        step1();
    }
}
