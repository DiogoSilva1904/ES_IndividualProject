package es._7.todolist.configtests;

import org.springframework.security.test.context.support.WithSecurityContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockMyUserSecurityContextFactory.class)
public @interface WithMockMyUser {
    String username() default "sub";
    String userSub() default "sub";
}
