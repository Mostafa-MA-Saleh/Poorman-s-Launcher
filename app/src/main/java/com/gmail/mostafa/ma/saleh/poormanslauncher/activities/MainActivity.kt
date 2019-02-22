package com.gmail.mostafa.ma.saleh.poormanslauncher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gmail.mostafa.ma.saleh.poormanslauncher.R
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable
import com.gmail.mostafa.ma.saleh.poormanslauncher.recycler_adapters.LaunchablesAdapter
import com.gmail.mostafa.ma.saleh.poormanslauncher.view_models.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val launchablesAdapter by lazy {
        LaunchablesAdapter(this).apply {
            setOnLaunchableClickListener(::onLaunchableClick)
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        launchablesRecyclerView.adapter = launchablesAdapter
        viewModel.launchables.observe(this, Observer {
            launchablesAdapter.updateLauchables(it)
        })
    }

    private fun onLaunchableClick(launchable: Launchable) = launchable.launch(this)
}
