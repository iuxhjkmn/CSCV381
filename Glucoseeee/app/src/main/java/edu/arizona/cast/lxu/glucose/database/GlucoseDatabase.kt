package edu.arizona.cast.lxu.glucose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import edu.arizona.cast.lxu.glucose.Glucose

@Database(entities =[Glucose::class],version = 1)
@TypeConverters(GlucoseTypeConverters::class)
abstract class GlucoseDatabase : RoomDatabase(){
    abstract fun glucoseDao(): GlucoseDao
}