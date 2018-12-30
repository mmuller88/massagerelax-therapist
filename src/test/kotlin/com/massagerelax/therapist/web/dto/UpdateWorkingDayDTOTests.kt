package com.massagerelax.therapist.web.dto

import org.junit.Test
import org.junit.jupiter.api.Assertions

class UpdateWorkingDayDTOTests {

    @Test
    fun testHourFromInt(){

        var hours: List<Hour> = Hour.hourListFromInt(0)

        Assertions.assertEquals(hours.size, 24)

        for(i in 0..23) {
            Assertions.assertEquals(i, hours[i].hour)
            Assertions.assertEquals(false, hours[i].working)
        }

        hours = Hour.hourListFromInt(1)

        Assertions.assertEquals(0, hours[0].hour)
        Assertions.assertEquals(true, hours[0].working)

        for(i in 1..23) {
            Assertions.assertEquals(i, hours[i].hour)
            Assertions.assertEquals(false, hours[i].working)
        }

        hours = Hour.hourListFromInt(7)

        for(i in 0..2) {
            Assertions.assertEquals(i, hours[i].hour)
            Assertions.assertEquals( true, hours[i].working, "$i isn't true")
        }

        for(i in 3..23) {
            Assertions.assertEquals(i, hours[i].hour)
            Assertions.assertEquals( false, hours[i].working, "$i isn't true")
        }
    }


}