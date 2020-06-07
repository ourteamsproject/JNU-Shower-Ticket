package com.sevenpounds.shower.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpContext {
    private HttpResponse response;
    private HttpRequestBase request;

    public HttpContext(HttpResponse response, HttpRequestBase request) {
        this.response = response;
        this.request = request;
    }

    public HttpResponse getResponse() {
        return this.response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public HttpRequestBase getRequest() {
        return this.request;
    }

    public void setRequest(HttpRequestBase request) {
        this.request = request;
    }
}