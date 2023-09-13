package edu.arizona.cast.kangzheng.hellodate
//Import libraries
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {
    private lateinit var datebutton: Button//create variable for button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datebutton=findViewById(R.id.show_time)//
        datebutton.setOnClickListener {//create a listener to run the
            //function when user clicks
            (displayDate())//call the function
        }
        displayDate()
    }

    private fun displayDate() {//function created to run the code to get current
        //time
        val dateTime = LocalDateTime.now()
        datebutton.text =
            dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                .withZone(
                    ZoneId.systemDefault()))

    }




}