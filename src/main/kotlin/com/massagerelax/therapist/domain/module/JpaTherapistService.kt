package com.massagerelax.therapist.domain.module

import com.massagerelax.therapist.domain.MassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistMassageKeyExistException
import com.massagerelax.therapist.domain.TherapistMassageTypeNotFoundException
import com.massagerelax.therapist.domain.TherapistNotFoundException
import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
import com.massagerelax.therapist.domain.repository.TherapistRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JpaTherapistService(val therapistRepository: TherapistRepository, val massageTypeRepo: MassageTypeRepository): TherapistService {

    override fun retrieveTherapist(therapistId: Long): TherapistEntity {
        return therapistRepository.findById(therapistId).orElseThrow{ TherapistNotFoundException(therapistId) }
    }

    override fun retrieveTherapists(): List<TherapistEntity> {
        return therapistRepository.findAll()
    }

    override fun addTherapist(therapist: TherapistEntity): TherapistEntity {
        return therapistRepository.save(therapist)
    }

    override fun updateTherapist(id: Long, updateTherapist: TherapistEntity): TherapistEntity {
        return therapistRepository.findById(id).map { existingTherapist ->
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(
                            name = updateTherapist.name,
                            description = updateTherapist.description,
                            number = updateTherapist.number,
                            mobile_table = updateTherapist.mobile_table,
                            workingDays = updateTherapist.workingDays,
                            hoursMonday = updateTherapist.hoursMonday,
                            hoursTuesday = updateTherapist.hoursTuesday,
                            hoursWednesday = updateTherapist.hoursWednesday,
                            hoursThursday = updateTherapist.hoursThursday,
                            hoursFriday = updateTherapist.hoursFriday,
                            hoursSaturday = updateTherapist.hoursSaturday,
                            hoursSunday = updateTherapist.hoursSunday
                            )
            therapistRepository.save(updatedTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun addTherapistMassage(id: Long, massageType: MassageTypeEntity): TherapistEntity {
        // check if massagetype id exists
        massageTypeRepo.findById(massageType.id).orElseThrow{MassageTypeNotFoundException(massageType.id) }

        return therapistRepository.findById(id).map { existingTherapist ->
            // Check if therapist already has the massage type
            if(existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageKeyExistException(existingTherapist.name, massageType.name)
            }
            existingTherapist.massageTypes.addAll(listOf(massageType))
            // update therapist
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(massageTypes = existingTherapist.massageTypes)
            therapistRepository.save(updatedTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun deleteTherapistMassage(id: Long, massageType: MassageTypeEntity) {
        // check if massagetype id exists
        massageTypeRepo.findById(massageType.id).orElseThrow{MassageTypeNotFoundException(massageType.id) }

        therapistRepository.findById(id).map { existingTherapist ->
            // Check if therapist has the massage type
            if(!existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageTypeNotFoundException(existingTherapist.id, massageType.id)
            }
            existingTherapist.massageTypes.remove(massageType)
            therapistRepository.save(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

    override fun deleteTherapist(id: Long) {
        therapistRepository.findById(id).map { existingTherapist ->
            therapistRepository.delete(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(id) }
    }

}