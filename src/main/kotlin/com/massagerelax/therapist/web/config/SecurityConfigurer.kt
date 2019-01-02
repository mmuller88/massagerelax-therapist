package com.massagerelax.therapist.web.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "rest.security", value = ["enabled"], havingValue = "true")
@Import(SecurityProperties::class)
class SecurityConfigurer : ResourceServerConfigurerAdapter() {

    @Autowired
    private val resourceServerProperties: ResourceServerProperties? = null

    @Autowired
    private val securityProperties: SecurityProperties? = null

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.resourceId(resourceServerProperties!!.resourceId)
    }


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(securityProperties!!.apiMatcher)
                .authenticated()

    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        if (null != securityProperties!!.corsConfiguration) {
            source.registerCorsConfiguration("/**", securityProperties.corsConfiguration)
        }
        return source
    }

    @Bean
    fun jwtAccessTokenCustomizer(mapper: ObjectMapper): JwtAccessTokenCustomizer {
        return JwtAccessTokenCustomizer(mapper)
    }

    @Bean
    fun oauth2RestTemplate(details: OAuth2ProtectedResourceDetails): OAuth2RestTemplate {
        val oAuth2RestTemplate = OAuth2RestTemplate(details)

        //Prepare by getting access token once
        oAuth2RestTemplate.accessToken
        return oAuth2RestTemplate
    }


}