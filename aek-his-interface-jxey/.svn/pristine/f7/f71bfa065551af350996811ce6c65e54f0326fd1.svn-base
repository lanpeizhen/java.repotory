package com.aek.web.controller;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.mb.common.dal.object.AbstractDO;

public class BeanUtil {
    
    /**
     * 判断两个实体的属性是否相等。
     * @param oracle
     * @param mysql
     * @return
     */
    public static boolean judgeSame(AbstractDO oracle, AbstractDO mysql) {
        Field[] fields = oracle.getClass().getDeclaredFields();
        for (Field field : fields) {
            String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            try {
                Method m = (Method) oracle.getClass().getMethod(methodName);
                Method m1 = (Method) mysql.getClass().getMethod(methodName);
                if (m != null && m.equals(m1)) {
                    Object obj = m.invoke(oracle);
                    Object obj1 = m1.invoke(mysql);
                    if (obj != null) {
                        if (!obj.equals(obj1) || obj1 == null) {
                            return false;
                        }
                    } else {
                        if (obj == null && obj1 != null) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
