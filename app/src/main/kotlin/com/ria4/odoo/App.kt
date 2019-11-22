package com.ria4.odoo

import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.luseen.logger.LogType
import com.luseen.logger.Logger
//import com.squareup.leakcanary.LeakCanary
import com.ria4.odoo.di.component.ApplicationComponent
import com.ria4.odoo.di.component.DaggerApplicationComponent
import com.ria4.odoo.di.module.ApplicationModule
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.presentation.utils.extensions.MR2orAbove
import com.ria4.odoo.presentation.utils.extensions.unSafeLazy
import com.ria4.odoo.presentation.utils.extensions.log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException


/**
 * Created by glovebx on 11.11.2019.
 */
class App : MultiDexApplication() {

    val applicationComponent: ApplicationComponent by unSafeLazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    // 当前用户
    var user: User? = null

    companion object {
        lateinit var instance: App
    }
//
//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }

    override fun onCreate() {
        super.onCreate()

//        MultiDex.install(this)

//        if (LeakCanary.isInAnalyzerProcess(this)) return
//        LeakCanary.install(this)
        instance = this
        initLogger()

        Fresco.initialize(this);

        // detectFileUriExposure方法 需要SDK最小是18
        // 否则会报错 exposed beyond app through ClipData.Item.getUri()
        MR2orAbove {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }

        setRxJavaErrorHandler()
    }

    private fun initLogger() {
        Logger.Builder()
                .isLoggable(BuildConfig.DEBUG)
                .logType(LogType.ERROR)
                .tag("Odoo")
                .setIsKotlin(true)
                .build()
    }

    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(object : Consumer<Throwable> {
            override fun accept(e: Throwable) {
//                var e = e
                if (e is UndeliverableException) {
//                    e = e.cause
                }
                if (e is IOException) {
                    // fine, irrelevant network problem or API that throws on cancellation
                    return
                }
                if (e is InterruptedException) {
                    // fine, some blocking code was interrupted by a dispose call
                    return
                }
                if (e is NullPointerException || e is IllegalArgumentException) {
                    // that's likely a bug in the application
                    Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
                    return
                }
                if (e is IllegalStateException) {
                    // that's a bug in RxJava or in a custom operator
                    Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
                    return
                }
                log("Undeliverable exception")
                log(e)
            }
        })
    }
}
