package com.massagerelax.therapist.web.support

import com.massagerelax.therapist.web.controller.proxy.AreaServiceNotFoundException
import feign.Response
import feign.codec.ErrorDecoder


class MyErrorDecoder : ErrorDecoder {

    private val defaultErrorDecoder = ErrorDecoder.Default()

    override fun decode(methodKey: String, response: Response): Exception {
        return if (response.status() == 404) {
            AreaServiceNotFoundException()
        } else defaultErrorDecoder.decode(methodKey, response)
    }

}