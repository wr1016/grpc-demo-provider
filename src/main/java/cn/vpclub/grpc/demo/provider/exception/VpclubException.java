package cn.vpclub.grpc.demo.provider.exception;

import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;

/**
 * 异常类
 * Created by WangRui on 2017/9/21.
 */
public class VpclubException extends RuntimeException {

    private static final long serialVersionUID = -3953266288895754521L;
    private BaseResponse error;
    private long param = 0L;
    private Object paramObj = null;

    public VpclubException(BaseResponse dataNull) {
        super(dataNull == null ? "" : dataNull.getRetMsg());
        this.error = dataNull;
    }

    public VpclubException(BaseResponse error, long param) {
        super(error == null ? "" : error.getRetMsg());
        this.error = error;
        this.param = param;
    }

    public VpclubException(BaseResponse error, Object paramObj) {
        super(error == null ? "" : error.getRetMsg());
        this.error = error;
        this.paramObj = paramObj;
    }

    public String getRespCode() {
        return this.error == null ? BaseResponse.SYSTEM_ERROR.getRetCode() : this.error.getRetCode();
    }

    public String getRestDesc() {
        return this.error == null ? BaseResponse.SYSTEM_ERROR.getRetMsg() : this.error.getRetMsg();
    }

    public long getParam() {
        return this.param;
    }

    public Object getParamObj() {
        return this.paramObj;
    }
}
