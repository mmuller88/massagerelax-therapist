package com.massagerelax.therapist.web.dto

data class CreateTherapistDTO(val name: String, val description: String?, val number: String, val mobileTable: Boolean, val workingDays: Week)