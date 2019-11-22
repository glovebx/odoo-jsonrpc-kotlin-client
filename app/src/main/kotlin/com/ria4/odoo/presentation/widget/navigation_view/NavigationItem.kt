package com.ria4.odoo.presentation.widget.navigation_view

import com.ria4.odoo.R

/**
 * Created by glovebx on 20.08.2017.
 */
data class NavigationItem(val item: NavigationId,
                          val icon: Int,
                          var isSelected: Boolean = false,
                          val itemIconColor: Int = R.color.navigation_item_color) {

    val name: String
        get() = item.name

    val id: NavigationId
        get() = item
}