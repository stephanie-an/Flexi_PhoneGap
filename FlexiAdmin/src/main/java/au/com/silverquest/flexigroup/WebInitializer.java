package au.com.silverquest.flexigroup;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
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

        // Initialise the Spring Application with the ApplicationConfig class
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(ApplicationConfig.class);
        ctx.addListener(new ContextLoaderListener(rootCtx));

        log.debug("Configured the Root Context");

        // register destruction calllbacks properly with JSF
        ctx.addListener(new RequestContextListener());
        ServletRegistration.Dynamic appReg = ctx.addServlet("dispatcher", new DispatcherServlet(rootCtx));
        appReg.setLoadOnStartup(1);
        appReg.addMapping("/app/*");
        log.debug("Configured the Web Context");

        // Declare PrimeFaces filter in order to enable file uploading
        ctx.addFilter("Faces Servlet", new FileUploadFilter()).addMappingForUrlPatterns(null, false, "/*");


        // JSF servlet configuration; it will create its own servlet via com.sun.faces.config.FacesInitializer
        ctx.setInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME, ".xhtml");
        ctx.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.toString());
        ctx.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
    }
}
