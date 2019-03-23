package com.massagerelax.therapist.web.support

import com.massagerelax.therapist.web.support.ErrorResponseEntity.Companion.badReqeust
import com.massagerelax.therapist.web.support.ErrorResponseEntity.Companion.notFound
import com.massagerelax.therapist.domain.DataNotFoundException
import com.massagerelax.therapist.domain.InvalidArgumentException
import com.massagerelax.therapist.domain.KeyExistException
import com.massagerelax.therapist.web.controller.client.NotFoundException
import com.massagerelax.therapist.web.support.ErrorResponseEntity.Companion.conflictReqeust
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.ConnectException
import java.util.*

@ControllerAdvice
class ExceptionHandlers @Autowired constructor(var messageSource: MessageSource) {

    @ExceptionHandler(KeyExistException::class)
    fun keyExistException(exception: KeyExistException, locale: Locale) =
            conflictReqeust(messageSource.getMessage(exception, locale))

//    @ExceptionHandler(DataNotFoundException::class)
//    fun resourceNotFoundException(exception: DataNotFoundException, locale: Locale) =
//            notFound(messageSource.getMessage(exception, locale))
//
//    @ExceptionHandler(ConnectException::class)
//    fun serviceNotFoundException(exception: ConnectException) =
//            notFound(exception.localizedMessage)
//
//    @ExceptionHandler(NotFoundException::class)
//    fun serviceUrlNotFoundException(exception: NotFoundException, locale: Locale) =
//            notFound(messageSource.getMessage(exception, locale))

    @ExceptionHandler(InvalidArgumentException::class)
    fun invalidArgumentException(exception: InvalidArgumentException, locale: Locale) =
            badReqeust(messageSource.getMessage(exception, locale))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(exception: MethodArgumentNotValidException, locale: Locale) =
            badReqeust("Argument validation failed", mapBindingResult(exception.bindingResult, locale));

    @ExceptionHandler(BindException::class)
    fun bindException(exception: BindException, locale: Locale) =
            badReqeust("Fatal Binding Exception", mapBindingResult(exception.bindingResult, locale));

    fun mapBindingResult(bindingResult: BindingResult, locale: Locale) =
            bindingResult.allErrors.map { messageSource.getMessage(it, locale) }

}