package com.fsoc.template.presentation.base

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fsoc.template.R

abstract class BaseActivity<L : ViewBinding> : AppCompatActivity() {

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

//    fun currentFragment(): Fragment? {
//        val navHostFragment = supportFragmentManager.findFragmentById(getNavControllerId())
//        navHostFragment?.childFragmentManager?.apply {
//            return fragments[0]
//        }
//        return null
//    }

}