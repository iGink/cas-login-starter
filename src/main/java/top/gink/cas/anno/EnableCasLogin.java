package top.gink.cas.anno;

import org.springframework.context.annotation.Import;
import top.gink.cas.config.CasConfiguration;
import top.gink.cas.config.CasMvcConfiguration;
import top.gink.cas.controller.CasController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CasConfiguration.class, CasMvcConfiguration.class, CasController.class})
public @interface EnableCasLogin {
}
