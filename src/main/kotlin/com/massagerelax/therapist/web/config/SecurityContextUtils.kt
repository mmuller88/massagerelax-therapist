package com.massagerelax.therapist.web.config

import java.util.HashSet
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
object SecurityContextUtils {

    private val LOGGER = LoggerFactory.getLogger(SecurityContextUtils::class.java)

    private val ANONYMOUS = "anonymous"

    private val ADMIN_ROLE = "ROLE_ADMIN"

    val userName: String
        get() {
            val securityContext = SecurityContextHolder.getContext()
            val authentication = securityContext.authentication
            var username = ANONYMOUS

            if (null != authentication) {
                if (authentication.principal is UserDetails) {
                    val springSecurityUser = authentication.principal as UserDetails
                    username = springSecurityUser.username

                } else if (authentication.principal is String) {
                    username = authentication.principal as String

                } else {
                    LOGGER.debug("User details not found in Security Context")
                }
            } else {
                LOGGER.debug("Request not authenticated, hence no user name available")
            }

            return username
        }

    val userRoles: Set<String>
        get() {
            val securityContext = SecurityContextHolder.getContext()
            val authentication = securityContext.authentication
            val roles = HashSet<String>()

            authentication?.authorities?.forEach { e -> roles.add(e.authority) }
            return roles
        }

    fun isAdmin(): Boolean = userRoles.contains(ADMIN_ROLE)
}