package org.num.customAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface RequestPramameter {
    /**
     * 获取一个类对象
     * @return
     */
    public Class parms();
}
