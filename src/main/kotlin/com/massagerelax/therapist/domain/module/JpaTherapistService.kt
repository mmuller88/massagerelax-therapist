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

    override fun retrieveTherapist(userName: String): TherapistEntity {
        return therapistRepository.findById(userName).orElseThrow{ TherapistNotFoundException(userName) }
    }

    override fun retrieveTherapists(): List<TherapistEntity> {
        return therapistRepository.findAll()
    }

    override fun addTherapist(therapist: TherapistEntity): TherapistEntity {
        return therapistRepository.save(therapist)
    }

    override fun updateTherapist(userName: String, updateTherapist: TherapistEntity): TherapistEntity {
        return therapistRepository.findById(userName).map { existingTherapist ->
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(
                            userName = updateTherapist.userName,
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
        }.orElseThrow{ TherapistNotFoundException(userName) }
    }

    override fun addTherapistMassage(userName: String, massageType: MassageTypeEntity): TherapistEntity {
        // check if massagetype id exists
        massageTypeRepo.findById(massageType.id).orElseThrow{MassageTypeNotFoundException(massageType.id) }

        return therapistRepository.findById(userName).map { existingTherapist ->
            // Check if therapist already has the massage type
            if(existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageKeyExistException(existingTherapist.userName, massageType.name)
            }
            // update therapist
            val updatedTherapist: TherapistEntity = existingTherapist
                    .copy(massageTypes = existingTherapist.massageTypes + listOf(massageType))
            therapistRepository.save(updatedTherapist)
        }.orElseThrow{ TherapistNotFoundException(userName) }
    }

    override fun deleteTherapistMassage(userName: String, massageType: MassageTypeEntity) {
        // check if massagetype id exists
        massageTypeRepo.findById(massageType.id).orElseThrow{MassageTypeNotFoundException(massageType.id) }

        return therapistRepository.findById(userName).map { existingTherapist ->
            // Check if therapist has the massage type
            if(!existingTherapist.massageTypes.contains(massageType)) {
                throw TherapistMassageTypeNotFoundException(existingTherapist.userName!!, massageType.id!!)
            }
            therapistRepository.delete(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(userName) }
    }

    override fun deleteTherapist(userName: String) {
        return therapistRepository.findById(userName).map { existingTherapist ->
            therapistRepository.delete(existingTherapist)
        }.orElseThrow{ TherapistNotFoundException(userName) }
    }

}