package net.gvmtool.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@Configuration
class SecurityConfiguration {

    static RESOURCE_ID = "rest-service"
    static GRANT_TYPE = "password"
    static AUTHORITY = "USER"

    @Configuration
    @EnableResourceServer
    static class ResourceServerConfiguration extends
        ResourceServerConfigurerAdapter {

        void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID)
        }

        void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .regexMatchers(HttpMethod.POST, "/announce/.*").authenticated()
                .regexMatchers(HttpMethod.GET, "/admin/((?!health|info).)*").authenticated()
        }

    }

    @Configuration
    @EnableAuthorizationServer
    static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        TokenStore tokenStore = new InMemoryTokenStore()

        @Autowired
        @Qualifier("authenticationManagerBean")
        AuthenticationManager authenticationManager

        @Value("#{systemEnvironment['CLIENT_ID']}")
        String clientId = "client_id"

        @Value("#{systemEnvironment['CLIENT_SECRET']}")
        String clientSecret = "client_secret"

        void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
        }

        void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                .inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .authorizedGrantTypes(GRANT_TYPE)
                .authorities(AUTHORITY)
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID)
        }

    }

    @Configuration
    @EnableWebSecurity
    static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
}
