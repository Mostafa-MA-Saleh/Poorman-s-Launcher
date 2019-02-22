package com.gmail.mostafa.ma.saleh.poormanslauncher.view_models

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gmail.mostafa.ma.saleh.poormanslauncher.database.DB
import com.gmail.mostafa.ma.saleh.poormanslauncher.extensions.toBitmap
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable
import com.gmail.mostafa.ma.saleh.poormanslauncher.repository.LaunchablesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LaunchablesRepository = LaunchablesRepository(DB.shared.launchablesDao)
    val launchables: LiveData<List<Launchable>> = repository.allLaunchables

    private val context: Context
        get() = getApplication()

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        scope.launch(Dispatchers.IO) {
            val pm = context.packageManager
            val acceptableIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            pm.queryIntentActivities(acceptableIntent, 0)?.forEach { app ->
                val launchable = Launchable(
                    packageName = app.activityInfo.packageName,
                    label = app.loadLabel(pm).toString()
                )
                app.cacheIcon(pm)
                insert(launchable)
            }
        }
    }

    private fun ResolveInfo.cacheIcon(pm: PackageManager) = scope.launch(Dispatchers.IO) {
        File(context.cacheDir, "${activityInfo.packageName}.png").takeUnless { it.exists() }?.run {
            loadIcon(pm).toBitmap()?.compress(Bitmap.CompressFormat.PNG, 100, outputStream())
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(launchable: Launchable) = scope.launch(Dispatchers.IO) {
        repository.insert(launchable)
    }
}