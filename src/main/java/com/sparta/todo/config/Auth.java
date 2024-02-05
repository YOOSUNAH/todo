package com.sparta.todo.config;


import jakarta.validation.Valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Valid
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {


}
