package edu.arizona.cast.kangzheng.bmi

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.pow


private const val TAG = "BMIViewModel"
class BMIViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){

    init{
        Log.d(TAG, "ViewModel instance created")
    }
    var weight = 5//binding.addWeight.text.toString().toFloat()
    var heightFT = 12//binding.addFeet.text.toString().toFloat()
    var heightIN = 120//binding.addInches.text.toString().toFloat()
    var heightSUM = ((heightFT*12)+heightIN)
    private fun calculateBMI(){


        if (weight != null && heightFT != null && heightIN != null){
            val bmi = weight*703/heightSUM.pow(2)
            val bmiScore = String.format("%.2f", bmi)


            var bmiResult = ""
            var color = 0
            when {
                bmi <18.5 -> {
                    bmiResult="Underweight"
                    color = R.color.underweight
                }
                bmi <24.9 -> {
                    bmiResult="Normal"
                    color = R.color.normal
                }
                bmi <30   -> {
                    bmiResult="Overweight"
                    color = R.color.overweight
                }
                else -> {
                    bmiResult="Obese"
                    color = R.color.obese
                }
            }
            //binding.txtMSG.text="BMI: $bmiScore\n  $bmiResult"
            //binding.txtMSG.setTextColor(ContextCompat.getColor(this,color))

        }else{
           //something =  binding.txtMSG.text = "Please fill up all the blanks!"
        }

    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance to be destroyed")
    }

}