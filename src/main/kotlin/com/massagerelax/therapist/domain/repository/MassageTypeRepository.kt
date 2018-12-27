package com.massagerelax.therapist.domain.repository

import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

@Transactional(Transactional.TxType.MANDATORY)
interface MassageTypeRepository : JpaRepository<MassageTypeEntity, Long>