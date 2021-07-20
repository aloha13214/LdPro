package com.fsoc.template.common.di

import com.fsoc.template.common.di.module.*
import com.fsoc.template.presentation.main.home.HomeFragment
import com.fsoc.template.presentation.main.login.LoginFragment
import com.fsoc.template.presentation.main.setting.SettingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DaoModule::class, CommonModule::class, MapperModule::class, RepoModule::class, ViewModelModule::class])
interface AppComponent {
    // inject fragment
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: LoginFragment)
}