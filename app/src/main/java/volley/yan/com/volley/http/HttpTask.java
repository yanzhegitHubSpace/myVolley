package volley.yan.com.volley.http;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import volley.yan.com.volley.http.interfaces.IHttpListener;
import volley.yan.com.volley.http.interfaces.IHttpService;

public class HttpTask<T> implements Runnable {


    private IHttpService httpService;

    public HttpTask(RequestHolder<T> requestHolder) {
        httpService = requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());
        T request = requestHolder.getRequestInfo();
        String requestInfo = JSON.toJSONString(request);
        httpService.setRequestData(requestInfo.getBytes());
    }

    @Override
    public void run() {
        httpService.excute();
    }
}
