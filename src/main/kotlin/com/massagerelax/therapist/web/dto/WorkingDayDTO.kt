package com.massagerelax.therapist.web.dto

data class WorkingDayDTO(val weekDay: String, val working: Boolean, val hours: List<Hour>)