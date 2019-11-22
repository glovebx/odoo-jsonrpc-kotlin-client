package com.ria4.odoo.presentation.screen.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ria4.odoo.R
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.presentation.base_mvp.base.BaseActivity
import com.ria4.odoo.presentation.screen.auth.AuthActivity
import com.ria4.odoo.presentation.utils.extensions.*
import com.ria4.odoo.presentation.utils.glide.TransformationType
import com.ria4.odoo.presentation.utils.glide.load
import com.ria4.odoo.presentation.widget.navigation_view.NavigationItem
import com.ria4.odoo.presentation.widget.navigation_view.NavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import permissions.dispatcher.*
import javax.inject.Inject
import com.ria4.odoo.presentation.widget.navigation_view.NavigationId as Id

@RuntimePermissions
class HomeActivity : BaseActivity<HomeContract.View, HomeContract.Presenter>(), HomeContract.View,
        NavigationItemSelectedListener {

    private val TRANSLATION_X_KEY = "TRANSLATION_X_KEY"
    private val CARD_ELEVATION_KEY = "CARD_ELEVATION_KEY"
    private val SCALE_KEY = "SCALE_KEY"

    private var exitTime: Long = 0L

    @Inject
    protected lateinit var homePresenter: HomePresenter

    override fun initPresenter() = homePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ria4.odoo.R.layout.activity_home)
        initViews()

        presenter.getNavigatorState()?.let {
            navigator.restore(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fun put(key: String, value: Float) = outState.putFloat(key, value)
        with(mainView) {
            put(TRANSLATION_X_KEY, translationX)
            put(CARD_ELEVATION_KEY, scale)
            put(SCALE_KEY, cardElevation)
        }
    }

    override fun onRestoreInstanceState(savedState: Bundle?) {
        super.onRestoreInstanceState(savedState)
        savedState?.let {
            with(mainView) {
                translationX = it.getFloat(TRANSLATION_X_KEY)
                scale = it.getFloat(CARD_ELEVATION_KEY)
                cardElevation = it.getFloat(SCALE_KEY)
            }
        }
    }

    override fun onDestroy() {
        presenter.saveNavigatorState(navigator.getState())
        super.onDestroy()
    }

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navView.navigationItemSelectListener = this
        navView.header.userName

        drawerLayout.drawerElevation = 0F
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val moveFactor = navView.width * slideOffset
                mainView.translationX = moveFactor
                mainView.scale = 1 - slideOffset / 4
                mainView.cardElevation = slideOffset * 10.toPx(this@HomeActivity)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                presenter.handleDrawerOpen()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                presenter.handleDrawerClose()
            }
        })
        drawerLayout.setScrimColor(Color.TRANSPARENT)

//        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
//            v.addTopMargin(insets.systemWindowInsetTop)
//            navView.addBottomMargin(insets.systemWindowInsetBottom)
//            insets
//        }
    }

    override fun setArcArrowState() {
        arcView.onClick {
            super.onBackPressed()
        }
        arcImage.setAnimatedImage(com.ria4.odoo.R.drawable.arrow_left)
    }

    override fun setArcHamburgerIconState() {
        drawerLayout?.let {
            arcView.onClick {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            //  汉堡格式菜单
            arcImage.setAnimatedImage(com.ria4.odoo.R.drawable.hamb)
        }
    }

    override fun openHomeFragment() {
//        goTo<>()
    }

    override fun openLoginActivity() {
        start<AuthActivity>()
        showToast("Logged out")
        finish()
    }

    override fun setToolBarTitle(title: String) {
        toolbarTitle?.setAnimatedText(title)
    }

    override fun onFragmentChanged(currentTag: String, currentFragment: Fragment) {
        presenter.handleFragmentChanges(currentTag, currentFragment)
    }

    override fun updateDrawerInfo(user: User) {
        val header = navView.header
        with(header) {
            userName.text = user.userName
            userInfo.text = user.userName
            userAvatar.load(user.userIcon, TransformationType.CIRCLE)
        }
    }

    override fun checkNavigationItem(position: Int) {
        navView?.let {
            navView.setChecked(position)
        }
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
            else -> {
                if (arcImage.tag == R.drawable.hamb) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - this.exitTime > 2000) {
                        showToast("再按一次退出")
                        this.exitTime = currentTime
                        return
                    }
                }
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: NavigationItem) {
        when (item.id) {
            Id.HOME -> {
//                goTo<>()
            }
            Id.ABOUT -> {
//                goTo<AboutFragment>()
            }
            Id.LOG_OUT -> {
                presenter.logOut()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission("android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
    fun permissionNeeds() {
    }

    @OnShowRationale("android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
    fun permissionShowRationale(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setTitle("提示" as CharSequence)
                .setMessage("没有所需权限，将无法继续，请点击下方“确定”后同意取得权限。" as CharSequence)
                .setPositiveButton("确定" as CharSequence) { _, _ -> request.proceed() }
                .setNegativeButton("取消" as CharSequence) { _, _ -> request.cancel() }
                .show()
    }

    @OnPermissionDenied("android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
    fun permissionDenied() {
        showToast("没有权限，无法继续")
    }

    @OnNeverAskAgain("android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
    fun permissionNeverAskAgain() {
        ask4Permission()
    }

    private fun ask4Permission() {
        AlertDialog.Builder(this)
                .setTitle("注意" as CharSequence)
                .setMessage("因为APP需要权限，请点击下方“设置”按钮后进入权限设置打开所需权限。" as CharSequence)
                .setNegativeButton("关闭" as CharSequence, null)
                .setPositiveButton("设置" as CharSequence) { _, _ ->
                    startActivity(Intent("android.settings.APPLICATION_DETAILS_SETTINGS").also {
                        it.data = Uri.parse("package:$packageName")
                    })
                }
                .show()
    }
}
