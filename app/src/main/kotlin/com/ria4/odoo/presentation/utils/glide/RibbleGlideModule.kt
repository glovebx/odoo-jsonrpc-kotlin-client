package com.ria4.odoo.presentation.utils.glide

import android.app.ActivityManager
import android.content.Context
import androidx.core.app.ActivityManagerCompat
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions


/**
 * Created by glovebx on 17.02.2017.
 */

@GlideModule
class RibbleGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val activityManager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryCacheSizeBytes = 1024 * 1024 * 20L // 20mb
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))
        builder.setDefaultRequestOptions(RequestOptions().format(
                if (ActivityManagerCompat.isLowRamDevice(activityManager)) {
                    DecodeFormat.PREFER_RGB_565
                } else {
                    DecodeFormat.PREFER_ARGB_8888
                })
        )
    }

}
