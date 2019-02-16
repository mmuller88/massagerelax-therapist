package com.massagerelax.therapist.web.controller.proxy

import com.massagerelax.therapist.domain.DataNotFoundException
import org.springframework.context.MessageSourceResolvable

abstract class NotFoundException : RuntimeException, MessageSourceResolvable {

    constructor(message: String) : super(message) { }
    constructor(message: String, cause: Throwable) : super(message, cause) { }

    override fun getArguments(): Array<out Any>? = arrayOf()
    override fun getDefaultMessage(): String?  = message
}

class AreaServiceNotFoundException() : NotFoundException("Area-Service not found") {

    override fun getCodes(): Array<out String> = arrayOf("error.areaServiceNotFound")
}
