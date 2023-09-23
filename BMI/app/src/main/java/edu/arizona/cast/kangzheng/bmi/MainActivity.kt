package edu.arizona.cast.kangzheng.bmi

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import edu.arizona.cast.kangzheng.bmi.databinding.ActivityMainBinding
import kotlin.math.pow

private const val TAG = "BMIViewModel"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bmiViewModel: BMIViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        if (savedInstanceState == null){
            Log.d(TAG, "savedInstanceState == null")
        }
        else{
            Log.d(TAG, "savedInstanceState = "+savedInstanceState.toString())
        }
        Log.d(TAG, "Inside onCreate, we have a bmiViewModel = $bmiViewModel")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Calculate.setOnClickListener{
            calculateBMI()
        }
        binding.Clear.setOnClickListener{
            clearOut()
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
        Log.d(TAG, "Activity is finishing? = ${this.isFinishing}")
    }

    private fun calculateBMI(){
        val weight = binding.addWeight.text.toString().toFloat()
        val heightFT = binding.addFeet.text.toString().toFloat()
        val heightIN = binding.addInches.text.toString().toFloat()
        val heightSUM = ((heightFT*12)+heightIN)

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
            binding.txtMSG.text="BMI: $bmiScore\n  $bmiResult"
            binding.txtMSG.setTextColor(ContextCompat.getColor(this,color))

        }else{
            binding.txtMSG.text = "Please fill up all the blanks!"
        }

    }

    private  fun clearOut(){

        binding.addFeet.text.clear()
        binding.addInches.text.clear()
        binding.addWeight.text.clear()
    }
}




