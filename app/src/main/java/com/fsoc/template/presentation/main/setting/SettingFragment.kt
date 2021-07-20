package com.fsoc.template.presentation.main.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentSettingBinding
import com.fsoc.template.domain.entity.setting.Model
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel

class SettingFragment: BaseFragment<MainViewModel, FragmentSettingBinding>() {
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
        val modelList: List<Model> = genData()
        val modelList1: List<Model> = genData1()
        val modelList2: List<Model> = genData2()
        val modelList3: List<Model> = genData3()
        val modelList4: List<Model> = genData4()
        val modelList5: List<Model> = genData5()
        val modelList6: List<Model> = genData6()
        val modelList7: List<Model> = genData7()
        val modelList8: List<Model> = genData8()
        val modelList9: List<Model> = genData9()
        val modelList10: List<Model> = genData10()
        val modelList11: List<Model> = genData11()


        val customDropDownAdapter = CustomDropDownAdapter(getBaseContext(), modelList)
        val customDropDownAdapter1 = context?.let { CustomDropDownAdapter(it, modelList1) }
        val customDropDownAdapter2 = context?.let { CustomDropDownAdapter(it, modelList2) }
        val customDropDownAdapter3 = context?.let { CustomDropDownAdapter(it, modelList3) }
        val customDropDownAdapter4 = context?.let { CustomDropDownAdapter(it, modelList4) }
        val customDropDownAdapter5 = context?.let { CustomDropDownAdapter(it, modelList5) }
        val customDropDownAdapter6 = context?.let { CustomDropDownAdapter(it, modelList6) }
        val customDropDownAdapter7 = context?.let { CustomDropDownAdapter(it, modelList7) }
        val customDropDownAdapter8 = context?.let { CustomDropDownAdapter(it, modelList8) }
        val customDropDownAdapter9 = context?.let { CustomDropDownAdapter(it, modelList9) }
        val customDropDownAdapter10 = context?.let { CustomDropDownAdapter(it, modelList10) }
        val customDropDownAdapter11 = context?.let { CustomDropDownAdapter(it, modelList11) }
        
        binding.sp.adapter = customDropDownAdapter
        binding.sp1.adapter = customDropDownAdapter1
        binding.sp2.adapter = customDropDownAdapter2
        binding.sp3.adapter = customDropDownAdapter3
        binding.sp4.adapter = customDropDownAdapter4
        binding.sp5.adapter = customDropDownAdapter5
        binding.sp6.adapter = customDropDownAdapter6
        binding.sp7.adapter = customDropDownAdapter7
        binding.sp8.adapter = customDropDownAdapter8
        binding.sp9.adapter = customDropDownAdapter9
        binding.sp10.adapter = customDropDownAdapter10
        binding.sp11.adapter = customDropDownAdapter11
    }

    private fun genData5(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Có nhận tin trùng"))
        tmp.add(Model("2. Không nhận tin trùng"))
        return tmp
    }

    private fun genData6(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Báo cáo kiểu cũ "))
        tmp.add(Model("2. Báo cáo kiểu mới "))
        return tmp
    }

    private fun genData7(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Theo tổng tiền nhận "))
        tmp.add(Model("2. Theo tổng tiền tồn "))
        return tmp
    }

    private fun genData8(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Trả đủ "))
        tmp.add(Model("2. Trả nhiều nhất 2 nháy "))
        tmp.add(Model("3. Trả nhiều nhất 3 nháy "))
        tmp.add(Model("4. Trả nhiều nhất 4 nháy "))
        return tmp
    }

    private fun genData9(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không cảnh báo "))
        tmp.add(Model("2. Có cảnh báo "))
        return tmp
    }

    private fun genData10(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không tách xiên "))
        tmp.add(Model("2. Tách riêng xiên 2-3-4 "))
        return tmp
    }

    private fun genData11(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không báo thiếu tin "))
        tmp.add(Model("2. Có báo thiếu tin "))
        return tmp
    }

    private fun genData4(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không nhắn hết giờ"))
        tmp.add(Model("2. Nhắn hết giờ"))
        return tmp
    }

    private fun genData3(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không giới hạn"))
        tmp.add(Model("2. 160 kí tự"))
        tmp.add(Model("3. 320 kí tự"))
        tmp.add(Model("4. 480 kí tự"))
        tmp.add(Model("5. 1000 kí tự"))
        tmp.add(Model("6. 2000 kí tự (Zalo)"))
        return tmp
    }

    private fun genData2(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Không làm tròn"))
        tmp.add(Model("2. Làm tròn đến 10"))
        tmp.add(Model("3. Làm tròn đến 50"))
        tmp.add(Model("4. Làm tròn đến 100"))
        return tmp
    }

    private fun genData1(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model("1. Chuyển theo tiền"))
        tmp.add(Model("2. Chuyển theo điểm"))
        return tmp
    }

    private fun genData(): List<Model> {
        val lst = arrayListOf<Model>()
        lst.add(Model("0 trả thưởng"))
        for ( i in 1..10){
            lst.add(Model("Nhân $i lần"))
        }
        return lst
    }

//    override fun observable() {
//    }

    override fun fireData() {
//        viewModel.checkAppExpire()
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }
}