package com.ria4.odoo.presentation.navigation

import com.ria4.odoo.presentation.utils.Experimental

/**
 * Created by glovebx on 11.11.2019.
 */
@Experimental
sealed class BackStrategy {

    object KEEP : BackStrategy()
    object DESTROY : BackStrategy()
}