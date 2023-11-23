package edu.arizona.cast.lxu.glucose

import android.content.Context
import android.util.Log
import androidx.room.Room
import edu.arizona.cast.lxu.glucose.database.GlucoseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "glucose-database.db"
private const val TAG ="GlucoseRepository"

@OptIn(DelicateCoroutinesApi::class)
class GlucoseRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope)
{
    private val database: GlucoseDatabase = Room.databaseBuilder(
            context.applicationContext,
            GlucoseDatabase::class.java,
            DATABASE_NAME
    ).createFromAsset(DATABASE_NAME)
        .build()

    suspend fun getGlucoses(): Flow<List<Glucose>> = database.glucoseDao().getGlucoses()


    suspend fun getGlucose(date: Date): Glucose = database.glucoseDao().getGlucose(date)

    fun updateGlucose(g: Glucose){
        coroutineScope.launch {
            database.glucoseDao().updateGlucose(g)
            Log.d(TAG, "updateGlucose = " +g.toString() + " updated " +g.toString())
        }



    }

    companion object {
        private var INSTANCE: GlucoseRepository? = null
        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = GlucoseRepository(context)
            }
        }
        fun get(): GlucoseRepository{
            return INSTANCE ?: throw IllegalStateException("GlucoseRepository must be initialzied!")
        }
    }



}