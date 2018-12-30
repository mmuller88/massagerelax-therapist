package com.massagerelax.therapist.web.dto

import com.massagerelax.therapist.Helper.toBoolean
import java.util.*

class Hour(val hour: Int, val working: Boolean) {
    companion object {
        fun hourListFromInt(hours: Int): List<Hour> {
            val hourList: MutableList<Hour> = LinkedList()
            for(p in 0..23) {
                hourList.add(Hour(p, ((1 shl p) and hours).toBoolean()))
            }
            return hourList
        }
    }
}

data class UpdateWorkingDayDTO(val working: Boolean, val hours: List<Hour>)