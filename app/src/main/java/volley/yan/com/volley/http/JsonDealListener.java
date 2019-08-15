package volley.yan.com.volley.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import volley.yan.com.volley.http.interfaces.IDataListener;
import volley.yan.com.volley.http.interfaces.IHttpListener;

public class JsonDealListener<M> implements IHttpListener {

    // 需要两个成员变量
    // 1、回调给调用者的接口
    // 2、JSON转实体类的类型

    private Class<M> response;

    private IDataListener dataLintener;

    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 两个成员变量是通过构造函数传进来的
     *
     * @param response
     * @param dataLintener
     */
    public JsonDealListener(Class<M> response, IDataListener dataLintener) {
        this.response = response;
        this.dataLintener = dataLintener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream is = null;
        try {
            is = httpEntity.getContent();
            // 得到网络返回的数据
            String content = getContent(is);
            // 将string转化成对象类型
            final M m = JSON.parseObject(content, response);
            // 线程切换
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataLintener.onSuccess(m);
                }
            });
        } catch (IOException e) {
            dataLintener.onFailure();
        }
    }


    @Override
    public void onFailure() {
        dataLintener.onFailure();
    }

    /**
     * 通过流转字符串
     *
     * @param is
     * @return
     */
    private String getContent(InputStream is) throws IOException {


        StringBuilder str = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;

        while ((line = reader.readLine()) != null) {

            str.append(line);

            System.out.println(line);

            System.out.println("**********************************************");

        }
        return str.toString();
    }

}


