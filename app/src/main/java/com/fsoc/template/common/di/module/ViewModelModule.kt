package com.fsoc.template.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fsoc.template.common.di.qualifier.ViewModelKey
import com.fsoc.template.presentation.base.ViewModelFactory
import com.fsoc.template.presentation.main.MainViewModel
import com.fsoc.template.presentation.main.customer.add.AddCustomerViewModel
import com.fsoc.template.presentation.main.customer.list.ListCustomerViewModel
import com.fsoc.template.presentation.main.home.HomeViewModel
import com.fsoc.template.presentation.main.login.LoginViewModel
import com.fsoc.template.presentation.main.message.detail.MessagesViewModel
import com.fsoc.template.presentation.main.message.list.MessageListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeFragmentViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListCustomerViewModel::class)
    abstract fun bindListCustomerViewModel(viewModel: ListCustomerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCustomerViewModel::class)
    abstract fun bindAddCustomerViewModel(viewModel: AddCustomerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageListViewModel::class)
    abstract fun bindMessageListFragmentViewModel(viewModel: MessageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun bindMessageFragmentViewModel(viewModel: MessagesViewModel): ViewModel
}