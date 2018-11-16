package org.vaadin.diego.spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import com.vaadin.flow.server.VaadinServlet;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application implements BeanDefinitionRegistryPostProcessor {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registry.registerBeanDefinition("myServlet", new RootBeanDefinition(ServletRegistrationBean.class,
                () -> new ServletRegistrationBean<>(new VaadinServlet() {
                    @Override
                    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                        if (!serveStaticOrWebJarRequest(req, resp)) {
                            resp.sendError(404);
                        }
                    }
                }, "/frontend/*")));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
