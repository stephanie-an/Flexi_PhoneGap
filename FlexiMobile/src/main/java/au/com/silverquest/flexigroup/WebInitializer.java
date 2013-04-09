package au.com.silverquest.flexigroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 16/02/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebInitializer implements WebApplicationInitializer {

    final Logger log = LoggerFactory.getLogger(WebInitializer.class);

    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        log.debug("Starting up WebInitializer");

        // Initialise the Spring Application with the CommonApplicationConfig class
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(ApplicationConfig.class);
        ctx.addListener(new ContextLoaderListener(rootCtx));

        log.debug("Configured the Root Context");

        // register destruction callbacks properly with JSF

        ServletRegistration.Dynamic dispatcher = ctx.addServlet("dispatcher", new DispatcherServlet(rootCtx));
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/service/*");
        log.debug("Configured the Web Context");
    }
}
