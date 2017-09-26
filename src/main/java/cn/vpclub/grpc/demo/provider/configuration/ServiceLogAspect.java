package cn.vpclub.grpc.demo.provider.configuration;

import cn.vpclub.moses.utils.common.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Service层日志切面
 * <p>
 * Created by WangRui on 2017/9/21.
 */
@Aspect
@Component("grpc-demo-provider-serviceLogAspect")
@Slf4j
public class ServiceLogAspect {

    protected ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    /**
     * 定义一个切入点
     */
    @Pointcut("execution(public * cn.vpclub.grpc.demo.provider.service..*.*(..))")
    public void pointCutMethod() {
        return;
    }

    /**
     * 声明前置通知
     */
    @Before("pointCutMethod()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            startTime.set(System.currentTimeMillis());

            // 记录下请求参数内容
            log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
                    + "." + joinPoint.getSignature().getName() + "()\n"
                    + "PARAMS : " + JsonUtil.objectToJson(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("前置通知处理异常: " + e.getMessage(), e);
        }
    }

    /**
     * 声明后置通知
     *
     * @param result
     */
    @AfterReturning(pointcut = "pointCutMethod()", returning = "result")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，返回内容
            if (null != result) {
                if (result instanceof String) {
                    log.info("RESULT : " + result);
                } else {
                    log.info("RESULT : " + JsonUtil.objectToJson(result));
                }
            }
            log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        } catch (Exception e) {
            log.error("后置通知处理异常: " + e.getMessage(), e);
        }
    }

}
