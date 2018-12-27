package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.web.dto.*
import com.massagerelax.therapist.domain.entity.MassageTypeEntity

interface MassageTypeService {
    fun retrieveMassageType(id: Long): MassageTypeEntity

    fun retrieveMassageTypes(): List<MassageTypeEntity>

    fun addMassageType(therapist: MassageTypeEntity): MassageTypeEntity

    fun updateMassageType(id: Long, therapist: MassageTypeEntity): MassageTypeEntity

    fun deleteMassageType(id: Long)
}