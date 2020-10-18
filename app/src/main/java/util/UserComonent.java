package util;

import com.example.yishutansuo.ActivityH;
import com.example.yishutansuo.MainActivity2;

import javax.inject.Singleton;

import dagger.Component;
import scope.UserScope;

/**
 * 组件 注入对象到其他类中
 *
 * dependencies
 */
@UserScope
@Component(modules = {UserModule.class},dependencies = {StuComponent.class})
public interface UserComonent {
        void toActivity(ActivityH activityH);
        void toMainActivity2(MainActivity2 mainActivity2);

}
