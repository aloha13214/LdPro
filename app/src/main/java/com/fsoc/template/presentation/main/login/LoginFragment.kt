package com.fsoc.template.presentation.main.login

import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment<LoginViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int = R.layout.fragment_login

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {

        }
    }

    override fun setUpView() {
        btn_get_todos.click {
            viewModel.fetchUser()
        }
    }

    override fun fireData() {
    }
}