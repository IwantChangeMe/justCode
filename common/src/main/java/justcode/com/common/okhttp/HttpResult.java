package justcode.com.common.okhttp;


public class HttpResult<E> {

    private E entity;
    private int resultCode;
    private String resultMessage;

    public HttpResult() {
    }

    public HttpResult(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * 系统异常
     */
    public static final HttpResult defaultErrorResult = new HttpResult(1, "服务器开小差");
    /**
     * 账号或密码为空
     */
    public static final HttpResult paramErrorResult = new HttpResult(2, "参数错误");
    /**
     * 没有连接上服务器
     */
    public static final HttpResult timeOutErrorResult = new HttpResult(-2, "没有连接上服务器");
    /**
     * JSON解析异常
     */
    public static final HttpResult paseErrorResult = new HttpResult(-3, "JSON解析异常");

    public boolean isSucc() {
        return resultCode == 0;
    }
}
