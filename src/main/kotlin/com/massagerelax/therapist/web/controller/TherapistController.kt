package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.web.dto.CreateTherapistDTO
import com.massagerelax.therapist.web.dto.TherapistDTO
import com.massagerelax.therapist.web.dto.UpdateTherapistDTO
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.module.JpaTherapistService
import com.massagerelax.therapist.web.controller.client.AreaServiceClient
import com.massagerelax.therapist.web.controller.client.Employee
import com.massagerelax.therapist.web.controller.client.EmployeeClient
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
//@RequestMapping("/api")
class TherapistController(private val jpaTherapistService: JpaTherapistService) {

    private val LOGGER = LoggerFactory.getLogger(TherapistController::class.java)

    @Autowired
    lateinit var employeeClient: EmployeeClient

    @GetMapping("/feign")
    fun listRest(): List<Employee> {
        return employeeClient.findByDepartment("1")
    }

    @GetMapping("/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun getAllTherapists(): ResponseEntity<List<TherapistDTO>> {
        LOGGER.info("Therapist get all {}")
        return ResponseEntity.ok(jpaTherapistService.retrieveTherapists().map { therapistEntity -> therapistEntity.toDto()})
    }

    @Autowired
    lateinit var areaServiceClient: AreaServiceClient

    @GetMapping("/therapists/long/{long}/lat/{lat}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "Area Service not found", response = ErrorResponse::class)
    ])
    fun getAllTherapistsWithinTheirRadius(@PathVariable("long") long: Double, @PathVariable("lat") lat: Double): ResponseEntity<List<String>> {
        LOGGER.info("Therapist get by long lat {}", long, lat)
        return ResponseEntity.ok(areaServiceClient.retrieveTherapists(long, lat))
    }

    @GetMapping("/therapists/{therapistId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun getTherapistById(@PathVariable(value = "therapistId") therapistId: Long): ResponseEntity<TherapistDTO> {
        LOGGER.info("Therapist get by id {}", therapistId)
        return ResponseEntity.ok(jpaTherapistService.retrieveTherapist(therapistId).toDto())
    }

    @PostMapping("/therapists")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "Invalid parameter: therapistBodyCreate is invalid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class)
    ])
    fun createNewTherapist(@Valid @RequestBody therapistBodyCreate: CreateTherapistDTO): ResponseEntity<TherapistDTO> {
        LOGGER.info("Therapist add {}", therapistBodyCreate)
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
        LOGGER.info("Therapist update {}", therapistId, therapistBodyUpdate)
        val updatedTherapist = jpaTherapistService.retrieveTherapist(therapistId).copy(
                name = therapistBodyUpdate.name,
                description = therapistBodyUpdate.description,
                number = therapistBodyUpdate.number,
                mobile_table = therapistBodyUpdate.mobileTable
        )
        return ResponseEntity.ok(jpaTherapistService.updateTherapist(therapistId, updatedTherapist).toDto())
    }

    @DeleteMapping("/therapists/{therapistId}")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun deleteTherapistById(@PathVariable(value = "therapistId") therapistId: Long): ResponseEntity<Void> {
        LOGGER.info("Therapist delete {}", therapistId)
        jpaTherapistService.deleteTherapist(therapistId)
        return ResponseEntity.noContent().build()
    }

}