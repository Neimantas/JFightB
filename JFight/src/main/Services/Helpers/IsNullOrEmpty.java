package main.Services.Helpers;

import java.lang.reflect.Field;

public final class IsNullOrEmpty {

    private IsNullOrEmpty(){}

    public static <T> boolean isRegParamsNull(T model) throws IllegalAccessException {

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (field.get(model) != null && fieldType.equals(String.class)) {
                String a = (String) field.get(model);
                if (a.isEmpty()) {
                    return true;
                }
            } else if (field.get(model) == null){
                return true;
            }
        }
        return false;
    }
}
