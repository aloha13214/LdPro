package com.fsoc.template.presentation.main.home

import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.toast
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeFragmentViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(listUser, ::observeListUser)
        }
    }

    private fun observeListUser(listUser: String) {
        toast(requireContext(), listUser)
    }

    override fun setUpView() {
        homeClick.click {
            viewModel.fetch()
            navigate(R.id.detailFragment)
        }
    }

    override fun showErrorMsg(err: Throwable) {
        super.showErrorMsg(err)
        homeClick.text = "Go to detail"
    }

    override fun fireData() {
//        viewModel.checkAppExpire()
    }
}