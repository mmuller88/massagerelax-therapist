package com.massagerelax.therapist.domain

import org.springframework.context.MessageSourceResolvable

abstract class KeyExistException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message) { }
    constructor(message: String, cause: Throwable) : super(message, cause) { }

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message

}

class TherapistMassageKeyExistException(private val therapist: String, private val massageType: String) : KeyExistException("Therapist $therapist already offers $massageType") {

    override fun getCodes(): Array<out String> = arrayOf("error.therapistMassageKeyExist")
    override fun getArguments(): Array<out Any> = arrayOf(therapist, massageType)

}