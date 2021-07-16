package com.fsoc.template.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.fsoc.template.R
import com.fsoc.template.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getNavControllerId(): Int {
        return R.id.mainNavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers()
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mNavController.navigate(
                when (menuItem.itemId) {
                    R.id.nav_tc -> R.id.homeFragment
                    R.id.nav_cd -> R.id.settingFragment
                    else -> R.id.homeFragment
                }
            )
            // close drawer when item is tapped
            drawerLayout.closeDrawers()
            drawerLayout.addDrawerListener(
                object : DrawerListener {
                    override fun onDrawerSlide(
                        drawerView: View,
                        slideOffset: Float
                    ) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
            )
            true
        }


    }
}
