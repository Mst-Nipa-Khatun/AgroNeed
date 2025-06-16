package com.nipa.agroneed.config;

import com.nipa.agroneed.service.Impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    public static final String COOKIES_JWT_TOKEN_KEY = "jwtToken";
    public static final int COOKIES_JWT_TOKEN_MAX_AGE = 24 * 60 * 60;// 1 day expiry

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final JwtAuthAndCookieFilter jwtAuthAndCookieFilter;

    @Value("${server.port}")
    private Integer port;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, MyAuthenticationEntryPoint myAuthenticationEntryPoint, JwtAuthAndCookieFilter jwtAuthAndCookieFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.jwtAuthAndCookieFilter = jwtAuthAndCookieFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //securityFilterChain is in spring set security configuration
        http
                // csrf-cross site request forgery when .disable means off the csrf protecion,Basically csrf alsways enable
                //if we want,we can off this protection howerver in this application we pass the jwt token thats why we disable this csrf protection
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  //cors-Cross origin resources sharing is browser side security feature,where we can API access one domain to another domain.
                .exceptionHandling(ex -> ex.authenticationEntryPoint(myAuthenticationEntryPoint)) //exception handle kora hoyeche,when unauthorised user can access in API then show the message 401,403.here we create a custom class where we decelare
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//In this application we use jwt thats why no need to store any session.here stateless means every request can be checked newly,don't create any session.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/", "/pages/**", "/error", "/register",
                                "/webjars/**", "/css/**", "/images/**", "/favicon.ico",
                                "/favicon.png", "/shared/**", "/webjars/AdminLTE/**",
                                "/Categories/getAll","/users/create","/shoppingCart","/shoppingCart/getAll",
                                "/orders/placeOrder","/orders/getAll","/viewAllOrders","/orders/ordersUpdate","/application-static-image/**",
                                "/Products/create"//todo will need to authenticated dynamically
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                //added jwt token authentication filter
                .addFilterBefore(jwtAuthAndCookieFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build(); //lastly register in spring security
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Allow frontend domains only (DO NOT USE "*")
        configuration.setAllowedOrigins(List.of("http://localhost:" + port, "https://yourfrontend.com"));

        // ✅ Define HTTP methods allowed
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Allow specific headers (Authorization needed for JWT)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // ✅ Allow credentials (cookies, Authorization headers)
        configuration.setAllowCredentials(true);

        // ✅ Register the CORS policy for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
