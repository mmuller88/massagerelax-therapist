package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.domain.TherapistMassageKeyExistException
import com.massagerelax.therapist.domain.TherapistMassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistNotFoundException
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.repository.TherapistRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JpaTherapistService(val repo: TherapistRepository): TherapistService {

    override fun retrieveTherapist(therapistId: Long): TherapistEntity {
        return repo.findById(therapistId).orElseThrow{ TherapistNotFoundException(therapistId) }
    }

    override fun retrieveTherapists(): List<TherapistEntity> {
        return repo.findAll()
    }

    override fun addTherapist(therapist: TherapistEntity): TherapistEntity {
        return repo.save(therapist)
    }

    override fun updateTherapist(id: Long, newTherapist: TherapistEntity): TherapistEntity {
        return repo.findById(id).map { existingTherapist ->
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(name = newTherapist.name, description = newTherapist.description, number = newTherapist.number, mobile_table = newTherapist.mobile_table)
            repo.save(updatedTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun addTherapistMassage(id: Long, massageType: MassageTypeEntity): TherapistEntity {
        return repo.findById(id).map { existingTherapist ->
            // Check if therapist already has the massage type
            if(existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageKeyExistException(existingTherapist.name, massageType.name)
            }
            // update therapist
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(massageTypes = existingTherapist.massageTypes + listOf(massageType))
            repo.save(updatedTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun deleteTherapistMassage(id: Long, massageType: MassageTypeEntity) {
        return repo.findById(id).map { existingTherapist ->
            // Check if therapist has the massage type
            if(!existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageTypeNotFoundException(existingTherapist.id!!, massageType.id!!)
            }
            repo.delete(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun deleteTherapist(id: Long) {
        return repo.findById(id).map { existingTherapist ->
            repo.delete(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

}