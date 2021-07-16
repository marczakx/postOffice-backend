package marczakx.traditionalMail.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WbcSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().

				withUser("vip").password("{noop}8888").roles("VIP")

				.and().

				withUser("sudden").password("{noop}0000").roles("SUDDEN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/vip/**").hasRole("VIP")

				.antMatchers(HttpMethod.OPTIONS, "/sudden/**").hasRole("SUDDEN")

				.and().httpBasic();

	}
}
