package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.web.dto.UpdateTherapistDTO
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import java.util.*

interface TherapistService {
    fun retrieveTherapist(id: Long): TherapistEntity

    fun retrieveTherapists(): List<TherapistEntity>

    fun addTherapist(therapist: TherapistEntity): TherapistEntity

    fun updateTherapist(id: Long, therapist: TherapistEntity): TherapistEntity

    fun deleteTherapist(id: Long)

    fun addTherapistMassage(id: Long, massageType: MassageTypeEntity): TherapistEntity
    fun deleteTherapistMassage(id: Long, massageType: MassageTypeEntity)
}