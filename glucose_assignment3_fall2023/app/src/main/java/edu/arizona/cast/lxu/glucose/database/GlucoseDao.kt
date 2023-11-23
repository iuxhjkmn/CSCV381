package edu.arizona.cast.lxu.glucose.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import edu.arizona.cast.lxu.glucose.Glucose
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface GlucoseDao{
    @Query("SELECT * FROM glucose")
    fun getGlucoses(): Flow<List<Glucose>>

    @Query("SELECT * FROM glucose WHERE date=(:date) ORDER BY date DESC")
    suspend fun getGlucose(date: Date): Glucose

    @Update
    suspend fun updateGlucose(glucose: Glucose)

}