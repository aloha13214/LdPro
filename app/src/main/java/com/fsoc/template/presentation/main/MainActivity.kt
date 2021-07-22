package com.fsoc.template.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fsoc.template.R
import com.fsoc.template.common.extension.getCurrentDate
import com.fsoc.template.databinding.ActivityMainBinding
import com.fsoc.template.databinding.LayoutToolbarBinding
import com.fsoc.template.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(binding.appBarMain.toolbar)
        setUpToolbar()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_setting, R.id.nav_listCustomerFragment), binding.drawerLayout
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

    override fun setUpBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
