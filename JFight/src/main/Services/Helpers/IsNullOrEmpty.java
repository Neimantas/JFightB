package main.Services.Helpers;

import java.lang.reflect.Field;

public class IsNullOrEmpty {

    public <T> boolean isRegParamsNull(T model) throws IllegalAccessException {

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (field.get(model) != null) {

                if (fieldType.equals(String.class)) {
                    String a = (String) field.get(model);
                    if (a.isEmpty()) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
