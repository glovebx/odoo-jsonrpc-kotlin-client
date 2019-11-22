package com.ria4.odoo

object Config {
    const val APP_QB = "com.tencent.mtt"
    const val APP_WX = "com.tencent.mm"
    const val APP_QQ = "com.tencent.mobileqq"
    const val APP_QZONE = "com.qzone"

    const val WX_MAIN_ACTIVITY = "com.tencent.mm.ui.LauncherUI"
    const val WX_SHARE_TO_CHAT_ACTIVITY = "com.tencent.mm.ui.tools.ShareImgUI"
    const val WX_SHARE_TO_TIMELINE_ACTIVITY = "com.tencent.mm.ui.tools.ShareToTimeLineUI"
    const val QQ_SHARE_TO_CHAT_ACTIVITY = "com.tencent.mobileqq.activity.JumpActivity"

    const val MEDIA_PATH_WITH_SLASH = "/odoo/"
    const val MEDIA_GALLERY_FOLDER = "gallery"

    const val APP_CLIP_KEY_SELF = "COM_RIA4_ODOO"

    const val INTENT_CHOOSER_TITLE = "来自Odoo助手"

    // 小于 6.7.3 版本可以传 ACTION_SEND_MULTIPLE 参数 + 最多9张图片
    // 大于等于 6.7.3 版本禁止一键多图，但是可以传 ACTION_SEND_MULTIPLE 参数 + 1张图片
    const val WX_SHARE_ONLY_ONE_IMAGE_VERSION_CODE = 1360
    // 大于等于 7.0.0 版本原生方法禁止分享多图，甚至进一步不允许用 ACTION_SEND_MULTIPLE
    const val WX_SHARE_NO_IMAGE_VERSION_CODE = 1380

    const val REQUEST_IMAGE_EDIT = 1001

    const val PAGE_INDEX_FIRST = 1

    const val ACTION_MEDIA_TYPE = "action_media_type"
    const val ACTION_MEDIA_EDIT = "action_media_edit"
}