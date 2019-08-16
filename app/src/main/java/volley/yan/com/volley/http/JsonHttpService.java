package volley.yan.com.volley.http;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import volley.yan.com.volley.http.interfaces.IHttpListener;
import volley.yan.com.volley.http.interfaces.IHttpService;

public class JsonHttpService implements IHttpService {


    private IHttpListener httpListener;

    private HttpClient httpClient = new DefaultHttpClient();

    private HttpPost httpPost = null;

    private String url;

    private byte[] requestData;

    /**
     * httpClient 获取网络的回调
     */
    private HttpResponseHandler httpResponseHandler = new HttpResponseHandler();

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {

        try {
            httpPost = new HttpPost(url);
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
            httpPost.setEntity(byteArrayEntity);
            httpClient.execute(httpPost, httpResponseHandler);
        } catch (IOException e) {
            httpListener.onFailure();
        }
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }


    private class HttpResponseHandler extends BasicResponseHandler {

        @Override
        public String handleResponse(HttpResponse response) throws IOException {
            // 处理数据
            // 响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                httpListener.onSuccess(response.getEntity());
            }
            return null;
        }
    }


    public String getStrFromInputSteam(InputStream in){
        BufferedReader bf= null;
        try {
            bf = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            //最好在将字节流转换为字符流的时候 进行转码
            StringBuffer buffer=new StringBuffer();
            String line="";
            while((line=bf.readLine())!=null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            httpListener.onFailure();
        }
        return "";
    }

}
