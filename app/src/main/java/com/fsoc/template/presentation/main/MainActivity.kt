package com.fsoc.template.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fsoc.template.R
import com.fsoc.template.common.extension.getCurrentDate
import com.fsoc.template.common.preferences.SharedPrefsHelper
import com.fsoc.template.databinding.ActivityMainBinding
import com.fsoc.template.databinding.LayoutToolbarBinding
import com.fsoc.template.domain.entity.setting.*
import com.fsoc.template.presentation.base.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    }

    private var enableNotificationListenerAlertDialog: AlertDialog? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDrawerLayout()
        requirePermissionsIfNeeded()

        val firstSetting = SharedPrefsHelper.getString(this, FIRST_SETTING)
        if (firstSetting == null) {
            initDefaultSetting()
        }

        if (!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog?.show()
        }
    }

    private fun requirePermissionsIfNeeded() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(binding.appBarMain.toolbar)
        setUpToolbar()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_listMessage,
                R.id.nav_listCustomerFragment,
                R.id.nav_setting
            ), binding.drawerLayout
        )
        val mNavController = findNavController(R.id.nav_host_fragment_content)
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        binding.navView.setupWithNavController(mNavController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpToolbar() {
        val bindingToolBar = LayoutToolbarBinding.inflate(layoutInflater)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.appBarMain.toolbar.addView(bindingToolBar.root, params)
        bindingToolBar.currentDate.text = "Ngày: ${getCurrentDate()}"
    }

    /**
     * Is Notification Service Enabled.
     */
    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Build Notification Listener Alert Dialog.
     */
    private fun buildNotificationServiceAlertDialog(): AlertDialog? {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton(R.string.ok) { _, _ ->
            startActivity(
                Intent(
                    ACTION_NOTIFICATION_LISTENER_SETTINGS
                )
            )
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { _, _ ->
        }
        return alertDialogBuilder.create()
    }

    private fun initDefaultSetting() {
        SharedPrefsHelper.saveString(this, REWARD_SETTING, RewardSettingEnum.NO_REWARD.name)
        SharedPrefsHelper.saveString(this, UNIT_SETTING, UnitSettingEnum.MONEY_TRANSFER.name)
        SharedPrefsHelper.saveString(this, ROUND_SETTING, RoundSettingEnum.NO_ROUND.name)
        SharedPrefsHelper.saveString(
            this,
            CHARACTER_SETTING,
            CharacterSettingEnum.NO_CHARACTER.name
        )
        SharedPrefsHelper.saveString(this, TIME_SETTING, TimeSettingEnum.NO_NOTIFICATION.name)
        SharedPrefsHelper.saveString(this, MESSAGE_SETTING, MessageSettingEnum.SAME_MESSAGE.name)
        SharedPrefsHelper.saveString(this, REPORT_SETTING, ReportSettingEnum.REPORT_OLD.name)
        SharedPrefsHelper.saveString(this, SORT_SETTING, SortSettingEnum.SORT_ONE.name)
        SharedPrefsHelper.saveString(
            this,
            PAY_BONUS_SETTING,
            PayBonusSettingEnum.PAY_BONUS_ONE.name
        )
        SharedPrefsHelper.saveString(this, ERR_SETTING, ErrSettingEnum.ERR_ONE.name)
        SharedPrefsHelper.saveString(this, DETACHED_SETTING, DetachedSettingEnum.NO_DETACHED.name)
        SharedPrefsHelper.saveString(
            this,
            MINOR_REPORT_SETTING,
            MinorReportSettingEnum.MINOR_REPORT_ONE.name
        )

        SharedPrefsHelper.saveString(this, FIRST_SETTING, "FIRST_SETTING")
    }

    override fun setUpBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
