package com.massagerelax.therapist

import com.massagerelax.therapist.domain.entity.MassageTypeEntity
import com.massagerelax.therapist.domain.entity.TherapistEntity
import com.massagerelax.therapist.domain.repository.MassageTypeRepository
import com.massagerelax.therapist.domain.repository.TherapistRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.awt.print.Book



@SpringBootApplication
class TherapistApplication : CommandLineRunner {

	private val logger = LoggerFactory.getLogger(TherapistApplication::class.java)

	@Autowired
	val massageTypeRepo: MassageTypeRepository? = null

	@Autowired
	val therapistRepo: TherapistRepository? = null

	override fun run(vararg args: String?) {
		// Cleanup the tables
		massageTypeRepo?.deleteAllInBatch()
		therapistRepo?.deleteAllInBatch()


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
				hoursMonday = 130048,
				hoursTuesday = 130048,
				hoursWednesday = 130048,
				hoursThursday = 130048,
				hoursFriday = 130048,
				hoursSaturday = 130048,
				hoursSunday = 130048
				)

		val martin = TherapistEntity(
				name = "Martin Mueller",
				description = "Kenia's husband",
				number = "555",
				mobile_table = false,
				massageTypes = mutableListOf(relax),
				workingDays = 42,
				hoursMonday = 520192,
				hoursTuesday = 520192,
				hoursWednesday = 520192,
				hoursThursday = 520192,
				hoursFriday = 520192,
				hoursSaturday = 520192,
				hoursSunday = 520192
		)

		massageTypeRepo?.save(relax)
		massageTypeRepo?.save(swedish)
		massageTypeRepo?.save(tai)

		therapistRepo?.save(kenia)
		therapistRepo?.save(martin)

		// fetch all books
//		for(therapist in therapistRepo.findAll()) {
//			logger.info(therapist.toString())
//		}
	}
}

fun main(args: Array<String>) {
	runApplication<TherapistApplication>(*args)
}

