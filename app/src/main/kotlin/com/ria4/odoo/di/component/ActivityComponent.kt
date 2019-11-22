package com.ria4.odoo.di.component

import com.ria4.odoo.di.module.ActivityModule
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.presentation.screen.auth.AuthActivity
import com.ria4.odoo.presentation.screen.dispatch.DispatchActivity
import com.ria4.odoo.presentation.screen.home.HomeActivity
import dagger.Subcomponent

/**
 * Created by glovebx on 11.11.2019.
 */

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(homeActivity: HomeActivity)

    fun inject(authActivity: AuthActivity)

    fun inject(dispatchActivity: DispatchActivity)

}