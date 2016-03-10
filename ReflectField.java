package com.zhongqinglv.platform.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD})
public @interface ReflectField {
    int ordinal() default 0;

    String name() default "";

    String format() default "";

    boolean serialize() default true;

    boolean deserialize() default true;
}
