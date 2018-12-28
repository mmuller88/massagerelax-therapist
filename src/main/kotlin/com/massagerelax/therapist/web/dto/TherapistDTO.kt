package com.massagerelax.therapist.web.dto


class WeekDay{
    var working: Boolean = false
    var hours: Hours? = null

    constructor()

    constructor(working: Boolean, hours: Int) {
        this.working = working
        this.hours = Hours((hours and (1 shl 0)) != 0,
        (hours and (1 shl 1)) != 0,
        (hours and (1 shl 2)) != 0,
        (hours and (1 shl 3)) != 0,
        (hours and (1 shl 4)) != 0,
        (hours and (1 shl 5)) != 0,
        (hours and (1 shl 6)) != 0,
        (hours and (1 shl 7)) != 0,
        (hours and (1 shl 8)) != 0,
        (hours and (1 shl 9)) != 0,
        (hours and (1 shl 10)) != 0,
        (hours and (1 shl 11)) != 0,
        (hours and (1 shl 12)) != 0,
        (hours and (1 shl 12)) != 0,
        (hours and (1 shl 14)) != 0,
        (hours and (1 shl 15)) != 0,
        (hours and (1 shl 16)) != 0,
        (hours and (1 shl 17)) != 0,
        (hours and (1 shl 18)) != 0,
        (hours and (1 shl 19)) != 0,
        (hours and (1 shl 20)) != 0,
        (hours and (1 shl 21)) != 0,
        (hours and (1 shl 22)) != 0,
        (hours and (1 shl 23)) != 0)
    }
}

class Week(val monday: WeekDay,
           val tuesday: WeekDay,
           val wednesday: WeekDay,
           val thursday: WeekDay,
           val friday: WeekDay,
           val saturday: WeekDay,
           val sunday: WeekDay) {
    fun toInt(): Int {
        return (if (monday.working) 1 else 0 + if (tuesday.working) 2 else 0 + if (wednesday.working) 4 else 0 + if (thursday.working) 8 else 0 + if (friday.working) 16 else 0 + if (saturday.working) 32 else 0 + if (sunday.working) 24 else 0)
    }
}

class Hours(var h0: Boolean = false,var h1: Boolean=false,var h2: Boolean=false,var h3: Boolean=false,var h4: Boolean=false,var h5: Boolean=false,var h6: Boolean=false,var h7: Boolean=false,var h8: Boolean=false,var h9: Boolean=false,var h10: Boolean=false,var h11: Boolean=false,var h12: Boolean=false,var h13: Boolean=false,var h14: Boolean=false,var h15: Boolean=false,var h16: Boolean=false,var h17: Boolean=false,var h18: Boolean=false,var h19: Boolean=false,var h20: Boolean=false,var h21: Boolean=false,var h22: Boolean=false,var h23: Boolean=false)

data class TherapistDTO(
        val id: Long,
        val name: String,
        val description: String?,
        val number: String,
        val mobileTable: Boolean=false,
        val workingDays: Week)