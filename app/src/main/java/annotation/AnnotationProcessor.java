package annotation;

import android.util.Log;

import com.example.yishutansuo.ActivityH;

import java.lang.reflect.Method;

public class AnnotationProcessor {
    public static void process(){
        Method[] methods = ActivityH.class.getDeclaredMethods();
        for (Method method:methods){
            Get get = method.getAnnotation(Get.class);
            if (get!=null){
                Log.e("yh",  get.value());
            }
        }
    }
}
