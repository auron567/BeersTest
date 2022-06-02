package com.example.beerstest

import android.app.Application
import com.example.beerstest.core.utils.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree()
        Timber.plant(tree)
    }
}
