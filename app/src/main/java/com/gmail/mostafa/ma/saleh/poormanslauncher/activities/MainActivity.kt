package com.gmail.mostafa.ma.saleh.poormanslauncher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.gmail.mostafa.ma.saleh.poormanslauncher.R
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable
import com.gmail.mostafa.ma.saleh.poormanslauncher.recycler_adapters.LaunchablesAdapter
import com.gmail.mostafa.ma.saleh.poormanslauncher.utils.SnapToBlock
import com.gmail.mostafa.ma.saleh.poormanslauncher.view_models.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val launchablesAdapter by lazy {
        LaunchablesAdapter(this, ROW_COUNT, COLUMN_COUNT).apply {
            setOnLaunchableClickListener(::onLaunchableClick)
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        launchablesRecyclerView.adapter = launchablesAdapter
        (launchablesRecyclerView.layoutManager as? GridLayoutManager)?.apply { spanCount = ROW_COUNT }
        SnapToBlock.attachToRecyclerView(launchablesRecyclerView)
        viewModel.launchables.observe(this, Observer(launchablesAdapter::updateLauchables))
    }

    private fun onLaunchableClick(launchable: Launchable) = launchable.launch(this)

    companion object {
        private const val COLUMN_COUNT = 5
        private const val ROW_COUNT = 6
    }
}


