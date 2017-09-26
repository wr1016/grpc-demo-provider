package cn.vpclub.grpc.demo.provider.enums;

import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;

import java.text.MessageFormat;

/**
 * 接口返回值枚举
 * Created by WangRui on 2017/9/21.
 */
public enum ReturnCodeEnum {

    CODE_1000("1000", "成功"),
    CODE_1001("1001", "无权限访问"),
    CODE_1002("1002", "{0}信息不存在"),
    CODE_1003("1003", "支付方式错误"),
    CODE_1004("1004", "服务器处理异常"),
    CODE_1005("1005", "失败"),
    CODE_1006("1006", "参数不全"),
    CODE_1007("1007", "请求超时"),
    CODE_1008("1008", "信息已存在"),
    CODE_1009("1009", "登录状态已失效, 请重新登录"),

    CODE_1010("1010", "{0}"),
    CODE_1011("1011", "{0}不能为空");

    /**
     * 业务编号
     */
    private String code;

    /**
     * 业务值
     */
    private String value;

    private ReturnCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public final String getCode() {
        return code;
    }

    public final String getValue() {
        return value;
    }

    /**
     * 将枚举值参数实例化到返回结果 带参型
     */
    public BaseResponse getBaseResponse(Object... params) {
        return new BaseResponse(code, MessageFormat.format(value, params));
    }

    /**
     * 将枚举值参数实例化到返回结果 无参型
     */
    public BaseResponse getBaseResponse() {
        return new BaseResponse(code, value);
    }

    @Override
    public String toString() {
        return this.code + "=" + this.value;
    }
}
