package au.com.silverquest.flexigroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
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
@ImportResource(value = {"/WEB-INF/spring-security.xml"})
@ComponentScan(
        basePackages = {
                "au.com.silverquest.flexigroup.config.scope",
                "au.com.silverquest.flexigroup.view",
                "au.com.silverquest.flexigroup.services"
        },
        basePackageClasses = ApplicationConfig.class,
        excludeFilters = {@ComponentScan.Filter(Configuration.class)}
)
@Import({CommonApplicationConfig.class})
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("Adding ResourceHandlers");
        // Allow static html in the web-root to pass through
        registry.addResourceHandler("/resources/*").addResourceLocations("/resources");
    }
}
