package com.massagerelax.therapist.web.controller.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name="area", path = "/api", url = "\${area.url}")
interface AreaServiceClient {

    @GetMapping("/therapists/long/{long}/lat/{lat}")
    fun retrieveTherapists(@PathVariable("long") long: Double, @PathVariable("lat") lat: Double) : List<String>

    @GetMapping("/alive")
    fun checkAlive() : String

}