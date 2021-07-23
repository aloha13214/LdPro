package com.fsoc.template.presentation.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fsoc.template.R
import com.fsoc.template.common.preferences.SharedPrefsHelper
import com.fsoc.template.domain.entity.setting.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity<L : ViewBinding> : AppCompatActivity() {

    companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }

    private var enableNotificationListenerAlertDialog: AlertDialog? = null
    private var disableBack = false

//    protected val mNavController: NavController by lazy {
//        findNavController(getNavControllerId())
//    }

    val progressBarHandler by lazy {
        ProgressBarHandler(this)
    }

    lateinit var binding: L

    abstract fun setUpBinding(): L

    /**
     * layout res of activity
     */
    abstract fun layoutRes(): Int

    /**
     * navigation controller id in layout
     */
//    abstract fun getNavControllerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setUpBinding()
        setContentView(binding.root)

        val firstSetting = SharedPrefsHelper.getString(this, FIRST_SETTING)
        if (firstSetting == null) {
            initDefaultSetting()
        }

        if (!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog?.show()
        }
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

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransitionEnter()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }
//
//    override fun onBackPressed() {
//        showConfirmDialog("Bạn có muốn thoát khỏi ứng dụng không?"){
//            val homeIntent = Intent(Intent.ACTION_MAIN)
//            homeIntent.addCategory(Intent.CATEGORY_HOME)
//            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(homeIntent)
//        }
//    }

    fun showLoading(isLoading: Boolean) {
//        if (isLoading && activityError.isVisible) {
//            activityError.show(false)
//        }
//        activityLoading.show(isLoading)
    }

    fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarHandler.show()
        } else {
            progressBarHandler.hide()
        }
    }

    fun showError(msg: String) {
//        activityError.show(true)
//        activityError.text = msg
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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
        alertDialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
            startActivity(
                Intent(
                    ACTION_NOTIFICATION_LISTENER_SETTINGS
                )
            )
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { _, _ ->
            // If you choose to not enable the notification listener
            // the app. will not work as expected
        }
        return alertDialogBuilder.create()
    }


//    fun currentFragment(): Fragment? {
//        val navHostFragment = supportFragmentManager.findFragmentById(getNavControllerId())
//        navHostFragment?.childFragmentManager?.apply {
//            return fragments[0]
//        }
//        return null
//    }

}