package top.rectorlee.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.rectorlee.enums.SensitiveStrategy;
import top.rectorlee.utils.SensitiveJsonSerializer;

import java.lang.annotation.*;

/**
 * 脱敏注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {
    // 脱敏策略
    SensitiveStrategy strategy();
}
