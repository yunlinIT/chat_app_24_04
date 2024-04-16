package com.koreait.exam.chat_app_24_04.bounded_context.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class Config {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnMissingBean({OpenEntityManagerInViewInterceptor.class, OpenEntityManagerInViewFilter.class})
    @ConditionalOnMissingFilterBean(OpenEntityManagerInViewFilter.class)
    @ConditionalOnProperty(prefix = "spring.jpa", name = "open-in-view", havingValue = "true", matchIfMissing = true)
    protected static class JpaWebConfiguration {
        @Value("${custom.jpa.open-in-view.exclude-patterns}")
        private List<String> excludePatterns;

        private static final Log logger = LogFactory.getLog(Config.JpaWebConfiguration.class);

        private final JpaProperties jpaProperties;

        protected JpaWebConfiguration(JpaProperties jpaProperties) {
            this.jpaProperties = jpaProperties;
        }

        @Bean
        public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
            if (this.jpaProperties.getOpenInView() == null) {
                logger.warn("spring.jpa.open-in-view is enabled by default. "
                        + "Therefore, database queries may be performed during view "
                        + "rendering. Explicitly configure spring.jpa.open-in-view to disable this warning");
            }
            return new OpenEntityManagerInViewInterceptor();
        }

        @Bean
        public WebMvcConfigurer openEntityManagerInViewInterceptorConfigurer(
                OpenEntityManagerInViewInterceptor interceptor) {
            return new WebMvcConfigurer() {

                @Override
                public void addInterceptors(InterceptorRegistry registry) {

                    InterceptorRegistration interceptorRegistration = registry
                            .addWebRequestInterceptor(interceptor);

                    if (excludePatterns != null) {
                        interceptorRegistration.excludePathPatterns(excludePatterns);
                    }
                }

            };
        }

    }
}