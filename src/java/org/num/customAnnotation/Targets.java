package org.num.customAnnotation;

import java.lang.annotation.ElementType;

@Targets({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface Targets {
    ElementType[] value();
}