package volley.yan.com.volley.http.interfaces;

import org.apache.http.HttpEntity;

/**
 * 请求结果回调
 */
public interface IHttpListener {

    /**
     * 网络访问框架层
     * @param httpEntity
     */
    void onSuccess(HttpEntity httpEntity);


    void onFailure();


}
