package util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import scope.UserScope;

/**
 * Dagger
 * 用于提供对象
 *
 * @Singleton 单例
 */

@UserScope
@Module
public class UserModule {
         @UserScope
        @Provides
        public User getUser(){
            return  new User();
        }



}
