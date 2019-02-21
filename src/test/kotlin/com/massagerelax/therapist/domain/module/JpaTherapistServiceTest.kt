package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.domain.MassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistMassageKeyExistException
import com.massagerelax.therapist.domain.TherapistMassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistNotFoundException
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
import com.massagerelax.therapist.domain.repository.TherapistRepository
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Before
import org.mockito.BDDMockito.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class JpaTherapistServiceTest {

    @Mock
    lateinit var therapistRepository: TherapistRepository

    @Mock
    lateinit var massageTypeRepository: MassageTypeRepository

    lateinit var therapistService : JpaTherapistService

    val relax = MassageTypeEntity(
            id = 1,
            name = "Relax massage",
            description = "For relaxing")

    val swedish = MassageTypeEntity(
            id = 2,
            name = "Swedish massage",
            description = "For swedish relaxing")

    val tai = MassageTypeEntity(
            id = 3,
            name = "Tai massage",
            description = "For maximum relaxing")

    val kenia = TherapistEntity(
            id = 1,
            name = "Kenia Alves",
            description = "Martin's wife",
            number = "1233344",
            mobile_table = true,
            massageTypes = mutableListOf(relax),
            workingDays = 85,
            hoursMonday = 523264,
            hoursTuesday = 523264,
            hoursWednesday = 523264,
            hoursThursday = 523264,
            hoursFriday = 523264,
            hoursSaturday = 523264,
            hoursSunday = 523264
    )

    val martin = TherapistEntity(
            id = 2,
            name = "Martin Mueller",
            description = "Kenia's husband",
            number = "555",
            mobile_table = false,
            massageTypes = mutableListOf(relax),
            workingDays = 42,
            hoursMonday = 523264,
            hoursTuesday = 523264,
            hoursWednesday = 523264,
            hoursThursday = 523264,
            hoursFriday = 523264,
            hoursSaturday = 523264,
            hoursSunday = 523264
    )

    @Before
    fun setup() {
        therapistService = JpaTherapistService(therapistRepository, massageTypeRepository)
    }

    @Test
    fun retrieveTherapist() {
        given(therapistRepository.findById(kenia.id)).willReturn(Optional.of(kenia))
        val retrieveTherapist = therapistService.retrieveTherapist(kenia.id)
        assertThat(retrieveTherapist, `is`(kenia))

        Assertions.assertThrows(TherapistNotFoundException::class.java) {
            therapistService.retrieveTherapist(martin.id)
        }
    }

    @Test
    fun retrieveTherapists() {
        val therapists = listOf(kenia, martin)
        given(therapistRepository.findAll()).willReturn(therapists)
        val retrieveTherapists = therapistService.retrieveTherapists()
        assertThat(retrieveTherapists[0], `is`(kenia))
        assertThat(retrieveTherapists[1], `is`(martin))
    }

    @Test
    fun addTherapist() {
        given(therapistRepository.save(kenia)).willReturn(kenia)
        val addTherapist = therapistService.addTherapist(kenia)
        assertThat(addTherapist, `is`(kenia))
    }

    @Test
    fun updateTherapist() {
        val updatedKenia = kenia.copy(name = "Alice", number = "666")
        given(therapistRepository.findById(kenia.id)).willReturn(Optional.of(kenia))
        given(therapistRepository.save(updatedKenia)).willReturn(updatedKenia)
        val updateTherapist = therapistService.updateTherapist(kenia.id, updatedKenia)
        assertThat(updateTherapist, `is`(updateTherapist))

        Assertions.assertThrows(TherapistNotFoundException::class.java) {
            therapistService.updateTherapist(martin.id, updatedKenia)
        }
    }

    @Test
    fun addTherapistMassage() {
        given(therapistRepository.findById(kenia.id)).willReturn(Optional.of(kenia))
        given(massageTypeRepository.findById(relax.id)).willReturn(Optional.of(relax))
        given(massageTypeRepository.findById(swedish.id)).willReturn(Optional.of(swedish))

        Assertions.assertThrows(TherapistMassageKeyExistException::class.java) {
            therapistService.addTherapistMassage(kenia.id, relax)
        }

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            therapistService.addTherapistMassage(kenia.id, tai)
        }

        Assertions.assertThrows(TherapistNotFoundException::class.java) {
            therapistService.addTherapistMassage(martin.id, relax)
        }

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            therapistService.addTherapistMassage(martin.id, tai)
        }

        val updatedKenia = kenia.copy(massageTypes = mutableListOf(relax, swedish))
        given(therapistRepository.save(updatedKenia)).willReturn(updatedKenia)
        val addTherapistMassage = therapistService.addTherapistMassage(kenia.id, swedish)

        assertThat(addTherapistMassage, `is`(updatedKenia))
    }

    @Test
    fun deleteTherapistMassage() {
        given(therapistRepository.findById(kenia.id)).willReturn(Optional.of(kenia))
        given(therapistRepository.save(kenia)).willReturn(kenia)
        given(massageTypeRepository.findById(relax.id)).willReturn(Optional.of(relax))
        given(massageTypeRepository.findById(swedish.id)).willReturn(Optional.of(swedish))

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            therapistService.deleteTherapistMassage(kenia.id, tai)
        }

        Assertions.assertThrows(TherapistMassageTypeNotFoundException::class.java) {
            therapistService.deleteTherapistMassage(kenia.id, swedish)
        }

        Assertions.assertThrows(TherapistNotFoundException::class.java) {
            therapistService.addTherapistMassage(martin.id, relax)
        }

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            therapistService.addTherapistMassage(martin.id, tai)
        }

        therapistService.deleteTherapistMassage(kenia.id, relax)

    }

    @Test
    fun deleteTherapist() {
        given(therapistRepository.findById(kenia.id)).willReturn(Optional.of(kenia))
        doNothing().`when`(therapistRepository).delete(kenia)
        therapistService.deleteTherapist(kenia.id)

        Assertions.assertThrows(TherapistNotFoundException::class.java) {
            therapistService.deleteTherapist(martin.id)
        }
    }

}