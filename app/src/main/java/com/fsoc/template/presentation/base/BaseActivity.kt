package com.fsoc.template.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.fsoc.template.R
import com.fsoc.template.common.extension.show
import com.fsoc.template.common.preferences.SharedPrefsHelper
import com.fsoc.template.domain.entity.setting.*
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_base.*

abstract class BaseActivity : AppCompatActivity() {

    private var disableBack = false

    protected val mNavController: NavController by lazy {
        findNavController(getNavControllerId())
    }

    /**
     * layout res of activity
     */
    abstract fun layoutRes(): Int

    /**
     * navigation controller id in layout
     */
    abstract fun getNavControllerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)
        View.inflate(this, layoutRes(), findViewById(R.id.activityContent))

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(mNavController)

        val firstSetting = SharedPrefsHelper.getString(this, FIRST_SETTING)
        if (firstSetting == null) {
            initDefaultSetting()
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

    override fun onSupportNavigateUp() = mNavController.navigateUp()

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

    override fun onBackPressed() {
        if (!disableBack) {
            super.onBackPressed()
        }
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading && activityError.isVisible) {
            activityError.show(false)
        }
        activityLoading.show(isLoading)
    }

    fun showError(msg: String) {
        activityError.show(true)
        activityError.text = msg
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

    fun currentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(getNavControllerId())
        navHostFragment?.childFragmentManager?.apply {
            return fragments[0]
        }
        return null
    }

}