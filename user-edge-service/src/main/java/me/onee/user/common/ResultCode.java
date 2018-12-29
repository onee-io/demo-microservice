package me.onee.user.common;


/**
 * 返回状态码
 */
public enum ResultCode {
    R200(200, "成功"),
    R400(400, "失败"),
    R401(401, "用户名和密码不一致"),
    R402(402, "手机号和邮箱必须填写一项"),
    R403(403, "验证码发送失败"),
    R404(404, "验证码错误"),
    R500(500, "服务不可用");

    private int code;
    private String info;

    ResultCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}