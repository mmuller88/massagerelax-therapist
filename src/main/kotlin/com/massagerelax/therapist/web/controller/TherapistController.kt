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
import org.springframework.security.access.prepost.PostAuthorize
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

    @GetMapping("/therapists/{therapistId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun getTherapistById(@PathVariable(value = "therapistId") therapistId: Long): ResponseEntity<TherapistDTO> {
        return ResponseEntity.ok(jpaTherapistService.retrieveTherapist(therapistId).toDto())
    }

    @PostMapping("/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyCreate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun createNewTherapists(@Valid @RequestBody therapistBodyCreate: CreateTherapistDTO): ResponseEntity<TherapistDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(jpaTherapistService.addTherapist(TherapistEntity.fromDto(therapistBodyCreate)).toDto())
    }

    @PutMapping("/therapists/{therapistId}")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyUpdate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
        ])
//    @PostAuthorize("returnObject.username == authentication.principal.nickName")
    fun updateTherapistById(@PathVariable(value = "therapistId") therapistId: Long,
                          @Valid @RequestBody therapistBodyUpdate: UpdateTherapistDTO): ResponseEntity<TherapistDTO> {
        return ResponseEntity.ok(jpaTherapistService.updateTherapist(therapistId, TherapistEntity.fromDto(therapistBodyUpdate)).toDto())
    }

    @DeleteMapping("/therapists/{therapistId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun deleteTherapistById(@PathVariable(value = "therapistId") therapistId: Long): ResponseEntity<Void> {
        jpaTherapistService.deleteTherapist(therapistId)
        return ResponseEntity.noContent().build()
    }

}