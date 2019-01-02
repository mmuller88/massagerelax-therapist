package com.massagerelax.therapist.web.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration


@Component
@Configuration
@ConfigurationProperties(prefix = "rest.security")
class SecurityProperties {

    private var enabled: Boolean = false

    fun getEnabled() = enabled
    fun setEnabled(b: Boolean) {
        enabled = b
    }

    lateinit var apiMatcher: String
    private var cors: Cors? = null
    fun getCors() = cors
    fun setCors(cors: Cors) {
        this.cors = cors
    }

    lateinit var issuerUri: String

    val corsConfiguration: CorsConfiguration
        get() {
            val corsConfiguration = CorsConfiguration()
            corsConfiguration.allowedOrigins = cors!!.allowedOrigins
            corsConfiguration.allowedMethods = cors!!.allowedMethods
            corsConfiguration.allowedHeaders = cors!!.allowedHeaders
            corsConfiguration.exposedHeaders = cors!!.exposedHeaders
            corsConfiguration.allowCredentials = cors!!.getAllowCredentials()
            corsConfiguration.maxAge = cors!!.getMaxAge()

            return corsConfiguration
        }

    class Cors {

        var allowedOrigins: List<String>? = null
        var allowedMethods: List<String>? = null
        var allowedHeaders: List<String>? = null
        var exposedHeaders: List<String>? = null
        private var allowCredentials: Boolean = false
        fun getAllowCredentials() = allowCredentials
        fun setAllowCredentials(b: Boolean) {
            allowCredentials = b
        }
        private var maxAge: Long = 0
        fun getMaxAge() = maxAge
        fun setMaxAge(maxAge: Long) {
            this.maxAge = maxAge
        }
    }

}