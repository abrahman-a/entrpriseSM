package com.egaz.esm.ems.security.clientDetails;

import com.egaz.esm.ems.security.jwt.JWTAuthenticationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class ClientRegistrationSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Autowired
    private ClientRegistrationDetailsService userDetailsService;

//    private static final String[] SECURED_URLs = {
//            "/dprms/**"};
//
//    private static final String[] UN_SECURED_URLs = {
//            "/projects/**",
//            "/projects/{id}",
//            "/users/**",
//            "/login",
//            "/register/**",
//            "/roles/**",
//            "/documents/**",
//            "/notifications/**"
//    };

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        RequestMatcher unsecuredRequests = new OrRequestMatcher(
                new AntPathRequestMatcher("/projects/**"),
                new AntPathRequestMatcher("/projects/{id}"),
                new AntPathRequestMatcher("/users/**"),
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/register/**"),
                new AntPathRequestMatcher("/roles/**"),
                new AntPathRequestMatcher("/documents/**"),
                new AntPathRequestMatcher("/notifications/**")
        );

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(unsecuredRequests).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dprms/**")).hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                .and()
//                    .authorizeHttpRequests(authorizeRequests ->
//                            authorizeRequests
//                                    .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/")).hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
//                                    .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/register")).hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER","ROLE_USER")
//                                    .dispatcherTypeMatchers(HttpMethod.GET).hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER")
//                                    .dispatcherTypeMatchers(HttpMethod.PUT).hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
//                                    .dispatcherTypeMatchers(HttpMethod.DELETE).hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
//                    )
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
