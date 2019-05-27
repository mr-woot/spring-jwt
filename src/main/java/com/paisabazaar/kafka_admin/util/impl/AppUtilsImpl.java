package com.paisabazaar.kafka_admin.util.impl;

import com.paisabazaar.kafka_admin.util.AppUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("appUtils")
public class AppUtilsImpl implements AppUtils {

    @Override
    public void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, this.getNullPropertyNames(src));
    }

    @Override
    public String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
