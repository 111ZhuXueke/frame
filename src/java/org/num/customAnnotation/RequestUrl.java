package org.num.customAnnotation;

import java.lang.annotation.*;

/**
 * 地址映射
 * @author zxk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RequestUrl {
    /**
     * 访问地址
     * @return
     */
    public String url() default "";
    /**
     * get/post
     */
    public enum method{ POST,GET}
    /**
     * 默认访问方法
     * @return
     */
    public method setMethod() default method.GET;
}
