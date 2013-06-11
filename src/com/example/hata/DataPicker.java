package com.example.hata;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Максим
 * Date: 18.03.13
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */
public class DataPicker {
    private Object picker;
    private Class<?> classPicker;

    public DataPicker(Object picker) {
        this.picker = picker;
        classPicker = picker.getClass();
    }

    public void setRange(int start, int end){
        try {
            Method m = classPicker.getMethod("setRange", int.class, int.class);
            m.invoke(picker,start, end);
        }catch (Exception e){

        }
    }

    public Integer getCurrent() {
        Integer current = -1;
        try {
            Method m = classPicker.getMethod("getCurrent");
            current = (Integer) m.invoke(picker);
        } catch (Exception e) {
        }
        return current;
    }

    public void setCurrent(int current) {
        try {
            Method m = classPicker.getMethod("setCurrent", int.class);
            m.invoke(picker, current);
        } catch (Exception e) {
        }
    }

}
