package cn.pachira.pachiracontent.result;

/**
 * 结果类
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:48
 **/

public class Result {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 返回内容
     */
    private Object data;

    public Result() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
