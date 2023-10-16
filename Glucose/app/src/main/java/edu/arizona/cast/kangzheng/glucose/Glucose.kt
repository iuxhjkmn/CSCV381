package edu.arizona.cast.kangzheng.glucose

import androidx.lifecycle.MutableLiveData
import java.util.Date
import java.util.UUID

data class Glucose(
    val id:UUID = UUID.randomUUID(),
    var fasting: Int =0,
    var result: String = "",
    var date: Date = Date(),
    var breakfast: Int=0,
    var lunch: Int=0 ,
    var dinner: Int =0,
    var isChecked: Boolean = false)

