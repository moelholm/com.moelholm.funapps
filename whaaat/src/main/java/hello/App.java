package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application's single-point-of-entry.
 */
@ComponentScan
@EnableAutoConfiguration
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public App() {
        logger.info("Current working dir is: {}", System.getProperty("user.dir"));
    }

    public static void main(String[] args) {

        logger.info("Starting Spring Boot application...");

        SpringApplication.run(App.class, args);

    }

}
