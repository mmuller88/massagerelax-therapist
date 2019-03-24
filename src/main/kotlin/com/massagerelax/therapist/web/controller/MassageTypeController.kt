package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.dto.*
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.module.JpaMassageTypeService
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("/api")
class MassageTypeController(private val jpaMassageTypeService: JpaMassageTypeService) {

    private val LOGGER = LoggerFactory.getLogger(MassageTypeController::class.java)

    @GetMapping("/massagetypes")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun getAllMassageTypes(): ResponseEntity<List<MassageTypeDTO>> {
        LOGGER.info("MassageType get all {}")
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageTypes().map { massageTypeEntity -> massageTypeEntity.toDto()})
    }

    @PostMapping("/massagetypes")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: massageTypeBodyCreate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun createNewMassageTypes(@Valid @RequestBody massageTypeBodyCreate: CreateMassageTypeDTO): ResponseEntity<MassageTypeDTO> {
        LOGGER.info("MassageType addl {}", massageTypeBodyCreate)
        return ResponseEntity.ok(jpaMassageTypeService.addMassageType(MassageTypeEntity.fromDto(massageTypeBodyCreate)).toDto())
    }

    @GetMapping("/massagetypes/{massageTypeId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun getMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long): ResponseEntity<MassageTypeDTO> {
        LOGGER.info("MassageType get with id {}", MassageTypeId)
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageType(MassageTypeId).toDto())
    }

    @GetMapping("/massagetypes/{massageTypeId}/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun getMassageTypeWithTherapistsById(@PathVariable(value = "massageTypeId") MassageTypeId: Long): ResponseEntity<List<TherapistDTO>> {
        LOGGER.info("MassageType get all therapists by massagetype id {}", MassageTypeId)
        return ResponseEntity.ok(jpaMassageTypeService.retrieveMassageType(MassageTypeId).therapists.map { therapistEntity -> therapistEntity.toDto() })
    }

    @PutMapping("/massagetypes/{massageTypeId}")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyUpdate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun updateMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long,
                          @Valid @RequestBody massageTypeBodyUpdate: UpdateMassageTypeDTO): ResponseEntity<MassageTypeDTO> {
        LOGGER.info("MassageType update by id {}", MassageTypeId, massageTypeBodyUpdate)
        return ResponseEntity.ok(jpaMassageTypeService.updateMassageType(MassageTypeId, MassageTypeEntity.fromDto(massageTypeBodyUpdate)).toDto())
    }

    @DeleteMapping("/massagetypes/{massageTypeId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "massageTypeId don't exists", response = ErrorResponse::class)
    ])
    fun deleteMassageTypeById(@PathVariable(value = "massageTypeId") MassageTypeId: Long) {
        LOGGER.info("MassageType delete by id {}", MassageTypeId)
        return jpaMassageTypeService.deleteMassageType(MassageTypeId)
    }

}