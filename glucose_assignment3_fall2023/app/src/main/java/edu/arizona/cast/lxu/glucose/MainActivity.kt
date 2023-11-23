package edu.arizona.cast.lxu.glucose

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import java.util.*


private const val TAG = "MainActivity"
private const val REMINDER_WORK = "REMINDER_WORK"
class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActvity","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}
