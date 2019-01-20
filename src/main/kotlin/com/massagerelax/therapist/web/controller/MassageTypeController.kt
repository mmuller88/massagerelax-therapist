package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.dto.*
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.module.JpaMassageTypeService
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MassageTypeController(private val jpaMassageTypeService: JpaMassageTypeService) {

    @GetMapping("/massagetypes")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun getAllMassageTypes(): ResponseEntity<List<MassageTypeDTO>> {
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageTypes().map { massageTypeEntity -> massageTypeEntity.toDto()})
    }

    @PostMapping("/massagetypes")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: massageTypeBodyCreate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun createNewMassageTypes(@Valid @RequestBody massageTypeBodyCreate: CreateMassageTypeDTO): ResponseEntity<MassageTypeDTO> {
        return ResponseEntity.ok(jpaMassageTypeService.addMassageType(MassageTypeEntity.fromDto(massageTypeBodyCreate)).toDto())
    }

    @GetMapping("/massagetypes/{massageTypeId}")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun getMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long): ResponseEntity<MassageTypeDTO> {
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageType(MassageTypeId).toDto())
    }

    @GetMapping("/massagetypes/{massageTypeId}/therapists")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun getMassageTypeWithTherapistsById(@PathVariable(value = "massageTypeId") MassageTypeId: Long): ResponseEntity<List<TherapistDTO>> {
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageType(MassageTypeId).therapists.map { therapistEntity -> therapistEntity.toDto() })
    }

    @PutMapping("/massagetypes/{massageTypeId}")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyUpdate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun updateMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long,
                          @Valid @RequestBody massageTypeBodyUpdate: UpdateMassageTypeDTO): ResponseEntity<MassageTypeDTO> {
        return ResponseEntity.ok(jpaMassageTypeService.updateMassageType(MassageTypeId, MassageTypeEntity.fromDto(massageTypeBodyUpdate)).toDto())
    }

    @DeleteMapping("/massagetypes/{massageTypeId}")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun deleteMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long) {
        return jpaMassageTypeService.deleteMassageType(MassageTypeId)
    }

}