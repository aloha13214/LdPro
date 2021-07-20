package com.fsoc.template.presentation.main

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.fsoc.template.R
import com.fsoc.template.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {
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
                    R.id.nav_tc -> R.id.loginFragment
                    R.id.nav_cd -> R.id.settingFragment
                    else -> R.id.homeFragment
                }
            )
            drawerLayout.closeDrawers()
            true
        }

        handleDatePicker()
    }

    private fun handleDatePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(myFormat, Locale.US)
                timePicker.text = sdf.format(cal.time)
            }

        val sdf = SimpleDateFormat(myFormat, Locale.US)
        timePicker.text = sdf.format(cal.time)

        timePicker.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
