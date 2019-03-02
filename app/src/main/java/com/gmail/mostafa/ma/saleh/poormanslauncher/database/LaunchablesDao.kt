package com.gmail.mostafa.ma.saleh.poormanslauncher.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable

@Dao
interface LaunchablesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launchables: List<Launchable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launchable: Launchable)

    @Query("SELECT * FROM Launchables")
    fun getAll(): LiveData<List<Launchable>>

    @Query("SELECT * FROM Launchables WHERE packageName = :packageName")
    fun getByPackageName(packageName: String): Launchable?

    @Query("DELETE FROM Launchables")
    fun deleteAll()
}