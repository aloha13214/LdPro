package com.fsoc.template.presentation.main.xsmb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.common.AppCommon
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.show
import com.fsoc.template.databinding.FragmentNorthernLotteryBinding
import com.fsoc.template.presentation.base.BaseFragment


class LiveLotteryFragment :
    BaseFragment<LiveLotteryViewModel, FragmentNorthernLotteryBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(LiveLotteryViewModel::class.java)
    }

    override fun setUpView() {
        binding.webView.loadUrl(AppCommon.URL_XSMB)
        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.lnWebView.show(true)
            } else {
                binding.lnWebView.show(false)
            }
        }
    }

    override fun fireData() {
        // do nothing
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNorthernLotteryBinding {
        return FragmentNorthernLotteryBinding.inflate(inflater, container, false)
    }

}