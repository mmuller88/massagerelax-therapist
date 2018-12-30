package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.domain.MassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistNotFoundException
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
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
internal class JpaMassageTypeServiceTest {

    @Mock
    lateinit var massageTypeRepository: MassageTypeRepository

    lateinit var massageTypeService: JpaMassageTypeService

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

    @Before
    fun setUp() {
        massageTypeService = JpaMassageTypeService(massageTypeRepository)
    }

    @Test
    fun retrieveMassageType() {
        given(massageTypeRepository.findById(relax.id)).willReturn(Optional.of(relax))
        val retrieveMassageType = massageTypeService.retrieveMassageType(relax.id)
        assertThat(retrieveMassageType, `is`(relax))

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            massageTypeService.retrieveMassageType(swedish.id)
        }
    }

    @Test
    fun retrieveMassageTypes() {

        val massageTypes = listOf(relax, swedish, tai)
        given(massageTypeRepository.findAll()).willReturn(massageTypes)
        val retrieveTherapists = massageTypeService.retrieveMassageTypes()
        assertThat(retrieveTherapists[0], `is`(relax))
        assertThat(retrieveTherapists[1], `is`(swedish))
        assertThat(retrieveTherapists[2], `is`(tai))
    }

    @Test
    fun addMassageType() {
        given(massageTypeRepository.save(relax)).willReturn(relax)
        val addMassageType = massageTypeService.addMassageType(relax)
        assertThat(addMassageType, `is`(relax))
    }

    @Test
    fun updateMassageType() {
        val updatedMassageType = relax.copy(name = "relax2")
        given(massageTypeRepository.findById(relax.id)).willReturn(Optional.of(relax))
        given(massageTypeRepository.save(updatedMassageType)).willReturn(updatedMassageType)
        val updateMassageType = massageTypeService.updateMassageType(relax.id, updatedMassageType)
        assertThat(updateMassageType, `is`(updatedMassageType))

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            massageTypeService.updateMassageType(swedish.id, updateMassageType)
        }
    }

    @Test
    fun deleteMassageType() {
        given(massageTypeRepository.findById(relax.id)).willReturn(Optional.of(relax))
        doNothing().`when`(massageTypeRepository).delete(relax)
        massageTypeService.deleteMassageType(relax.id)

        Assertions.assertThrows(MassageTypeNotFoundException::class.java) {
            massageTypeService.deleteMassageType(swedish.id)
        }
    }
}