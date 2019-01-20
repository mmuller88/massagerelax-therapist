package com.massagerelax.therapist

import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
import com.massagerelax.therapist.domain.repository.TherapistRepository
import org.hibernate.Hibernate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.awt.print.Book



@SpringBootApplication(exclude = [
	SecurityAutoConfiguration::class,
	UserDetailsServiceAutoConfiguration::class])
class TherapistApplication {

	private val logger = LoggerFactory.getLogger(TherapistApplication::class.java)

	@Bean
	fun databaseInitializer(massageTypeRepo: MassageTypeRepository,
							therapistRepo: TherapistRepository) = CommandLineRunner {
		massageTypeRepo.deleteAllInBatch()
		therapistRepo.deleteAllInBatch()


		val relax = MassageTypeEntity(
				name = "Relax massage",
				description = "For relaxing")

		val swedish = MassageTypeEntity(
				name = "Swedish massage",
				description = "For swedish relaxing")

		val tai = MassageTypeEntity(
				name = "Tai massage",
				description = "For maximum relaxing")

		val kenia = TherapistEntity(
				name = "Kenia Alves",
				description = "Martin's wife",
				number = "1233344",
				mobile_table = true,
				massageTypes = mutableListOf(relax, swedish),
				workingDays = 85,
				hoursMonday = 523264,
				hoursTuesday = 523264,
				hoursWednesday = 523264,
				hoursThursday = 523264,
				hoursFriday = 523264,
				hoursSaturday = 523264,
				hoursSunday = 523264
		)

		val martin = TherapistEntity(
				name = "Martin Mueller",
				description = "Kenia's husband",
				number = "555",
				mobile_table = false,
				massageTypes = mutableListOf(relax),
				workingDays = 42,
				hoursMonday = 523264,
				hoursTuesday = 523264,
				hoursWednesday = 523264,
				hoursThursday = 523264,
				hoursFriday = 523264,
				hoursSaturday = 523264,
				hoursSunday = 523264
		)

		massageTypeRepo.save(relax)
		massageTypeRepo.save(swedish)
		massageTypeRepo.save(tai)

		val keniaR = therapistRepo.save(kenia)
		therapistRepo.save(martin)
		logger.info("Kenia " + keniaR.id.toString())

		val therapists: List<TherapistEntity> = therapistRepo.findAll()

//		therapists.forEach{it ->
//			Hibernate.initialize(it)
//			print(it)
//		}

	}
}

fun main(args: Array<String>) {
	runApplication<TherapistApplication>(*args)
}

