package com.fsoc.template.presentation.main.database

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.AppCommon
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentDatabaseBinding
import com.fsoc.template.presentation.base.BaseFragment


class DatabaseFragment : BaseFragment<DatabaseViewModel, FragmentDatabaseBinding>() {

    companion object{
        const val SO_XO_ME = "https://xoso.me/"
        const val KET_QUA_NET = "https://xoso.com.vn/"
        const val HE_THONG = "https://xoso.com.vn/"
    }

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
    }


    override fun setUpView() {
        binding.apply {
            webView.loadUrl(SO_XO_ME)


            when(radioGroup.checkedRadioButtonId) {
                R.id.xsMe -> println("me")
                R.id.kqNet -> println("kqNet")
                R.id.heThong -> println("heThong")
            }
        }

    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDatabaseBinding = FragmentDatabaseBinding.inflate(inflater, container, false)
}