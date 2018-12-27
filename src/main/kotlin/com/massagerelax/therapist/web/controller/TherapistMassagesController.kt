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

    @GetMapping("/therapists/{therapistId}/massages")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun getMassagesByTherapistId(@PathVariable(value = "therapistId") therapistId: Long): List<MassageTypeDTO> {

        return jpaTherapistService.retrieveTherapist(therapistId).massageTypes.map { massage -> massage.toDto() }
    }

    @PostMapping("/therapists/{therapistId}/massages")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistMassageBodyAdd is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun addTherapistMassage(
            @PathVariable(value = "therapistId") therapistId: Long,
            @Valid @RequestBody therapistMassageBodyAdd: Long): ResponseEntity<TherapistEntity> {
        // get massage type
        val massageType = jpaMassageTypeService.retrieveMassageType(therapistMassageBodyAdd)
        return ResponseEntity.ok(jpaTherapistService.addTherapistMassage(therapistId, massageType))
    }

    @DeleteMapping("/therapists/{therapistId}/massages/{massageTypeId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun deleteTherapistMassage(
            @PathVariable(value = "therapistId") therapistId: Long,
            @PathVariable(value = "massageTypeId") massageTypeId: Long): ResponseEntity<String>? {
        // get massage type
        val massageType = jpaMassageTypeService.retrieveMassageType(massageTypeId)
        jpaTherapistService.deleteTherapistMassage(therapistId, massageType)
        return ResponseEntity.noContent().build()
    }

}