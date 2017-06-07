package com.aek.common.datasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DataSourceExchange implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable, Exception {
        DataSource dataSourceName = invocation.getMethod().getAnnotation(DataSource.class);
        MultiDataSource.setDataSourceKey(dataSourceName.name());
        Object obj = null;
        obj = invocation.proceed();
        return obj;
    }

}
