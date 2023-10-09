package edu.arizona.cast.kangzheng.bmi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.pow


private const val TAG = "BMIViewModel"
class BMIViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){

    init{
        Log.d(TAG, "ViewModel instance created")
    }
    var weight= MutableLiveData<Double>()
        // = 5//binding.addWeight.text.toString().toFloat()
    var heightFT = MutableLiveData<Double>()//binding.addFeet.text.toStg().toFloat()

    var heightIN = MutableLiveData<Double>()//binding.addInches.text.toString().toFloat()
    var textMsg = MutableLiveData<String>()



    var bmi = MutableLiveData<Double>()//weight*703/heightSUM*heightSUM//use function updateindex
    fun calculateBMI() {
        if (weight.value != null && heightFT.value != null && heightIN.value != null) {

            bmi.value = weight.value!! * 703 / (heightFT.value!! * 12 + heightIN.value!!).pow(2)
        }


    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance to be destroyed")
    }

    }



