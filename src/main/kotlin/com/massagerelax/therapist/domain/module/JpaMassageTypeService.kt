package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.domain.MassageTypeNotFoundException
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JpaMassageTypeService(val repo: MassageTypeRepository): MassageTypeService {
    override fun retrieveMassageType(id: Long): MassageTypeEntity {
        return repo.findById(id).orElseThrow{ MassageTypeNotFoundException(id) }
    }

    override fun retrieveMassageTypes(): List<MassageTypeEntity> {
        return repo.findAll()
    }

    override fun addMassageType(massageType: MassageTypeEntity): MassageTypeEntity {
        return repo.save(massageType)
    }

    override fun updateMassageType(id: Long, newMassageType: MassageTypeEntity): MassageTypeEntity {
        return repo.findById(id).map { existingMassageType ->
            val updatedMassageType: MassageTypeEntity = existingMassageType
                    .copy(name = newMassageType.name, description = newMassageType.description)
            repo.save(updatedMassageType)
        }.orElseThrow{ MassageTypeNotFoundException(id) }
    }

    override fun deleteMassageType(id: Long) {
        return repo.findById(id).map { existingTherapist ->
            repo.delete(existingTherapist)
        }.orElseThrow{ MassageTypeNotFoundException(id) }
    }

}