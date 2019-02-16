package com.massagerelax.therapist.web.controller.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name="area-service", url="\${area-service.host}:\${area-service.port}")
interface AreaServiceClient {

    @GetMapping("api/therapists/long/{long}/lat/{lat}")
    fun retrieveTherapists(@PathVariable("long") long: Double, @PathVariable("lat") lat: Double) : List<String>

}