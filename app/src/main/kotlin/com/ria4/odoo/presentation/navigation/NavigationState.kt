package com.ria4.odoo.presentation.navigation

/**
 * Created by glovebx on 11.11.2019.
 */
data class NavigationState constructor(
        var activeTag: String? = null,
        var firstTag: String? = null,
        var isCustomAnimationUsed: Boolean = false) {

    fun clear() {
        activeTag = null
        firstTag = null
    }
}