package cn.nonocast.misc;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PrePersistUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void pre(Object object){
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                if (field.getType().getName().equals("java.lang.Long")
                        && field.get(object) == null){
                    field.set(object,0L);
                }else if    (field.getType().getName().equals("java.lang.String")
                        && field.get(object) == null){
                    field.set(object,"");
                }else if (field.getType().getName().equals("java.util.Date")
                        && field.get(object) == null){
                    field.set(object,sdf.parse("2000-01-01"));
                }else if (field.getType().getName().equals("java.lang.Double")
                        && field.get(object) == null){
                    field.set(object,0.0d);
                }else if (field.getType().getName().equals("java.lang.Integer")
                        && field.get(object) == null){
                    field.set(object,0);
                }else if (field.getType().getName().equals("java.lang.Float")
                        && field.get(object) == null){
                    field.set(object,0.0f);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
