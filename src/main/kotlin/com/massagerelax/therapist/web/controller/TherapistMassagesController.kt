package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.dto.*
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.module.JpaMassageTypeService
import com.massagerelax.therapist.domain.module.JpaTherapistService
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class TherapistMassagesController(
        private val jpaTherapistService: JpaTherapistService,
        private val jpaMassageTypeService: JpaMassageTypeService)
    {

    @GetMapping("/therapists/{therapistName}/massages")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
    ])
    fun getMassagesByTherapistName(@PathVariable(value = "therapistName") therapistName: String): List<MassageTypeDTO> {

        return jpaTherapistService.retrieveTherapist(therapistName).massageTypes.map { massage -> massage.toDto() }
    }

    @PostMapping("/therapists/{therapistName}/massages")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistMassageBodyAdd is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
    ])
    fun addTherapistMassage(
            @PathVariable(value = "therapistName") therapistName: String,
            @Valid @RequestBody therapistMassageBodyAdd: Long): ResponseEntity<TherapistEntity> {
        // get massage type
        val massageType = jpaMassageTypeService.retrieveMassageType(therapistMassageBodyAdd)
        return ResponseEntity.ok(jpaTherapistService.addTherapistMassage(therapistName, massageType))
    }

    @DeleteMapping("/therapists/{therapistName}/massages/{massageTypeId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
    ])
    fun deleteTherapistMassage(
            @PathVariable(value = "therapistName") therapistName: String,
            @PathVariable(value = "massageTypeId") massageTypeId: Long): ResponseEntity<String>? {
        // get massage type
        val massageType = jpaMassageTypeService.retrieveMassageType(massageTypeId)
        jpaTherapistService.deleteTherapistMassage(therapistName, massageType)
        return ResponseEntity.noContent().build()
    }

}