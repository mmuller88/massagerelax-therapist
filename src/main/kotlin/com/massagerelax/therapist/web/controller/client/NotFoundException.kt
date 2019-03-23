package com.massagerelax.therapist.web.controller.client

import org.springframework.context.MessageSourceResolvable

abstract class NotFoundException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message
}

class AreaServiceUrlNotFoundException(reason: String) : NotFoundException(reason) {

    override fun getCodes(): Array<out String> = arrayOf("error.areaServiceNotFound")
}
