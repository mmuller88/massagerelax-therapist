package com.massagerelax.therapist.web.dto


class Hours(val h0: Boolean=false,val h1: Boolean=false,val h2: Boolean=false,val h3: Boolean=false,val h4: Boolean=false,val h5: Boolean=false,val h6: Boolean=false,val h7: Boolean=false,val h8: Boolean=false,val h9: Boolean=false,val h10: Boolean=false,val h11: Boolean=false,val h12: Boolean=false,val h13: Boolean=false,val h14: Boolean=false,val h15: Boolean=false,val h16: Boolean=false,val h17: Boolean=false,val h18: Boolean=false,val h19: Boolean=false,val h20: Boolean=false,val h21: Boolean=false,val h22: Boolean=false,val h23: Boolean=false) {

    companion object {
        fun fromInt(hours: Int) = Hours(
                (hours and (1 shl 0)) != 0,
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

data class TherapistDTO(
        val userName: String,
        val description: String?,
        val number: String,
        val mobileTable: Boolean=false)