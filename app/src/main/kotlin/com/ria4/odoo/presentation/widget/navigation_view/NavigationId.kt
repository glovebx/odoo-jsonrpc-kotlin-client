package com.ria4.odoo.presentation.widget.navigation_view

import com.ria4.odoo.presentation.utils.extensions.emptyString

/**
 * Created by glovebx on 21.08.2017.
 */
sealed class NavigationId(val name: String = emptyString, val fullName: String = emptyString) {

    object HOME : NavigationId("主界面")
    object ABOUT : NavigationId("关于")
    object LOG_OUT : NavigationId("注销")
}