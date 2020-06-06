package com.demo.document2pdf.Room

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    public fun toDate(timestamp: Long) : Date?{
        return if (timestamp==null)  null else Date()
    }

    public fun toTimestamp(date: Date) : Long?{
        return if(date==null) null else date.time
    }

}
