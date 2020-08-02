package com.sigsaaqyf.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // para sobreescribir la config. por defecto
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**" };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers(resources).permitAll()
        .antMatchers("/","/index").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage(loginPage)
        //super.configure(http);
    }

    
}