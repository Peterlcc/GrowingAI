package com.peter.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectUtils {
    public static List<String> getFieldNames(Class clazz){
        List<String> fieldNames = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> fieldNames.add(field.getName()));
        return fieldNames;
    }
}
