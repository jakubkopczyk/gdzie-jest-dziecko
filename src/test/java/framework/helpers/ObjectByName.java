package framework.helpers;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static core.Capabilities.driver;

public class ObjectByName {
    public static Map<String, String> pages;
    static String pageObjectPackage = "page_object";
    static{
        pages= new HashMap<>();
        pages.put("welcome", pageObjectPackage+"."+"WelcomePage");
        pages.put("home", pageObjectPackage + "." + "HomePage");
    }


    public static Object getObjectByName(String className) {
        Object o = null;
        try {
            Class<?> cls = Class.forName(className);
            Constructor<?> ct = cls.getConstructor(AppiumDriver.class);
            o = ct.newInstance(driver);

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void invokeClassMethod(String className, String method) throws Exception {
        try {
            Class<?> cls = Class.forName(className);
            Method mt = cls.getMethod(method);
            Object ob = ObjectByName.getObjectByName(className);
            mt.invoke(ob);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                throw (Exception) e.getCause();
            }
        }

    }

    public static Boolean invokeClassMethod(String className, String method, String argument) throws Exception {
        Boolean result = null;
        try {
            Class<?> cls = Class.forName(className);
            Object ob = ObjectByName.getObjectByName(className);
            Method mt = cls.getMethod(method, String.class);
            result = (Boolean) mt.invoke(ob, argument);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                throw (Exception) e.getCause();
            }
        }
        return result;

    }

}
