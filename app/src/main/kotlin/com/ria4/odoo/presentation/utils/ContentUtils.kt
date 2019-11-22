package com.ria4.odoo.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File

object ContentUtils {

    // 保存图片到相册
    fun saveImageContentValues(context: Context, image: File): Uri? {
        val uri = getImageContentUri(context, image)
        if (uri != null) {
            updateImageDateTimes(context, image)
            return uri
        }

        val localContentValues = createImageContentValues(image, System.currentTimeMillis())
        return context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues)
    }

    //    DATE_TAKEN is in milliseconds since 1970
    //    DATE_ADDED、DATE_MODIFIED is in seconds since 1970, so just multiply it by 1000 and it'll be fine
    //    NOTE: DATE_MODIFIED is for internal use by the media scanner. Do not modify this field.
    private fun updateImageDateTimes(context: Context, image: File): Int {
        val datetime = System.currentTimeMillis()
        val localContentValues = ContentValues()
        localContentValues.put(MediaStore.Images.Media.DATE_TAKEN, datetime)
        localContentValues.put(MediaStore.Images.Media.DATE_ADDED, datetime / 1000)
        // 更改媒体的时间
        return context.contentResolver.update(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , localContentValues
                , MediaStore.Images.Media.DATA + "=? "
                , arrayOf(image.absolutePath))
    }

    private fun getImageContentUri(context: Context, image: File): Uri? {
        val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ",
                arrayOf(image.absolutePath), null)
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            return Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
        }

        return null
    }

    private fun createImageContentValues(image: File, datetime: Long): ContentValues {
        val localContentValues = ContentValues()
        localContentValues.put(MediaStore.Images.Media.TITLE, image.name)
        localContentValues.put(MediaStore.Images.Media.DISPLAY_NAME, image.name)
        localContentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        localContentValues.put(MediaStore.Images.Media.DATE_TAKEN, datetime)
        //        localContentValues.put(MediaStore.Images.Media.DATE_MODIFIED, datetime / 1000)
        localContentValues.put(MediaStore.Images.Media.DATE_ADDED, datetime / 1000)
        localContentValues.put(MediaStore.Images.Media.DATA, image.absolutePath)
        localContentValues.put(MediaStore.Images.Media.SIZE, image.length())
        return localContentValues
    }

    // 保存视频到相册
    fun saveVideoContentValues(context: Context, video: File): Uri? {
        val uri = getVideoContentUri(context, video)
        if (uri != null) {
            // 更新时间
            updateVideoDateTimes(context, video)
            return uri
        }
        val localContentValues = createVideoContentValues(video, System.currentTimeMillis())
        return context.contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues)
    }

    private fun updateVideoDateTimes(context: Context, video: File): Int {
        val datetime = System.currentTimeMillis()
        val localContentValues = ContentValues()
        localContentValues.put(MediaStore.Video.Media.DATE_TAKEN, datetime)
        localContentValues.put(MediaStore.Video.Media.DATE_ADDED, datetime / 1000)
        // 更改媒体的时间
        return context.contentResolver.update(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                , localContentValues
                , MediaStore.Video.Media.DATA + "=? "
                , arrayOf(video.absolutePath))
    }

    private fun getVideoContentUri(context: Context, video: File): Uri? {
        val cursor = context.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Video.Media._ID), MediaStore.Video.Media.DATA + "=? ",
                arrayOf(video.absolutePath), null)
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            return Uri.withAppendedPath(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id.toString())
        }

        return null
    }

    private fun createVideoContentValues(video: File, datetime: Long): ContentValues {
        val localContentValues = ContentValues()
        localContentValues.put(MediaStore.Images.Media.TITLE, video.name)
        localContentValues.put(MediaStore.Images.Media.DISPLAY_NAME, video.name)
        localContentValues.put(MediaStore.Images.Media.MIME_TYPE, "video/3gp")
        localContentValues.put(MediaStore.Images.Media.DATE_TAKEN, datetime)
        //        localContentValues.put(MediaStore.Images.Media.DATE_MODIFIED, datetime / 1000)
        localContentValues.put(MediaStore.Images.Media.DATE_ADDED, datetime / 1000)
        localContentValues.put(MediaStore.Images.Media.DATA, video.absolutePath)
        localContentValues.put(MediaStore.Images.Media.SIZE, video.length())
        return localContentValues
    }
}