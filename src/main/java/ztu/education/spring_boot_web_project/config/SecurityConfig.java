package ztu.education.spring_boot_web_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import ztu.education.spring_boot_web_project.security.AdminAuthenticationProvider;
import ztu.education.spring_boot_web_project.security.UserAuthenticationProvider;

/**
 * Позначаємо клас, як відповідальний клас за конфігурацію безпеки
 */
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public AuthenticationProvider authenticationProviderForUser() {
            return new UserAuthenticationProvider();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authenticationProvider(authenticationProviderForUser());

            http.csrf().disable();

            http.antMatcher("/user/**")
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/dish/**", "/dishes/**").permitAll()
                    .antMatchers("/user/register", "/user/login", "/admin/login").anonymous()
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/admin/**", "/swagger-ui/**").hasRole("ADMIN")
                    // Усі інші сторінки є загальнодоступними для коректного відображення помилки 404
                    // Вказувати не обов'язково завдяки наступному налаштуванню
                    // .anyRequest().permitAll()

                    .and()
                    // Налаштування до яких запитів будуть застосовуватися правила автентифікації та авторизації
                    .requestMatchers()
                    .regexMatchers("^(?!/admin).*") // всі посилання окрім /admin

                    // Усі інші сторінки потребують аутентифікації
                    // .and().authorizeRequests().anyRequest().authenticated()

                    .and()
                    // Налаштування для входу в систему
                    .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/login")
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler("/user/login"))
                    .usernameParameter("email")
                    .passwordParameter("password")
                    // Перенаправлення на головну сторінку після успішного входу
                    // .defaultSuccessUrl("/") // якщо не вказати, то перенаправлення відбудеться на сторінку до якої був запит

                    .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/login")

                    .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");
        }
    }

    @Configuration
    @Order(2)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public AuthenticationProvider authenticationProviderForAdmin() {
            return new AdminAuthenticationProvider();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authenticationProvider(authenticationProviderForAdmin());

            http.csrf().disable();

            http.antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/user/register", "/user/login", "/admin/login").anonymous()
                    .antMatchers("/admin/**", "/swagger-ui/**").hasRole("ADMIN")

                    .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler("/admin/login"))
                    .usernameParameter("email")
                    .passwordParameter("password")
                    // Перенаправлення на головну сторінку після успішного входу
                    // .defaultSuccessUrl("/") // якщо не вказати, то перенаправлення відбудеться на сторінку до якої був запит

                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login")

                    .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");
        }
    }
}
