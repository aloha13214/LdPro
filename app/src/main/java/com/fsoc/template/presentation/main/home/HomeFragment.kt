package com.fsoc.template.presentation.main.home

import com.fsoc.template.R
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.click
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.toast
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
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
        homeClick.click {
            viewModel.fetch()
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