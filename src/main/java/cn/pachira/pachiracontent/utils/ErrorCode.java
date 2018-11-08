package cn.pachira.pachiracontent.utils;

/**
 * 错误码的枚举
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:45
 **/

public enum ErrorCode {

    SUCCESS(0, "success"),
    INTERN_ERROR(10001, "内部错误"),
    MISSING_PARAM(10002, "缺少参数"),
    INVALID_PARAM(10003, "非法参数"),

    GET_JOKE_FAIL(20001, "获取笑话失败"),

    QUERY_PHONE_FAIL(30001, "查询归属地失败");

    /**
     * 错误码
     */
    private int errCode;
    /**
     * 错误信息
     */
    private String errMsg;

    ErrorCode(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
