import com.haybble.ropc.dto.LoginRequest
import com.haybble.ropc.filter.JWTFilter
import com.haybble.ropc.security.AuthEntryPointJwt
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfiguration(
    private val unauthorizedHandler: AuthEntryPointJwt,
    private val authTokenFilter: JWTFilter,
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {


    @Autowired
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)

    }



    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }



    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {

        httpSecurity.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
            .antMatchers("**/api/v1/authenticate").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(unauthorizedHandler)
        httpSecurity.addFilterAfter(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
         httpSecurity.build()
    }


}

