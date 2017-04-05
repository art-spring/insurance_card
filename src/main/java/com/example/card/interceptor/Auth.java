package com.example.card.interceptor;

import java.lang.annotation.*;

/**
 * Created by caichunyi on 2017/3/31.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
