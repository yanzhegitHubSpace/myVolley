package volley.yan.com.volley.http.interfaces;

/**
 * 跟调用层打交道
 */
public interface IDataListener<M> {

    /**
     * 回到结果给调用层
     *
     * @param m
     */
    void onSuccess(M m);

    void onFailure();

}
