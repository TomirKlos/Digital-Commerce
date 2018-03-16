package pl.klaster.digitalcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

@SpringBootApplication
@ComponentScan({
        "pl.klaster.digitalcommerce.components",
        "pl.klaster.digitalcommerce.web",
        "pl.klaster"
})
public class Web {
    public static void main(String[] args) throws Exception {

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        SpringApplication.run(Web.class, args);
    }
}
