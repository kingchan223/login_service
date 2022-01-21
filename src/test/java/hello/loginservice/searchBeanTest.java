package hello.loginservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@SpringBootTest
public class searchBeanTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LoginServiceApplication.class);

    @Test
    void findAllBeans() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String s : beansOfType.keySet()) {
            System.out.println(s);
        }
    }
}
