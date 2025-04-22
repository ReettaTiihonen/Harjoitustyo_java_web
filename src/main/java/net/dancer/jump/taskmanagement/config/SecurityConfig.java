package net.dancer.jump.taskmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // Public pages
                                .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Custom login page
                                .permitAll() // Allow access to login page
                )
                .logout(logout ->
                        logout
                                .permitAll() // Allow logout
                );
        http
                .securityContext((context) -> context.requireExplicitSave(false)) // tämä joskus auttaa Vaadinin kanssa
                .csrf(csrf -> csrf.disable()) // Vaadinilla CSRF usein hoidetaan muuten
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/manifest.webmanifest", "/sw.js", "/offline.html", "/icons/**", "/images/**", "/styles/**").permitAll();
                    auth.anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build());

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
