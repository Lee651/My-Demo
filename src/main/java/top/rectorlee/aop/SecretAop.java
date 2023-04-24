package top.rectorlee.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.rectorlee.annotation.Secret;
import top.rectorlee.utils.AesUtil;
import top.rectorlee.utils.Result;

import java.lang.reflect.Method;

/**
 * @author Lee
 * @description 加解密注解切面类
 * @date 2023-04-24  17:49:11
 */
@Aspect
@Component
@Slf4j
public class SecretAop {
    /**
     * 定义切点(类或方法使用了@Secret注解的地方)
     */
    @Pointcut("@annotation(top.rectorlee.annotation.Secret)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 被代理的对象
        Object target = point.getTarget();
        // 被代理方法的参数
        Object[] args = point.getArgs();
        // 获取通知签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 获取被代理的方法
        Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        // 获取被代理方法上的注解
        Secret secret = method.getAnnotation(Secret.class);
        // 最终返回对象
        Object respObj;

        try {
            String[] reqPropsName = secret.reqPropsName();
            String[] respPropsName = secret.respPropsName();

            // 对请求对象进行加密
            if (!ObjectUtils.isEmpty(reqPropsName)) {
                // 获取请求对象，如果参数只有一个并且是对象，则进行加密处理
                if (!ObjectUtils.isEmpty(args) && args.length == 1) {
                    Object arg = args[0];

                    // 遍历要加密的属性，和请求对象中的属性进行匹配
                    for (String propName : reqPropsName) {
                        // 得到匹配上的属性值
                        String propVal = (String) arg.getClass().getMethod(getterName(propName)).invoke(arg);

                        // 加密
                        String propEncrypt = AesUtil.encrypt(propVal);

                        // 设置加密的属性值
                        arg.getClass().getMethod(setterName(propName), String.class).invoke(arg, propEncrypt);
                    }
                } else if (args.length > 1) {
                    // todo 这里可自行扩展以满足其他请求对象及方式，如多个参数、非对象形式的参数等等。
                }
            }

            // 继续向下执行后得到的响应结果
            respObj = point.proceed();

            // 对响应对象进行解密
            if (!ObjectUtils.isEmpty(respPropsName)) {
                // 获取响应对象
                Object result = ((Result<?>)respObj).getData();
                log.info("result: {}", result);

                for (String propName : respPropsName) {
                    // 获取要解密的属性值
                    String propVal = (String) result.getClass().getMethod(getterName(propName)).invoke(result);

                    // 解密
                    String propDecrypt = AesUtil.decrypt(propVal);

                    // 设置解密的属性值
                    result.getClass().getMethod(setterName(propName), String.class).invoke(result, propDecrypt);
                }
            }
        } catch (Exception ex) {
            // 发生异常记录下日志，但正常执行下去，也可以不try..catch直接通过全局异常处理。
            log.error("加解密异常: {}", ex.getMessage(), ex);
            respObj = point.proceed();
        }

        return respObj;
    }

    /**
     * 转化get方法名
     */
    private String getterName(String name) {
        String first = name.substring(0, 1);
        String last = name.substring(1);
        return "get" + first.toUpperCase() + last;
    }

    /**
     * 转化set方法名
     */
    private String setterName(String name) {
        String first = name.substring(0, 1);
        String last = name.substring(1);
        return "set" + first.toUpperCase() + last;
    }
}
