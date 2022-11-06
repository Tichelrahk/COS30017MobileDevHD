package com.example.mealstoeat

import android.app.Application
import com.cloudinary.android.MediaManager
import com.cloudinary.android.download.picasso.PicassoDownloadRequestBuilderFactory

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        Cloudinary Setup
        MediaManager.init(this)
        MediaManager.get().setDownloadRequestBuilderFactory(PicassoDownloadRequestBuilderFactory())
    }
}