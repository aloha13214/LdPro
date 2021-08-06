package com.fsoc.template.presentation.main.home

import android.Manifest
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.getIMEI
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.toast
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.common.service.ReceiveBroadcastReceiver
import com.fsoc.template.data.api.entity.Todo
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.databinding.FragmentHomeBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var imageChangeBroadcastReceiver: ReceiveBroadcastReceiver? = null

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getTodos(), ::observeListTodos)
        }
    }

    private fun observeListTodos(resource: Resource<List<Todo>>) {
        when (resource.status) {
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
        requirePermissionsIfNeeded()
    }

    private fun requirePermissionsIfNeeded() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    context?.let { viewModel.getPhoneNumber(it) }
                    imageChangeBroadcastReceiver =
                        ReceiveBroadcastReceiver(
                            viewModel.phoneNumbers,
                            viewModel.databaseHelperMessage,
                            viewModel.database,
                            this@HomeFragment::onCallBackListMessage,
                            this@HomeFragment::onCallBackMessage,
                        )
                    val intentFilter = IntentFilter()
                    intentFilter.addAction("com.example.ssa_ezra.whatsappmonitoring")
                    activity?.registerReceiver(imageChangeBroadcastReceiver, intentFilter)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    println("MainActivity.onPermissionRationaleShouldBeShown")
                }
            }).check()
    }

    private fun onCallBackListMessage(listMessageEntity: ListMessageEntity) {
        // No action
    }

    private fun onCallBackMessage(messageEntity: MessageEntity) {
        // No action
    }

    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}