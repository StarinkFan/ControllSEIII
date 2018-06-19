package com.software2.demo.security;

import com.software2.demo.security.myUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler  myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHander;
    @Autowired
    private AuthenticationProvider provider;
    private final UserDetailsService userDetailsService;
    public SecurityConfiguration (myUserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }
/*    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;*/
    @Resource
    private SessionRegistry sessionRegistry;
    @Override
    //重写了configure参数为AuthenticationManagerBuilder的方法
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//并根据传入的AuthenticationManagerBuilder中的userDetailsService方法来接收我们自定义的认证方法。
// 且该方法必须要实现UserDetailsService这个接口。
            auth.authenticationProvider(provider);
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*http.authorizeRequests()其中这里的意思是指通过authorizeRequests()方法来开始请求权限配置。
而接着的.anyRequest().authenticated()是对http所有的请求必须通过授权认证才可以访问。
而and()是返回一个securityBuilder对象，formLogin()和httpBasic()是授权的两种方式。此处仅采用formLogin*/



        http
                .authorizeRequests()
                //我们指定任何用户都可以访问多个URL的模式。
                // 任何用户都可以访问以"/resources/","/signup", 或者 "/about"开头的URL。
                .antMatchers("/static/**","/signup.html","/findPassword.html","/homepage","/","/main/signup","/main/findSend","main/findPassword","/main/resetPW","/main/send").permitAll()
                //以 "/admin/" 开头的URL只能让拥有 "ROLE_ADMIN"角色的用户访问。
                // 请注意我们使用 hasRole 方法，没有使用 "ROLE_" 前缀。
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/main/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailHander)
                .usernameParameter("username")
                .passwordParameter("password")

                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and().csrf().disable();
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
    }
    //session失效跳转
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new SimpleRedirectSessionInformationExpiredStrategy("/loginPage");
    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //session监听器
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }
/*    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }*/




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义的 Authentication Provider

    }

}
