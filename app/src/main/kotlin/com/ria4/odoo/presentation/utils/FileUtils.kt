package com.ria4.odoo.presentation.utils

import android.os.Environment
import com.ria4.odoo.Config
import java.io.File

object FileUtils {

    fun getGalleryFolder(): File {
        val gallery = File(File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), Config.MEDIA_PATH_WITH_SLASH)
                , Config.MEDIA_GALLERY_FOLDER)
        if (!gallery.exists()) gallery.mkdirs()
        return gallery
    }
}