package edu.arizona.cast.lxu.glucose

import android.app.Application
class GlucoseApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        GlucoseRepository.initialize(this)

    }

}