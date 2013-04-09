package au.com.silverquest.flexigroup;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 16/02/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource("classpath:flexigroup.properties")
@ComponentScan(
        basePackages = {"au.com.silverquest.flexigroup.model"},
        basePackageClasses = CommonApplicationConfig.class,
        excludeFilters = {@ComponentScan.Filter(Configuration.class)}
)
@EnableJpaRepositories(
        basePackages = "au.com.silverquest.flexigroup.model.repository",
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManagerFactory"
)
@EnableTransactionManagement
public class CommonApplicationConfig extends WebMvcConfigurerAdapter {

    final Logger log = LoggerFactory.getLogger(CommonApplicationConfig.class);

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        log.debug("Creating the dataSource()");

        String driver = env.getProperty("db.driver");
        String jdbcUrl = System.getProperty("JDBC_CONNECTION_STRING") != null ? System.getProperty("JDBC_CONNECTION_STRING") : env.getProperty("db.url");
        String validationQuery = env.getProperty("db.validationQuery");
        Integer initialSize = new Integer(env.getProperty("db.connection.initial"));
        Integer maxConnections = new Integer(env.getProperty("db.connection.max"));

        log.debug("Database Url: " + jdbcUrl);

        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(driver);
        poolProperties.setUrl(jdbcUrl);
        poolProperties.setTestOnConnect(true);
        poolProperties.setTestOnBorrow(true);
        poolProperties.setValidationQuery(validationQuery);
        poolProperties.setInitialSize(initialSize);
        poolProperties.setMaxActive(maxConnections);

        log.debug(poolProperties.toString());

        final org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);

        log.debug("dataSource all ready to go");
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        log.debug("Creating entityManagerFactory()");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        log.debug("Scanning packages: au.com.silverquest.flexigroup.model.entity");
        factory.setPackagesToScan("au.com.silverquest.flexigroup.model.entity");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager() {
        log.debug("Creating the entityManager()");
        EntityManagerFactory emf = entityManagerFactory();
        return emf.createEntityManager();
    }

    @Bean
    public JpaDialect jpaDialect() {
        log.debug("Creating the jpaDialect()");
        return new HibernateJpaDialect();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        log.debug("Creating the transactionManager()");
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("Adding Interceptors");
        // We need the JPA EntityManagerFactory so we can keep our "Session" open during web requests for Lazy Loading!
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(entityManagerFactory());
        registry.addWebRequestInterceptor(interceptor);
    }
}
