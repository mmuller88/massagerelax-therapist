package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.config.SecurityContextUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class TherapistControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun updateTherapistById() {
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser(username = "kenia", authorities = [ "ROLE_ADMIN", "ROLE_USER" ])
    fun shouldRejectIfNoAuthentication() {
        println(SecurityContextUtils.userName)
        println(SecurityContextUtils.userRoles)
        println(SecurityContextUtils.isAdmin())
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/therapists")
                .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized)
    }
}