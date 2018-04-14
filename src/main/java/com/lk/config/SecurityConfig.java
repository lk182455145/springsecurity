package com.lk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import com.lk.authenticationprovider.DaoImplAuthenticationProvider;
import com.lk.service.impl.UserServicce;

@Configuration
@EnableWebSecurity//感觉配不配之没什么区别,因为在pom加入了spring-boot-starter-security中已经启动了
/**
 * Spring Security默认是禁用注解的，要想开启注解，需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，来判断用户对某个控制层(Controller)的方法是否具有访问权限
 * prePostEnabled = true
 * 	@PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
 * 	例如：@PreAuthorize("hasRole(‘admin‘)"),@PreAuthorize("#userId == authentication.principal.userId or hasAuthority(‘ADMIN’)")
 * 	@PostAuthorize 在方法执行之后执行,但是如果表达式计算结果为false,将抛出一个安全性异常
 * 	@PostFilter 在方法执行之后执行,但必须按照表达式来过滤方法的结果
 * 	@PreFilter 在方法执行之前执行,但必须在进入方法之前过滤输入值
 * securedEnabled = true
 * 	@Secured认证是否有权限访问，
 * 	例如：@Secured("IS_AUTHENTICATED_ANONYMOUSLY")public Account readAccount(Long id); ,@Secured("ROLE_TELLER")
 * */
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserServicce userServie;
	
	@Autowired
	private AuthenticationSuccessHandler loginSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler loginFailureHandler;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
    private SessionRegistry sessionRegistry;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(getAuthenticationProvider());
		auth.userDetailsService(userServie); //默认是DaoAuthenticationProvider认证
	}

	/* 
	 * xxx.permitAll() xxx里的请求不需要登陆就可以访问
	 * anyRequest() 代表任何请求
	 * xxx.authenticated() xxx里的请求必须登陆访问
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers(HttpMethod.POST, "/login").permitAll().
		antMatchers("/", "/index.html").permitAll().
		antMatchers("/w/role/a").hasRole("ADMIN").
		anyRequest().authenticated().
		and().rememberMe(). //需在自定义页面加个name为remember-me的checkbos
		and().formLogin().//会加载一个spring security自己的登陆页面 
		loginPage("/index.html").loginProcessingUrl("/login"). // 登陆页面是index.html 登陆请求是login
		successHandler(loginSuccessHandler).
		failureHandler(loginFailureHandler).
		and().logout().logoutSuccessHandler(logoutSuccessHandler);
		
		http.httpBasic().disable();
		
		http.csrf().disable();
		
		/**
		 * 
		 * 配置session并发集群之后一下配置无效,经测试如果在同一应用（IP和端口相同）登陆的话还是会生效。
		 * 
		 * session管理相关
		 * maximumSessions设置每个用户最大会话数，
		 * maxSessionsPreventsLogin:为true时会阻止新用户上线，false时会踢掉原来会话,建议设置为false,默认是false
		 * 因为当用户直接关闭浏览器时，并不一定会发出登出请求，这就导致服务器的session不会失效。从而导致新的会话不能上线
		 * expiredUrl：当用户session失效时，会发送302响应到指定的地址。我们需要处理相关的请求，如果是spa应该怎么
		 * 当再次发送请求是，就会进入entrypoint进行登录过程了
		 * 
		 * */
		 http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/index.html");
		
//		 http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).sessionRegistry(sessionRegistry).expiredUrl("/index.html");
		
	}

//	@Bean
//	public PasswordEncoder getPasswordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
	
	@ConditionalOnMissingBean(value = AuthenticationFailureHandler.class)
	@Bean
	public AuthenticationFailureHandler getAuthenticationFailureHandler(){
		return new LoginFailureHandler();
	}
	
	@ConditionalOnMissingBean(value = AuthenticationSuccessHandler.class)
	@Bean
	public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
		return new LoginSuccessHandler();
	}
	
	@ConditionalOnMissingBean(value = LogoutSuccessHandler.class)
	@Bean
	public LogoutSuccessHandler getLogoutSuccessHandler(){
		return new LogoutHandler();
	}
	
    @Bean
    public SessionRegistry getSessionRegistry(){
    	return new SessionRegistryImpl();
    }
	
//	@Bean
//	public AuthenticationProvider getAuthenticationProvider(){
//		DaoImplAuthenticationProvider pro = new DaoImplAuthenticationProvider();
//		pro.setUserDetailsService(userServie);
//		pro.setHideUserNotFoundExceptions(false); //设置自定义异常信息
//		return pro;
//	}
}
