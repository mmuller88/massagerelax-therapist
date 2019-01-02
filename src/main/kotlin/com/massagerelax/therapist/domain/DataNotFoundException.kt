package com.massagerelax.therapist.domain

import org.springframework.context.MessageSourceResolvable

abstract class DataNotFoundException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message) { }
    constructor(message: String, cause: Throwable) : super(message, cause) { }

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message
}

class TherapistNotFoundException(private val userName: String) : DataNotFoundException("Therapist with id $userName don't exist") {

    override fun getCodes(): Array<out String> = arrayOf("error.therapistNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(userName)
}

class MassageTypeNotFoundException(private val massageTypeId: Long) : DataNotFoundException("Massage type with id $massageTypeId don't exist") {

    override fun getCodes(): Array<out String> = arrayOf("error.massageTypeNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(massageTypeId)
}

class TherapistMassageTypeNotFoundException(private val userName: String, private val massageTypeId: Long) : DataNotFoundException("Therapist with user name $userName doesn't offer massage type $massageTypeId") {

    override fun getCodes(): Array<out String> = arrayOf("error.massageTypeNotFound")
    override fun getArguments(): Array<out Any> = arrayOf(userName, massageTypeId)
}
