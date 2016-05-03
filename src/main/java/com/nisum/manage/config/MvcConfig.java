package com.nisum.manage.config;

import com.mongodb.Mongo;
import com.nisum.manage.persistence.ArriveStatus;
import com.nisum.manage.service.ArriveStatusServicesImpl;
import com.nisum.manage.service.scheduler.NotificationSchedulerServiceImpl;
import com.nisum.manage.service.scheduler.PurgeSchedulerServiceImpl;
import com.nisum.manage.util.EmailUtils;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;

/**
 * Created by dpinto on 29-04-2016.
 */
@EnableWebMvc
@Configuration
@EnableScheduling
@EnableMongoRepositories(basePackages = "com.nisum.manage.persistence.repositories")
@ComponentScan(basePackages = {
        "com.nisum.manage.controller",
        "com.nisum.manage.service",
        "com.nisum.manage.persistence",
        "com.nisum.manage.persistence.repositories"})
@PropertySource("classpath:config.properties")
@Import({ SecurityConfig.class })
public class MvcConfig extends WebMvcConfigurerAdapter {





    public MappingJackson2HttpMessageConverter getJsonMessageConverter(){
        return new MappingJackson2HttpMessageConverter();
    }
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.getMessageConverters().add(0, getJsonMessageConverter());
        return handlerAdapter;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }


    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("donotreply-saschile@nisum.com");
        javaMailSender.setPassword("Sa&Ce@2477$");

        Properties javaMailProperties=new Properties();

        javaMailProperties.setProperty("mail.transport.protocol","smtp");
        javaMailProperties.setProperty("mail.smtp.auth","true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable","true");

        javaMailSender.setJavaMailProperties(javaMailProperties);

        return javaMailSender;
    }


    @Bean(name = "asServices")
    public ArriveStatusServicesImpl getAsServices() {
        return  new ArriveStatusServicesImpl();
    }

    ////////////////////////////////////////////////////////
    @Bean(name = "mailServices")
    public NotificationSchedulerServiceImpl getMailServices() {
        return  new NotificationSchedulerServiceImpl();
    }
    @Bean(name = "purgeServices")
    public PurgeSchedulerServiceImpl getPurgeServices() {
        return  new PurgeSchedulerServiceImpl();
    }
    @Bean(name = "emailUtils")
    public EmailUtils getEmailUtils() {
        return  new EmailUtils();
    }

    @Bean(name = "arriveStatus")
    public ArriveStatus getArriveStatus() {
        return  new ArriveStatus();
    }
    ///////////////////////////////////////////////////////////

    /*
 * Factory bean that creates the com.mongodb.Mongo instance
 */
    public @Bean MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {

        return new SimpleMongoDbFactory(new Mongo(), "database");
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }



}
