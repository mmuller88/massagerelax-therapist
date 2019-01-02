package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.web.dto.UpdateTherapistDTO
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import java.util.*

interface TherapistService {
    fun retrieveTherapist(userName: String): TherapistEntity

    fun retrieveTherapists(): List<TherapistEntity>

    fun addTherapist(therapist: TherapistEntity): TherapistEntity

    fun updateTherapist(userName: String, therapist: TherapistEntity): TherapistEntity

    fun deleteTherapist(userName: String)

    fun addTherapistMassage(userName: String, massageType: MassageTypeEntity): TherapistEntity
    fun deleteTherapistMassage(userName: String, massageType: MassageTypeEntity)
}