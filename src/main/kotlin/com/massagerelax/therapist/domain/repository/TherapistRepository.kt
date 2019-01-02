package com.massagerelax.therapist.domain.repository

import com.massagerelax.therapist.domain.entity.TherapistEntity
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

@Transactional(Transactional.TxType.MANDATORY)
interface TherapistRepository : JpaRepository<TherapistEntity, String>