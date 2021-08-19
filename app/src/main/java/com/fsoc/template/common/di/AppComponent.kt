package com.fsoc.template.common.di

import com.fsoc.template.common.di.module.*
import com.fsoc.template.presentation.main.customer.add.AddCustomerFragment
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment
import com.fsoc.template.presentation.main.customer.setting.SettingKhongNhanSoFragment
import com.fsoc.template.presentation.main.customer.setting.SettingTimeFragment
import com.fsoc.template.presentation.main.home.HomeFragment
import com.fsoc.template.presentation.main.login.LoginFragment
import com.fsoc.template.presentation.main.message.detail.MessageFragment
import com.fsoc.template.presentation.main.message.list.MessageListFragment
import com.fsoc.template.presentation.main.setting.SettingFragment
import com.fsoc.template.presentation.main.xsmb.LiveLotteryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DaoModule::class, CommonModule::class, MapperModule::class, RepoModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ListCustomerFragment)
    fun inject(fragment: AddCustomerFragment)
    fun inject(fragment: MessageListFragment)
    fun inject(fragment: MessageFragment)
    fun inject(fragment: SettingTimeFragment)
    fun inject(fragment: SettingKhongNhanSoFragment)
    fun inject(fragment: LiveLotteryFragment)
}