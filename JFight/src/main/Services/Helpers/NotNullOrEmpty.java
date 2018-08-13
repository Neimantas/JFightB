package main.Services.Helpers;

import java.lang.reflect.Field;
//Review. Rename -> EmptyChecker
public final class NotNullOrEmpty {

    private NotNullOrEmpty(){}
//Review. Please comment in detail why you had idea to use this?
    //Review. Name change -> isNullOrEmpty
    public static <T> boolean paramsNotNullOrEmpty(T model) {

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            try {
            	//Review. What about Integer is 0;
            	//Review. What kind of type default values are considered null and which are not??
                if (field.get(model) != null && fieldType.equals(String.class)) {
                    String a = (String) field.get(model);
                    if (a.isEmpty()) {
                        return false;
                    }
                } else if (field.get(model) == null) {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
        return true;
    }
}
