package com.massagerelax.therapist.web.controller.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "employee")
interface EmployeeClient {

    @GetMapping("/department/{departmentId}")
    fun findByDepartment(@PathVariable("departmentId") departmentId: String): List<Employee>

}
