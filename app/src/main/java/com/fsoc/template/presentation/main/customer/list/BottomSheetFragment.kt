package com.fsoc.template.presentation.main.customer.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fsoc.template.common.extension.click
import com.fsoc.template.data.db.entity.CustomerEntity
import com.fsoc.template.data.db.entity.SettingPrice
import com.fsoc.template.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(
    val bottomSheetListener: BottomSheetListener
) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.lnSettingPrice.click { bottomSheetListener(TypeSetting.SettingPrice) }
        binding.lnSettingTime.click { bottomSheetListener(TypeSetting.SettingTime) }
        return binding.root
    }
}

typealias BottomSheetListener = (type: TypeSetting) -> Unit

enum class TypeSetting {
    SettingPrice,
    SettingTime,
}