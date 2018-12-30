package com.massagerelax.therapist.domain

import org.springframework.context.MessageSourceResolvable

abstract class InvalidArgumentException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message) { }
    constructor(message: String, cause: Throwable) : super(message, cause) { }

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message
}

class InvalidStringParameterException(private val parameter: String) : InvalidArgumentException("The string url parameter $parameter is not valid") {

    override fun getCodes(): Array<out String> = arrayOf("error.invalidStringParameter")
    override fun getArguments(): Array<out Any> = arrayOf(parameter)
}

class InvalidPropertyValueException(private val properyValue: Int) : InvalidArgumentException("The body property value $properyValue is not valid") {

    override fun getCodes(): Array<out String> = arrayOf("error.invalidPropertyValue")
    override fun getArguments(): Array<out Any> = arrayOf(properyValue)
}