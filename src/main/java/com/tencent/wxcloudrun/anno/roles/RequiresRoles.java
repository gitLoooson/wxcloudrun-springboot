package com.tencent.wxcloudrun.anno.roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {
    RoleEnum[] value(); // 使用枚举值而不是字符串
    Logical logical() default Logical.AND; // AND表示需要所有角色，OR表示任一角色
}

