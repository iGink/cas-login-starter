package top.gink.cas.anno;

import org.springframework.context.annotation.Import;
import top.gink.cas.config.CasConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CasConfiguration.class)
public @interface EnableCasLogin {
    boolean enabled() default true;
}
