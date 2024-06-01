package com.switchwon.utils.mapper;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class SwitchwonObjectMapper {

    public static void map(Object target, Object source) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(target, source);
    }
}
