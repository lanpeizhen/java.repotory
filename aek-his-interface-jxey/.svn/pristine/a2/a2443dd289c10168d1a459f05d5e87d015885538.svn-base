package com.aek.common.datasource;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSource.mysql;
 
    public static String mysql = "mysqlDataSource";
 
    public static String oracle = "oracleDataSource";
    
}