package com.myicpc.config;

import com.myicpc.controller.converter.LocationConvertor;
import com.myicpc.controller.converter.PollOptionConvertor;
import com.myicpc.controller.converter.QuestChallengeConvertor;
import com.myicpc.controller.converter.QuestParticipantConvertor;
import com.myicpc.controller.converter.ScheduleDayConvertor;
import com.myicpc.controller.converter.TeamConvertor;
import com.myicpc.controller.converter.TeamInfoConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Smetana
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan("com.myicpc.controller")
public class WebappConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LocationConvertor locationConvertor;

    @Autowired
    private PollOptionConvertor pollOptionConvertor;

    @Autowired
    private QuestChallengeConvertor questChallengeConvertor;

    @Autowired
    private QuestParticipantConvertor questParticipantConvertor;

    @Autowired
    private ScheduleDayConvertor scheduleDayConvertor;

    @Autowired
    private TeamConvertor teamConvertor;

    @Autowired
    private TeamInfoConvertor teamInfoConvertor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(locationConvertor);
        registry.addConverter(pollOptionConvertor);
        registry.addConverter(questChallengeConvertor);
        registry.addConverter(questParticipantConvertor);
        registry.addConverter(scheduleDayConvertor);
        registry.addConverter(teamConvertor);
        registry.addConverter(teamInfoConvertor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(86400);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(86400);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(86400);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(86400);
        registry.addResourceHandler("/cache.manifest").addResourceLocations("/cache.manifest");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        List<ViewResolver> resolvers = new ArrayList<>();

        InternalResourceViewResolver internalResolver = new InternalResourceViewResolver();
        internalResolver.setPrefix("/WEB-INF/views/");
        internalResolver.setSuffix(".jsp");
        internalResolver.setViewClass(JstlView.class);
        resolvers.add(internalResolver);

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setViewResolvers(resolvers);
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String[] resources = {"classpath:i18n/text"};
        messageSource.setBasenames(resources);
        return messageSource;
    }

    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }

    @Bean
    public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
        return new SitePreferenceHandlerInterceptor();
    }

    @Bean
    public SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver() {
        return new SitePreferenceHandlerMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceResolverHandlerInterceptor());
        registry.addInterceptor(sitePreferenceHandlerInterceptor());
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
        argumentResolvers.add(sitePreferenceHandlerMethodArgumentResolver());
    }
}
