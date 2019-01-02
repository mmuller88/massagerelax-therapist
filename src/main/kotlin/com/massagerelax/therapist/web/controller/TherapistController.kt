package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.dto.CreateTherapistDTO
import com.massagerelax.therapist.web.dto.TherapistDTO
import com.massagerelax.therapist.web.dto.UpdateTherapistDTO
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.module.JpaTherapistService
import com.massagerelax.therapist.web.config.SecurityContextUtils
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize


@RestController
@RequestMapping("/api")
class TherapistController(private val jpaTherapistService: JpaTherapistService) {

    @GetMapping("/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun getAllTherapists(): ResponseEntity<List<TherapistDTO>> {
        return ResponseEntity.ok(jpaTherapistService.retrieveTherapists().map { therapistEntity -> therapistEntity.toDto()})
    }

    @GetMapping("/therapists/{therapistName}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
    ])
    fun getTherapistByName(@PathVariable(value = "therapistName") therapistName: String): ResponseEntity<TherapistDTO> {
        return ResponseEntity.ok(jpaTherapistService.retrieveTherapist(therapistName).toDto())
    }

    @PostMapping("/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyCreate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun createNewTherapists(@Valid @RequestBody therapistBodyCreate: CreateTherapistDTO): ResponseEntity<TherapistDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(jpaTherapistService.addTherapist(TherapistEntity.fromDto(therapistBodyCreate)).toDto())
    }

    @PutMapping("/therapists/{therapistName}")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyUpdate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
        ])
    fun updateTherapistByName(@PathVariable(value = "therapistName") therapistName: String,
                          @Valid @RequestBody therapistBodyUpdate: UpdateTherapistDTO): ResponseEntity<TherapistDTO> {
        return ResponseEntity.ok(jpaTherapistService.updateTherapist(therapistName, TherapistEntity.fromDto(therapistBodyUpdate)).toDto())
    }

    @DeleteMapping("/therapists/{therapistName}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistName don't exists", response = ErrorResponse::class)
    ])
    fun deleteTherapistByName(@PathVariable(value = "therapistName") therapistName: String): ResponseEntity<Void> {
        jpaTherapistService.deleteTherapist(therapistName)
        return ResponseEntity.noContent().build()
    }

}