package com.fsoc.template.presentation.main.database

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.AppCommon
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentDatabaseBinding
import com.fsoc.template.presentation.base.BaseFragment


class DatabaseFragment : BaseFragment<DatabaseViewModel, FragmentDatabaseBinding>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
    }


    override fun setUpView() {
        binding.apply {
            webView.loadUrl(AppCommon.URL_KQ_XSMB)

            radioGroup.check(R.id.xsMe)
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                webView.loadUrl(getUrlWebView(checkedId))
            }
        }
    }

    private fun getUrlWebView(checkId: Int): String {
        return when (checkId) {
            R.id.xsMe -> AppCommon.URL_KQ_XSMB
            R.id.kqNet -> AppCommon.URL_XSMB
            R.id.heThong -> AppCommon.URL_XSMB
            else -> AppCommon.URL_KQ_XSMB
        }
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDatabaseBinding = FragmentDatabaseBinding.inflate(inflater, container, false)
}