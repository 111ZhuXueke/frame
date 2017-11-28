package org.num.customAnnotation;

import java.lang.annotation.*;

/**
 * @author zxk
 * @date 2017-11-26 15:33
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Autowired {
    /**
     * 自动注入
     * @author zxk
     * @date 2017/11/26 15:45
     */
    public boolean required() default true;
}
