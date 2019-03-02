package com.gmail.mostafa.ma.saleh.poormanslauncher.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.gmail.mostafa.ma.saleh.poormanslauncher.database.LaunchablesDao
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable

class LaunchablesRepository(private val launchablesDao: LaunchablesDao) {
    val allLaunchables: LiveData<List<Launchable>> = launchablesDao.getAll()

    @WorkerThread
    suspend fun insert(launchable: Launchable) = launchablesDao.insert(launchable)

    @WorkerThread
    suspend fun getByPackageName(packageName: String) = launchablesDao.getByPackageName(packageName)
}