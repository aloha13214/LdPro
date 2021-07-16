package com.fsoc.template.presentation.main.setting

import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.domain.entity.setting.Model
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_setting
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity?:return, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setUpView() {
        val modelList: List<Model> = readFromAsset()

        val customDropDownAdapter = context?.let { CustomDropDownAdapter(it, modelList) }
        sp.adapter = customDropDownAdapter

    }

    private fun readFromAsset(): List<Model> {
        val lst = arrayListOf<Model>()
        for ( i in 0..10){
            lst.add(Model("Quang $i"))
        }
        return lst
    }

    override fun observable() {
    }

    override fun fireData() {
//        viewModel.checkAppExpire()
    }
}