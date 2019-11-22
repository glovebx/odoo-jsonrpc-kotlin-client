package com.ria4.odoo.di.component

import com.ria4.odoo.di.module.ActivityModule
import com.ria4.odoo.di.module.ApiModule
import com.ria4.odoo.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Singleton
@Component(modules = [(ApplicationModule::class), (ApiModule::class)])
interface ApplicationComponent {

    operator fun plus(activityModule: ActivityModule): ActivityComponent
}