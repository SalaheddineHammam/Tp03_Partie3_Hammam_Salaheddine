package ma.fsm.tp03_partie03_hammam_salaheddine.security;

import lombok.AllArgsConstructor;
import ma.fsm.tp03_partie03_hammam_salaheddine.security.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
//    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return null;
//            }
//        };
//    }
//    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

//    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("password")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("password")).roles("USER").build(),
                User.withUsername("user3").password(passwordEncoder.encode("password")).roles("USER","ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**", "/h2-console/**").permitAll();
//        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
//        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        httpSecurity.userDetailsService(userDetailServiceImpl);
        return httpSecurity.build();
//          return httpSecurity
//                    .formLogin(Customizer.withDefaults())
//                    .authorizeHttpRequests(ar->ar.requestMatchers("/deletePatient/**").hasRole("ADMIN"))
//                    .authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasRole("ADMIN"))
//                    .authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasRole("USER"))
//                    .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
//                    .build();

    }


}
