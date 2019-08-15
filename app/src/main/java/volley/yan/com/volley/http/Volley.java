package volley.yan.com.volley.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.FutureTask;

import volley.yan.com.volley.LoginReponse;
import volley.yan.com.volley.User;
import volley.yan.com.volley.http.interfaces.IDataListener;
import volley.yan.com.volley.http.interfaces.IHttpListener;
import volley.yan.com.volley.http.interfaces.IHttpService;

public class Volley {

    /**
     * 暴露给调用层
     *
     * @param <T> 请求参数类型
     * @param <M> 响应参数类型
     */
    public static <T, M> void sendRequest(T requestInfo,
                                          String url,
                                          Class<M> response,
                                          IDataListener dataListener) {


        RequestHolder<T> requestHolder = new RequestHolder<>();
        requestHolder.setUrl(url);
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonDealListener<>(response, dataListener);
        requestHolder.setHttpService(httpService);
        requestHolder.setHttpListener(httpListener);
        requestHolder.setRequestInfo(requestInfo);
        HttpTask httpTask = new HttpTask<>(requestHolder);
        try {
            ThreadPoolManager.getInstance().exute(new FutureTask<Object> (httpTask,null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void login() {
        Log.d("yanzhe", "invoke login interface");
        String url = "http://192.168.198.142:9090/api//test/login";
        User user = new User();
        user.setUserName("18792403286");
        user.setPassword("123456");
//        for (int i = 0; i < 50; i++) {
            Volley.sendRequest(user, url, LoginReponse.class, new IDataListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("yanzhe", "请求成功");
                    String s = JSON.toJSONString(o);
                    Log.d("yanzhe", s);
                }

                @Override
                public void onFailure() {
                    Log.d("yanzhe", "请求失败");
                }
            });
//        }

    }


}
