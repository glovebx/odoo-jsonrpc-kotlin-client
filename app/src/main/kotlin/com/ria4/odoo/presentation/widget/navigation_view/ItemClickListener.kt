package com.ria4.odoo.presentation.widget.navigation_view

/**
 * Created by glovebx on 20.08.2017.
 */
interface ItemClickListener {

    operator fun invoke(item: NavigationItem, position: Int)
}