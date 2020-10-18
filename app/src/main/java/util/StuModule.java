package util;

import dagger.Module;
import dagger.Provides;
import scope.StuScope;

@StuScope
@Module
public class StuModule {
    @StuScope
    @Provides
    public Stu getStu(){
        return  new Stu();
    }
}
