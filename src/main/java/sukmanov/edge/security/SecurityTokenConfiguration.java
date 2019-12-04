package sukmanov.edge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfiguration extends WebSecurityConfigurerAdapter {
    private static final String RESUME_ADMIN_PATTERN = "/resume/admin/**";
    private static final String ADMIN_ROLE = "ADMIN";

    private JwtConfig jwtConfig;

    public SecurityTokenConfiguration(@Autowired JwtConfig jwtConfig) { this.jwtConfig = jwtConfig; }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // make sure we use stateless session; session won't be used to store user's state.
                .and().exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) // handle an authorized attempts
                .and().addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class) // Add a filter to validate the tokens with every request
                .authorizeRequests().antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll() // allow all who are accessing "auth" service
                .antMatchers(RESUME_ADMIN_PATTERN).hasRole(ADMIN_ROLE) // must be an admin if trying to access admin area (authentication is also required here)
                .anyRequest().authenticated(); // Any other request must be authenticated
    }
}
