package com.fsoc.template.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.fsoc.template.R
import com.fsoc.template.common.extension.getCurrentDate
import com.fsoc.template.common.extension.getMenuList
import com.fsoc.template.databinding.ActivityMainBinding
import com.fsoc.template.databinding.LayoutToolbarBinding
import com.fsoc.template.presentation.base.BaseActivity
import com.fsoc.template.presentation.main.menu.MenuAdapter
import com.fsoc.template.presentation.main.menu.MenuMode
import com.fsoc.template.presentation.main.menu.MenuModel
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var toggle: ActionBarDrawerToggle
    val cal = Calendar.getInstance()
    val myFormat = "MM-dd-yyyy"

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getNavControllerId(): Int {
        return R.id.mainNavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mNavController.navigate(
                when (menuItem.itemId) {
                    R.id.nav_tc -> R.id.loginFragment
                    R.id.nav_cd -> R.id.settingFragment
                    else -> R.id.homeFragment
                }
            )
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                mNavController.navigate(layoutFragment)
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
        val menuAdapter = MenuAdapter(getMenuList()) {
            decideFragmentClicked(it)
        }
        binding.rcvMenu.adapter = menuAdapter

    }

    private fun decideFragmentClicked(menu: MenuModel) {
        layoutFragment = when (menu.key) {
            MenuMode.TRANGCHU -> {
                R.id.homeFragment
            }
            MenuMode.CAI_DAT -> {
                R.id.settingFragment
            }
            else -> {
                R.id.homeFragment
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpToolbar() {
        val bindingToolBar = LayoutToolbarBinding.inflate(layoutInflater)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.toolbar.addView(bindingToolBar.root, params)
        bindingToolBar.currentDate.text = "Ngày: ${getCurrentDate()}"
    }

    override fun setUpBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onDestroy() {
        super.onDestroy()
        binding.drawerLayout.removeDrawerListener(toggle)
    }

    companion object {
        var layoutFragment = R.id.homeFragment
    }
}
