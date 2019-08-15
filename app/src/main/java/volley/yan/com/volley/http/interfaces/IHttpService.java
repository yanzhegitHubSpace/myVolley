package volley.yan.com.volley.http.interfaces;

public interface IHttpService {

    /**
     * 设置url
     */
    void setUrl(String url);

    /**
     * 执行获取网络
     */
    void excute();

    /**
     * 设置Http回调接口
     */
    void setHttpListener(IHttpListener httpListener);

    /**
     * 设置请求参数
     */
    void setRequestData(byte [] requestData);



}
