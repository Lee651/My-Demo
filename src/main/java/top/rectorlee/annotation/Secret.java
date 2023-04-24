package top.rectorlee.annotation;

import java.lang.annotation.*;

/**
 * 加密解密注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secret {
    // 请求加密的属性
    String[] reqPropsName() default {"password"};

    // 响应解密的属性
    String[] respPropsName() default {"password"};
}
