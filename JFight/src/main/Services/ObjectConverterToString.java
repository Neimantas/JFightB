package main.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectConverterToString {

    public static List<Map<String, String>> listOfObjectsToStringList(List listOfObjects) {
        List<Map<String, String>> list = new ArrayList<>();
        listOfObjects.forEach(ob -> list.add(objectToStringMap(ob)));
        return list;
    }

    public static <T> Map<String, String> objectToStringMap(T object) {
        try {
            Map<String, String> map = new HashMap<>();
            Class<?> objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                String name = field.getName();
                field.setAccessible(true);
                String value = String.valueOf(field.get(object));
                map.put(name, value);
            }
            return map;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
