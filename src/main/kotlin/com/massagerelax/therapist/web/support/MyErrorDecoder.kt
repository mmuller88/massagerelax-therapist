package com.massagerelax.therapist.web.support

import com.massagerelax.therapist.web.controller.client.AreaServiceUrlNotFoundException
import feign.Response
import feign.codec.ErrorDecoder


class MyErrorDecoder : ErrorDecoder {

    private val defaultErrorDecoder = ErrorDecoder.Default()

    override fun decode(methodKey: String, response: Response): Exception {
        return if (response.status() == 404) {
            AreaServiceUrlNotFoundException(response.request().url() + " " + response.request().toString())
        }
        else defaultErrorDecoder.decode(methodKey, response)
    }

}