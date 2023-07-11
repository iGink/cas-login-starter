package top.gink.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gink.cas.anno.IgnoreCasLogin;

/**
 * @author Gink
 * @create 2023/7/10 16:41
 */
@RestController
public class DemoController {
    @GetMapping(path = {"", "/"})
    public String demo() {
        return "address";
    }

    @IgnoreCasLogin
    @GetMapping(path = {"ignore"})
    public String ignore() {
        return "ignore";
    }

}
