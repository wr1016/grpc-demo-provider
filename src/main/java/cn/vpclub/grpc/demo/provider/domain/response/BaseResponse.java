package cn.vpclub.grpc.demo.provider.domain.response;

import cn.vpclub.grpc.demo.provider.enums.ReturnCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * 返回值
 *
 * @author : WangRui
 * @Date : 2017/9/21
 */
@Setter
@Getter
public class BaseResponse<T> implements Serializable {
    /**
     * 返回代码，1000表示成功，其他请查看错误描述
     */
    protected String retCode = "1000";

    /**
     * 返回描述
     */
    protected String retMsg = "成功";

    /**
     * UUID
     */
    private String tid = UUID.randomUUID().toString();

    /**
     * 返回结果
     */
    protected T result;

    /**
     * 系统异常
     */
    public static BaseResponse SYSTEM_ERROR = new BaseResponse("9999", "系统异常");

    /**
     * 构造函数
     *
     * @param result
     */
    public BaseResponse(T result) {
        this.result = result;
    }

    /**
     * 枚举
     *
     * @param returnCodeEnum
     */
    public BaseResponse(ReturnCodeEnum returnCodeEnum) {
        this.retCode = returnCodeEnum.getCode();
        this.retMsg = returnCodeEnum.getValue();
    }

    /**
     * 构造函数
     */
    public BaseResponse() {
    }

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg  描述
     */
    public BaseResponse(String code, String msg) {
        this.retCode = code;
        this.retMsg = msg;
    }

}
