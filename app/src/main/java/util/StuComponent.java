package util;

import com.example.yishutansuo.ActivityG;
import com.example.yishutansuo.MainActivity2;

import dagger.Component;
import scope.StuScope;

@StuScope
@Component(modules = {StuModule.class})
public interface StuComponent {
   // void jumpMainActivity2(ActivityG activityG);
     Stu getStu();


}
