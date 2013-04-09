package au.com.silverquest.flexigroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 16/02/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource("classpath:flexigroup.properties")
@ComponentScan( basePackages = { "au.com.silverquest.flexigroup.services" })
@EnableWebMvc
@Import({CommonApplicationConfig.class})
@EnableTransactionManagement
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    Environment env;

    @Bean
    public String awsEmailAccessKey() {
        String awsEmailAccessKey = env.getProperty("flexi.aws.email.accesskey");
        log.debug("Getting AWS Email Access Key: " + awsEmailAccessKey);
        return awsEmailAccessKey;
    }

    @Bean
    public String awsEmailSecretKey() {
        String awsEmailSecretKey = env.getProperty("flexi.aws.email.secretkey");
        log.debug("Getting the AWS Email Secret Key: " + awsEmailSecretKey);
        return awsEmailSecretKey;
    }

    @Bean
    String flexiEmailSender() {
        String emailSender = env.getProperty("flexi.email.sender");
        log.debug("Getting the Email Sender: " + emailSender);
        return emailSender;
    }

    // MVC Config

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("Adding ResourceHandlers");
        // Let the Sencha Touch App pass through
        registry.addResourceHandler("/resources/*").addResourceLocations("/resources/");
    }
}
