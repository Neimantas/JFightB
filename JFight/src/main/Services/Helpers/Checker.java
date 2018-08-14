package main.Services.Helpers;

import java.lang.reflect.Field;

public final class Checker {

    private Checker(){}

    public static <T> boolean isNullOrEmpty(T model) {

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            try {
            	//Review. What about Integer is 0;
            	//Review. What kind of type default values are considered null and which are not??
                if (field.get(model) != null && fieldType.equals(String.class)) {
                    String a = (String) field.get(model);
                    if (a.isEmpty()) {
                        return true;
                    }
                } else if (field.get(model) == null) {
                    return true;
                }
            } catch (Exception e) {
                Logger.error(e.getMessage());
                e.getStackTrace();
            }
        }
        return false;
    }
}
