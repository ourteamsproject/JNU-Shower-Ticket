package com.sevenpounds.shower.util;


import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

public class InternalHttpClientUtils {
    public InternalHttpClientUtils() {
    }

    public static String getHttpProxyInfo(CloseableHttpClient httpClient) {
        StringBuilder proxy = new StringBuilder();
        String userName = "";
        String password = "";

        try {
            Field[] fields = httpClient.getClass().getDeclaredFields();
            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                field.setAccessible(true);
                if (field.getName().equals("defaultConfig")) {
                    RequestConfig config = (RequestConfig)field.get(httpClient);
                    proxy.append(config.getProxy().getHostName());
                    proxy.append(":");
                    proxy.append(config.getProxy().getPort());
                }

                if (field.getName().equals("credentialsProvider")) {
                    CredentialsProvider provider = (CredentialsProvider)field.get(httpClient);
                    Field[] providerFields = provider.getClass().getDeclaredFields();
                    providerFields[0].setAccessible(true);
                    ConcurrentHashMap<AuthScope, Credentials> map = (ConcurrentHashMap)providerFields[0].get(provider);

                    UsernamePasswordCredentials credential;
                    for(Iterator var12 = map.entrySet().iterator(); var12.hasNext(); password = credential.getPassword()) {
                        Entry<AuthScope, Credentials> entry = (Entry)var12.next();
                        credential = (UsernamePasswordCredentials)entry.getValue();
                        userName = credential.getUserName();
                    }
                }
            }

            if (!StringUtils.isBlank(userName)) {
                proxy.append(":");
                proxy.append(userName);
            }

            if (!StringUtils.isBlank(password)) {
                proxy.append(":");
                proxy.append(password);
            }
        } catch (Exception var15) {
            System.out.println(var15.getStackTrace());
        }

        return proxy.toString();
    }

    public static String getHttpProxyTimeOutInfo(HttpClient httpClient) {
        StringBuilder proxy = new StringBuilder();

        try {
            Field[] fields = httpClient.getClass().getDeclaredFields();
            Field[] var3 = fields;
            int var4 = fields.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                field.setAccessible(true);
                if (field.getName().equals("defaultConfig")) {
                    RequestConfig config = (RequestConfig)field.get(httpClient);
                    proxy.append(config.getConnectTimeout());
                    proxy.append(":");
                    proxy.append(config.getSocketTimeout());
                }
            }
        } catch (Exception var8) {
            System.out.println(var8.getStackTrace());
        }

        return proxy.toString();
    }
}
