package com.fsoc.template.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.getIMEI
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.toast
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.databinding.FragmentHomeBinding
import com.fsoc.template.presentation.base.BaseFragment


class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getTodos(), ::observeListTodos)
        }
    }

    private fun observeListTodos(resource: Resource<List<Todo>>) {
        when(resource.status){
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                showLoading(false)
                toast(requireContext(), "Success ${resource.data?.size}")
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.let { showErrorMsg(it) }
            }
        }
    }

    override fun setUpView() {
        binding.imeiDevice.text = getIMEI()
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}