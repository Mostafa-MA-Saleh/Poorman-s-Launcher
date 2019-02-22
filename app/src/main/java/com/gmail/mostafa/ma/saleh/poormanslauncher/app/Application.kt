package com.gmail.mostafa.ma.saleh.poormanslauncher.app

import com.facebook.drawee.backends.pipeline.Fresco
import com.gmail.mostafa.ma.saleh.poormanslauncher.database.DB

class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        DB.initialize(this)
        Fresco.initialize(this)
    }
}