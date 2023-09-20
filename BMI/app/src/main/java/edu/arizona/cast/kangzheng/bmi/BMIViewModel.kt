package edu.arizona.cast.kangzheng.bmi

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import edu.arizona.cast.kangzheng.bmi.databinding.ActivityMainBinding


private const val TAG = "BMIViewModel"
class BMIViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    private lateinit var binding: ActivityMainBinding
    init{
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance to be destroyed")
    }

}