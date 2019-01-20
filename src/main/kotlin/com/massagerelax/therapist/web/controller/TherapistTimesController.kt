package com.massagerelax.therapist.web.controller

import com.massagerelax.therapist.Helper.modifyBit
import com.massagerelax.therapist.domain.InvalidPropertyValueException
import com.massagerelax.therapist.domain.InvalidStringParameterException
import com.massagerelax.therapist.domain.module.JpaTherapistService
import com.massagerelax.therapist.web.dto.*
import com.massagerelax.therapist.web.support.ErrorResponse
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api")
class TherapistTimesController(private val jpaTherapistService: JpaTherapistService) {

    @GetMapping("/therapists/{therapistId}/working-times")
    @ApiResponses(value = [
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun getWorkingTimesByTherapistId(@PathVariable(value = "therapistId") therapistId: Long): ResponseEntity<List<WorkingDayDTO>> {
        val therapist = jpaTherapistService.retrieveTherapist(therapistId)
        val monday = WorkingDayDTO("Monday", therapist.workingDays and (1 shl 0) != 0, Hour.hourListFromInt(therapist.hoursMonday))
        val tuesday = WorkingDayDTO("Tuesday", therapist.workingDays and (1 shl 1) != 0, Hour.hourListFromInt(therapist.hoursTuesday))
        val wednesday = WorkingDayDTO("Wednesday", therapist.workingDays and (1 shl 2) != 0, Hour.hourListFromInt(therapist.hoursWednesday))
        val thursday = WorkingDayDTO("Thursday", therapist.workingDays and (1 shl 3) != 0, Hour.hourListFromInt(therapist.hoursThursday))
        val friday = WorkingDayDTO("Friday", therapist.workingDays and (1 shl 4) != 0, Hour.hourListFromInt(therapist.hoursFriday))
        val saturday = WorkingDayDTO("Saturday", therapist.workingDays and (1 shl 5) != 0, Hour.hourListFromInt(therapist.hoursSaturday))
        val sunday = WorkingDayDTO("Sunday", therapist.workingDays and (1 shl 6) != 0, Hour.hourListFromInt(therapist.hoursSunday))
        return ResponseEntity.ok(listOf(monday, tuesday, wednesday, thursday, friday, saturday, sunday))
    }

    @GetMapping("/therapists/{therapistId}/working-times/{weekDay}")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "weekDay is not valid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun getWorkingTimesByTherapistIdAndWeekDay(
            @PathVariable(value = "therapistId") therapistId: Long,
            @PathVariable(value = "weekDay") @ApiParam(value = "Weekday. Use monday, tuesday, wednesday, thursday, friday, saturday or sunday", example = "monday", required = true) weekDay: String): ResponseEntity<WorkingDayDTO> {
        val therapist = jpaTherapistService.retrieveTherapist(therapistId)
        var workingDayDTO: WorkingDayDTO?
        val weekDay = weekDay.toLowerCase()
        when (weekDay) {
            "monday" -> workingDayDTO = WorkingDayDTO("Monday", therapist.workingDays and (1 shl 0) != 0, Hour.hourListFromInt(therapist.hoursMonday))
            "tuesday" -> workingDayDTO = WorkingDayDTO("Tuesday", therapist.workingDays and (1 shl 1) != 0, Hour.hourListFromInt(therapist.hoursTuesday))
            "wednesday" -> workingDayDTO = WorkingDayDTO("Wednesday", therapist.workingDays and (1 shl 2) != 0, Hour.hourListFromInt(therapist.hoursWednesday))
            "thursday" -> workingDayDTO = WorkingDayDTO("Thursday", therapist.workingDays and (1 shl 3) != 0, Hour.hourListFromInt(therapist.hoursThursday))
            "friday" -> workingDayDTO = WorkingDayDTO("Friday", therapist.workingDays and (1 shl 4) != 0, Hour.hourListFromInt(therapist.hoursFriday))
            "saturday" -> workingDayDTO = WorkingDayDTO("Saturday", therapist.workingDays and (1 shl 5) != 0, Hour.hourListFromInt(therapist.hoursSaturday))
            "sunday" -> workingDayDTO = WorkingDayDTO("Sunday", therapist.workingDays and (1 shl 6) != 0, Hour.hourListFromInt(therapist.hoursSunday))
            else -> { throw InvalidStringParameterException(weekDay) }
        }
        return ResponseEntity.ok(workingDayDTO)
    }

    @PutMapping("/therapists/{therapistId}/working-times/{weekDay}")
    @ApiResponses(value = [
        ApiResponse(code = 400, message = "weekDay is not valid", response = ErrorResponse::class),
        ApiResponse(code = 401, message = "Authentication failed", response = ErrorResponse::class),
        ApiResponse(code = 404, message = "therapistId don't exists", response = ErrorResponse::class)
    ])
    fun updateWorkingTimesByTherapistIdAndWeekDay(
            @PathVariable(value = "therapistId") therapistId: Long,
            @PathVariable(value = "weekDay") @ApiParam(value = "Weekday. Use monday, tuesday, wednesday, thursday, friday, saturday or sunday", example = "monday", required = true) weekDay: String,
            @Valid @RequestBody therapistWorkingDayUpdate: UpdateWorkingDayDTO): ResponseEntity<WorkingDayDTO> {

        // check if the modified hours are in the right format
        for(hour in therapistWorkingDayUpdate.hours){
            if(hour.hour > 23 || hour.hour < 0) {
                throw InvalidPropertyValueException(hour.hour)
            }
        }

        val therapist = jpaTherapistService.retrieveTherapist(therapistId)
        var updatedTherapist = therapist.copy()
        val weekDay = weekDay.toLowerCase()

        // update week day flag
        when (weekDay) {
            "monday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 0, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursMonday = modifyBit(updatedTherapist.hoursMonday, hour.hour, hour.working)
                }
            }
            "tuesday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 1, therapistWorkingDayUpdate
                        .working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursTuesday = modifyBit(updatedTherapist.hoursTuesday, hour.hour, hour.working)
                }
            }
            "wednesday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 2, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursWednesday = modifyBit(updatedTherapist.hoursWednesday, hour.hour, hour.working)
                }
            }
            "thursday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 3, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursThursday = modifyBit(updatedTherapist.hoursThursday, hour.hour, hour.working)
                }
            }
            "friday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 4, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursFriday = modifyBit(updatedTherapist.hoursFriday, hour.hour, hour.working)
                }
            }
            "saturday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 5, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursSaturday = modifyBit(updatedTherapist.hoursSaturday, hour.hour, hour.working)
                }
            }
            "sunday" -> {
                updatedTherapist.workingDays = modifyBit(updatedTherapist.workingDays, 6, therapistWorkingDayUpdate.working)
                for(hour in therapistWorkingDayUpdate.hours){
                    updatedTherapist.hoursSunday = modifyBit(updatedTherapist.hoursSunday, hour.hour, hour.working)
                }
            }
            else -> { throw InvalidStringParameterException(weekDay) }
        }
        jpaTherapistService.updateTherapist(therapistId, updatedTherapist)

        return getWorkingTimesByTherapistIdAndWeekDay(therapistId, weekDay)
    }
}


