package com.fsoc.template.presentation.main.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentSettingBinding
import com.fsoc.template.domain.entity.setting.Model
import com.fsoc.template.common.preferences.SharedPrefsHelper
import com.fsoc.template.domain.entity.setting.*
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel

class SettingFragment: BaseFragment<MainViewModel, FragmentSettingBinding>() {

    val shared = SharedPrefsHelper
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(MainViewModel::class.java)
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

        saveKey(genData(), binding.sp, REWARD_SETTING)
        saveKey(genData1(), binding.sp1, UNIT_SETTING)
        saveKey(genData2(), binding.sp2, ROUND_SETTING)
        saveKey(genData3(), binding.sp3, CHARACTER_SETTING)
        saveKey(genData4(), binding.sp4, TIME_SETTING)
        saveKey(genData5(), binding.sp5, MESSAGE_SETTING)
        saveKey(genData6(), binding.sp6, REPORT_SETTING)
        saveKey(genData7(), binding.sp7, SORT_SETTING)
        saveKey(genData8(), binding.sp8, PAY_BONUS_SETTING)
        saveKey(genData9(), binding.sp9, ERR_SETTING)
        saveKey(genData10(), binding.sp10, DETACHED_SETTING)
        saveKey(genData11(), binding.sp11, MINOR_REPORT_SETTING)
    }

    private fun saveKey(genData1: List<Model>, spinner: AppCompatSpinner, keyShared: String) {
        val value = context?.let { shared.getString(it, keyShared) }
        genData1.forEachIndexed { index, model ->
            if (value == null) {
                spinner.setSelection(index)
                return@forEachIndexed
            } else if (model.name == value) {
                model.isCheck = true
                spinner.setSelection(index)
            }
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                context?.let {
                    shared.remove(it, keyShared)
                    shared.saveString(it, keyShared, genData1[position].name)
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }

    private fun genData(): List<Model> {
        val lst = arrayListOf<Model>()
        lst.add(Model(RewardSettingEnum.NO_REWARD.value))
        lst.add(Model(RewardSettingEnum.REWARD_ONE.value))
        lst.add(Model(RewardSettingEnum.REWARD_TWO.value))
        lst.add(Model(RewardSettingEnum.REWARD_THREE.value))
        lst.add(Model(RewardSettingEnum.REWARD_FOUR.value))
        lst.add(Model(RewardSettingEnum.REWARD_FIVE.value))
        lst.add(Model(RewardSettingEnum.REWARD_SIX.value))
        lst.add(Model(RewardSettingEnum.REWARD_SEVEN.value))
        lst.add(Model(RewardSettingEnum.REWARD_EIGHT.value))
        lst.add(Model(RewardSettingEnum.REWARD_NINE.value))
        lst.add(Model(RewardSettingEnum.REWARD_TEN.value))
        return lst
    }

    private fun genData1(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(UnitSettingEnum.MONEY_TRANSFER.value))
        tmp.add(Model(UnitSettingEnum.POINT_TRANSFER.value))
        return tmp
    }

    private fun genData2(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(RoundSettingEnum.NO_ROUND.value))
        tmp.add(Model(RoundSettingEnum.ROUND_ONE.value))
        tmp.add(Model(RoundSettingEnum.ROUND_TWO.value))
        tmp.add(Model(RoundSettingEnum.ROUND_THREE.value))
        return tmp
    }

    private fun genData3(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(CharacterSettingEnum.NO_CHARACTER.value))
        tmp.add(Model(CharacterSettingEnum.ROUND_ONE.value))
        tmp.add(Model(CharacterSettingEnum.ROUND_TWO.value))
        tmp.add(Model(CharacterSettingEnum.ROUND_THREE.value))
        tmp.add(Model(CharacterSettingEnum.ROUND_FOUR.value))
        tmp.add(Model(CharacterSettingEnum.ROUND_FIVE.value))
        return tmp
    }

    private fun genData4(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(TimeSettingEnum.NO_NOTIFICATION.value))
        tmp.add(Model(TimeSettingEnum.NOTIFICATION.value))
        return tmp
    }

    private fun genData5(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(MessageSettingEnum.SAME_MESSAGE.value))
        tmp.add(Model(MessageSettingEnum.NO_SAME_MESSAGE.value))
        return tmp
    }

    private fun genData6(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(ReportSettingEnum.REPORT_OLD.value))
        tmp.add(Model(ReportSettingEnum.REPORT_NEW.value))
        return tmp
    }

    private fun genData7(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(SortSettingEnum.SORT_ONE.value))
        tmp.add(Model(SortSettingEnum.SORT_TWO.value))
        return tmp
    }

    private fun genData8(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(PayBonusSettingEnum.PAY_BONUS_ONE.value))
        tmp.add(Model(PayBonusSettingEnum.PAY_BONUS_TWO.value))
        tmp.add(Model(PayBonusSettingEnum.PAY_BONUS_THREE.value))
        tmp.add(Model(PayBonusSettingEnum.PAY_BONUS_FOUR.value))
        return tmp
    }

    private fun genData9(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(ErrSettingEnum.ERR_ONE.value))
        tmp.add(Model(ErrSettingEnum.ERR_TWO.value))
        return tmp
    }

    private fun genData10(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(DetachedSettingEnum.NO_DETACHED.value))
        tmp.add(Model(DetachedSettingEnum.DETACHED.value))
        return tmp
    }

    private fun genData11(): List<Model> {
        val tmp = arrayListOf<Model>()
        tmp.add(Model(MinorReportSettingEnum.MINOR_REPORT_ONE.value))
        tmp.add(Model(MinorReportSettingEnum.MINOR_REPORT_TWO.value))
        return tmp
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }
}