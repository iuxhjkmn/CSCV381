package edu.arizona.cast.lxu.glucose

import android.app.Application
import android.util.Log
import java.util.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG ="GlucoseViewModel"

class GlucoseViewModel(gDate: Date): ViewModel(){

    private val glucoseRepository = GlucoseRepository.get()

    private val _glucose: MutableStateFlow<Glucose?> = MutableStateFlow(null)
    val glucose: StateFlow<Glucose?> = _glucose.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d(TAG, "ViewModel: The date passed is ${gDate.toString()}")
            _glucose.value = glucoseRepository.getGlucose(gDate)
            Log.d(TAG, "ViewModel: The retrieved glucose is ${_glucose.value.toString()}")
        }
    }
    fun updateGlucose(onUpdate: (Glucose) ->Glucose){
        _glucose.update{ oldGlucose ->
            oldGlucose?.let{onUpdate(it)}

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared ${glucose.value.toString()}")
        glucose.value?.let{glucoseRepository.updateGlucose(it)}
    }

    var beingDeleted = false

    var isNormal:Boolean = false
    var isNormals: Array<Boolean> = arrayOf<Boolean>(false, false, false, false)
    var msg: String = ""
    var avg:Int = -1
    var messages: Array<String> = arrayOf<String>("","","","")
    fun reset(){
        _glucose.value = Glucose()
        resetG()

    }
    fun resetG(){
        beingDeleted = false
        updateFasting(-1)
        updateBreakfast(-1)
        updateDinner(-1)
        updateLunch(-1)
        msg = ""
        avg = -1
        messages = arrayOf<String>("","","","")
        isNormals = arrayOf<Boolean>(false, false, false, false)
        isNormal = false


    }


    fun getFasting() = _glucose.value?.fasting

    fun getBreakFast() = _glucose.value?.breakfast
    fun getLunch() = _glucose.value?.lunch
    fun getDinner() = _glucose.value?.dinner



    fun updateFasting(level:Int){
        _glucose.value?.fasting = level
    }
    fun updateBreakfast(level:Int){
        _glucose.value?.breakfast= level
    }
    fun updateLunch(level:Int){
        _glucose.value?.lunch= level
    }
    fun updateDinner(level:Int){
        _glucose.value?.dinner= level
    }



    fun processGlucose(){
        var g = glucose.value!!
        isNormal = false
        calculateAvg()

        if (g.fasting != -1) { calculateInfoMeg(g.fasting, 0) }
        if (g.breakfast != 1) { calculateInfoMeg(g.breakfast, 1) }
        if (g.lunch!= -1) { calculateInfoMeg(g.lunch, 2) }
        if (g.dinner != -1) { calculateInfoMeg(g.dinner, 3) }


        msg = extractGlucose()
        Log.d(TAG, extractGlucose())
        Log.d(TAG, g.toString())


    }
    fun extractGlucose():String{
        var g = glucose.value!!
        val buf:StringBuffer = StringBuffer("")
        return buf.append(g.date.toString())
                .append("\nFasting:\t").append(if (g.fasting != -1) messages[0] else "None")
                .append("\nBreakfast:\t").append(if (g.breakfast !=-1) messages[1] else "None")
                .append("\nLunch:\t").append( if (g.lunch != -1) messages[2] else "None")
                .append("\nDinner:\t").append(if (g.dinner != -1) messages[3] else "None")
                .append("\nIsNormal:\t").append(isNormal.toString())
                .toString()

    }

    private fun calculateInfoMeg(m:Int, i:Int){
        val msg:StringBuffer = StringBuffer("")
        if (m < 70){
            msg.append("Hypoglycemic")
            isNormals[i] = false

        }
        else{
            if (i==0){
                if (m>=70 && m<=90){
                    msg.append("Normal")
                    isNormals[i] = true
                }
                else{
                    msg.append("Abnormal")
                    isNormals[i] = false
                }
            }
            else{
                if (m>140){
                    msg.append("Abnormal")
                    isNormals[i] = false

                }
                else{
                    msg.append("Normal")
                    isNormals[i] = true
                }
            }

        }
        isNormal = isNormals[0] && isNormals[1] && isNormals[2] && isNormals[3]
        messages[i] = msg.toString()


    }
    private fun isNormalMeasure(m:Int, i:Int):Boolean {
        if (m < 70) return false
        if (i==0){
            if (m>=70 && m<=90) return true
            return false
        }
        else{
            if (m>140)return false
            return true
        }
    }
    private fun calculateAvg(){
        var n:Int = 0
        var sum:Int = 0
        var g = glucose.value!!
        if (g.fasting != -1){
            sum += g.fasting
            n++
        }
        if (g.breakfast != -1){
            sum += g.breakfast
            n++
        }
        if (g.lunch != -1){
            sum += g.lunch
            n++
        }
        if (g.dinner != -1){
            sum += g.dinner
            n++
        }
        if (n != 0)
            avg = sum/n
        else
            avg = 0

    }
    fun save(){
        Log.d(TAG, "save function--->"+ _glucose.value?.toString())
    }

}
class GlucoseViewModelFactory(
    private val gDate: Date
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GlucoseViewModel(gDate) as T
    }
}