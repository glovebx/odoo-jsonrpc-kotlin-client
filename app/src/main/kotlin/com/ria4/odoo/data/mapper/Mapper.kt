package com.ria4.odoo.data.mapper

import com.ria4.odoo.data.response.*
import com.ria4.odoo.domain.entity.*
import com.ria4.odoo.domain.entity.ServerVersion
import com.ria4.odoo.presentation.utils.extensions.emptyString
import com.ria4.odoo.presentation.utils.extensions.toHtml
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by glovebx on 11.11.2019.
 */
class Mapper {

//    @JvmName("translateShotEntity")
//    fun translate(shotResponseList: List<ShotResponse>): List<Shot> {
//        return shotResponseList
//                .asSequence()
//                .map {
//                    translate(it)
//                }
//                .toList()
//    }

    fun translate(serverVersion: com.ria4.odoo.data.response.ServerVersion): ServerVersion {
        return serverVersion?.let {
            ServerVersion(it.serverVersion
                    , it.serverVersionInfo?.joinToString("-")
                    , it.serverSerie
                    , it.protocolVersion)
        }
    }

}