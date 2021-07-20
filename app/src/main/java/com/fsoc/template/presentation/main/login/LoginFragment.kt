package com.fsoc.template.presentation.main.login

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.databinding.FragmentLoginBinding
import com.fsoc.template.presentation.base.BaseFragment

class LoginFragment: BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int = R.layout.fragment_login

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {

        }
    }

    override fun setUpView() {
        binding.btnGetTodos.click {
            viewModel.fetchUser()
        }
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }
}