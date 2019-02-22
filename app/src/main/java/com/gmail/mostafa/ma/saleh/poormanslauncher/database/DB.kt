package com.gmail.mostafa.ma.saleh.poormanslauncher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.mostafa.ma.saleh.poormanslauncher.app.Application
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable

@Database(entities = [Launchable::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract val launchablesDao: LaunchablesDao

    companion object {
        private var application: Application? = null

        @JvmStatic
        val shared: DB by lazy {
            Room.databaseBuilder(application!!, DB::class.java, "PoormanDatabase").build().also {
                application = null
            }
        }

        @JvmStatic
        fun initialize(context: Context) {
            application = context.applicationContext as? Application
        }
    }
}