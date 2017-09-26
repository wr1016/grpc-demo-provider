package cn.vpclub.grpc.demo.provider.configuration;

import cn.vpclub.grpc.demo.provider.enums.ReturnCodeEnum;
import cn.vpclub.grpc.demo.provider.exception.VpclubException;
import cn.vpclub.grpc.demo.provider.domain.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.web.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 * 服务基础服务Controller抽象类
 * <p>
 * Created by WangRui on 2017/9/21.
 */
@ControllerAdvice
@Slf4j
public abstract class BaseController {

    /**
     * 管理基础路径
     */
    @Value("${server.context-path}")
    protected String contextPath;

    /**
     * 异常拦截处理
     *
     * @param exp
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception exp, HttpServletResponse response) {
        BaseResponse respInfo = new BaseResponse();
        respInfo.setRetCode(ReturnCodeEnum.CODE_1004.getCode());
        respInfo.setRetMsg(ReturnCodeEnum.CODE_1004.getValue());
        log.error("服务处理异常: " + exp.getMessage(), exp);
        HttpResponseUtil.setResponseJsonBody(response, JsonUtil.objectToJson(respInfo));
    }

    /**
     * 自定义业务处理异常拦截处理
     *
     * @param exp
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(VpclubException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void CustomhandleException(VpclubException exp, HttpServletResponse response) {
        log.error("业务处理异常：code" + exp.getRespCode() + "，|| 自定义异常描述：" + exp.getRestDesc(), exp);
        String respDesc = exp.getRestDesc();
        HttpResponseUtil.setResponseJsonBody(response, JsonUtil.objectToJson(new BaseResponse(exp.getRespCode(), respDesc)));
    }

    /**
     * 判断对象中属性是否为null
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static boolean chekAttribute(Object obj) {
        boolean re = false;
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (null != f.get(obj)) {
                    re = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                log.info("TransactionRecordsController 校验实体对象反射异常:", e.getMessage(), e);
                throw new VpclubException(ReturnCodeEnum.CODE_1010.getBaseResponse("校验实体对象反射异常"));
            }
        }
        return re;
    }

    public void setResponseJsonBody(HttpServletResponse response, Object object) {
        HttpResponseUtil.setResponseJsonBody(response, JsonUtil.objectToJson(object));
    }
}
