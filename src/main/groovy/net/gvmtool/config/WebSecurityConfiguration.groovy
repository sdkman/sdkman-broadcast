package net.gvmtool.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    static final ROLE = "USER"

    @Value("#{systemEnvironment['AUTH_USERNAME']}")
    String authUsername = "auth_username"

    @Value("#{systemEnvironment['AUTH_PASSWORD']}")
    String authPassword = "auth_password"

    void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(authUsername)
            .password(authPassword)
            .roles(ROLE)
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        super.authenticationManagerBean()
    }

}
