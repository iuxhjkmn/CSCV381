package edu.arizona.cast.lxu.glucose.database

import androidx.room.TypeConverter
import java.util.*

class GlucoseTypeConverters{

    @TypeConverter
    fun fromDate(date: Date?):Long? {
        return date?.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let{
            Date(it)
        }
    }


    @TypeConverter
    fun toIsNormal(isNormal: String?):Boolean?{
        return isNormal?.toBoolean()
    }
    @TypeConverter
    fun fromIsNormal(isNormal: Boolean?):String? {
        return isNormal?.toString()
    }


}