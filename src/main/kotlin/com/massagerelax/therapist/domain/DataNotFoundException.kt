package com.massagerelax.therapist.domain

import org.springframework.context.MessageSourceResolvable

abstract class DataNotFoundException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message) { }
    constructor(message: String, cause: Throwable) : super(message, cause) { }

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message

}

class TherapistNotFoundException(private val therapistId: Long) : DataNotFoundException("Therapist with id $therapistId don't exist") {

    override fun getCodes(): Array<out String> = arrayOf("error.therapistNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(therapistId)

}

class MassageTypeNotFoundException(private val massageTypeId: Long) : DataNotFoundException("Massage type with id $massageTypeId don't exist") {

    override fun getCodes(): Array<out String> = arrayOf("error.massageTypeNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(massageTypeId)

}

class TherapistMassageTypeNotFoundException(private val therapistId: Long, private val massageTypeId: Long) : DataNotFoundException("Therapist with id $therapistId doesn't offer massage type $massageTypeId") {

    override fun getCodes(): Array<out String> = arrayOf("error.massageTypeNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(massageTypeId)

}