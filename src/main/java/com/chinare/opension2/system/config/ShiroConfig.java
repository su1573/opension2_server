package com.chinare.opension2.system.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
//import org.apache.shiro.cache.CacheManager;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinare.opension2.common.config.Constant;
import com.chinare.opension2.common.redis.shiro.RedisCacheManager;
import com.chinare.opension2.common.redis.shiro.RedisManager;
import com.chinare.opension2.common.redis.shiro.RedisSessionDAO;
import com.chinare.opension2.system.shiro.JWTShiroRealm;
import com.chinare.opension2.system.shiro.JwtAuthFilter;
import com.chinare.opension2.system.shiro.UserRealm;

import net.sf.ehcache.CacheManager;

/**
 * @author demo 1992lcg@163.com
 */
@Configuration
public class ShiroConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.cache.type}")
    private String cacheType ;

    @Value("${server.session-timeout}")
    private int tomcatTimeout;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * 设置过滤器
     */
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
//        filterMap.put("authcToken", createAuthFilter());
//        shiroFilterFactoryBean.setFilters(filterMap);
//        
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/login","anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/fonts/**", "anon");
//        filterChainDefinitionMap.put("/img/**", "anon");
//        filterChainDefinitionMap.put("/docs/**", "anon");
//        filterChainDefinitionMap.put("/druid/**", "anon");
//        filterChainDefinitionMap.put("/upload/**", "anon");
//        filterChainDefinitionMap.put("/files/**", "anon");
//        filterChainDefinitionMap.put("/logout", "logout");
//        //filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/blog", "anon");
//        filterChainDefinitionMap.put("/actuator/**", "anon");
//        filterChainDefinitionMap.put("/swagger/**", "anon");
//        filterChainDefinitionMap.put("/blog/open/**", "anon");
//        //filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//     
    
    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");  //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        chainDefinition.addPathDefinition("/loginCas","anon");
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/css/**", "anon");
        chainDefinition.addPathDefinition("/js/**", "anon");
        chainDefinition.addPathDefinition("/fonts/**", "anon");
        chainDefinition.addPathDefinition("/img/**", "anon");
        chainDefinition.addPathDefinition("/docs/**", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/upload/**", "anon");
        chainDefinition.addPathDefinition("/files/**", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/blog", "anon");
        chainDefinition.addPathDefinition("/actuator/**", "anon");
        chainDefinition.addPathDefinition("/swagger/**", "anon");
        chainDefinition.addPathDefinition("/blog/open/**", "anon");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken"); // 默认进行用户鉴权
        return chainDefinition;
    }
    
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return factoryBean;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
//        authenticator.setRealms(Arrays.asList(jwtShiroRealm(), dbShiroRealm()));
        authenticator.setRealms(Arrays.asList(jwtShiroRealm()));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
//        securityManager.setRealms(Arrays.asList(jwtShiroRealm(), dbShiroRealm()));
        securityManager.setRealms(Arrays.asList(jwtShiroRealm()));
        // 自定义缓存实现 使用redis
//        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
//            securityManager.setCacheManager(rediscacheManager());
//        } else {
//            securityManager.setCacheManager(ehCacheManager());
//        }
//        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    
    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager) throws Exception{
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
 
        return filterRegistration;
    }
 
    
    
    protected JwtAuthFilter createAuthFilter(){
        return new JwtAuthFilter();
    }
 
    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
     @Bean
     protected SessionStorageEvaluator sessionStorageEvaluator(){
         DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
         sessionStorageEvaluator.setSessionStorageEnabled(false);
         return sessionStorageEvaluator;
     }
     /**
     * 用于用户名密码登录时认证的realm
     */
//     @Bean("dbRealm")
//     public Realm dbShiroRealm() {
//         DbShiroRealm myShiroRealm = new DbShiroRealm();
//         return myShiroRealm;
//     }
     /**
     * 用于JWT token认证的realm
     */
     @Bean("jwtRealm")
     public Realm jwtShiroRealm() {
         JWTShiroRealm myShiroRealm = new JWTShiroRealm();
         return myShiroRealm;
     }

    
    @Bean
    UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        //redisManager.setTimeout(1800);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager rediscacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionDAO sessionDAO() {
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            return redisSessionDAO();
        } else {
            return new MemorySessionDAO();
        }
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
        sessionManager.setSessionDAO(sessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new BDSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(cacheManager());
        return em;
    }

    @Bean("cacheManager2")
    CacheManager cacheManager(){
        return CacheManager.create();
    }


}
