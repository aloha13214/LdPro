package com.fsoc.template.presentation.main.message.list

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsoc.template.R
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.showConfirmDialog
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.common.service.ReceiveBroadcastReceiver
import com.fsoc.template.common.service.SmsReceiveBroadcastReceiver
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.databinding.FragmentMessageBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.customer.list.ListCustomerFragment
import com.fsoc.template.presentation.main.customer.list.Mode
import com.fsoc.template.presentation.main.message.list.adapter.ListMessageAdapter
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel

class MessageListFragment : BaseFragment<MessageListViewModel, FragmentMessageBinding>() {
    companion object {
        const val KEY_MESSAGE_ADD_CUSTOMER = "KEY_MESSAGE_ADD_CUSTOMER"
        const val KEY_MESSAGE_DETAIL = "KEY_MESSAGE_DETAIL"
    }

    private var imageChangeBroadcastReceiver: ReceiveBroadcastReceiver? = null

    private lateinit var listMessageAdapter: ListMessageAdapter

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getListMessage(), ::observerListMessage)
            observe(removeMessage(), ::observerDelete)
        }
    }

    private fun observerDelete(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.let { showErrorMsg(it) }
            }
            Status.SUCCESS -> {
                showLoading(false)
                viewModel.getAllListMessage()
            }
        }
    }

    private fun observerListMessage(resource: Resource<List<ListMessageEntity>>) {
        when (resource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.ERROR -> {
                showLoading(false)
                resource.e?.let { showErrorMsg(it) }
            }
            Status.SUCCESS -> {
                showLoading(false)
                resource.data?.let {
                    val tmp = it.toCollection(arrayListOf())
                    tmp.sortBy { obj -> obj.time }
                    tmp.reverse()
                    listMessageAdapter.setData(tmp)
                } ?: run {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllListMessage()
    }

    override fun setUpView() {
        listMessageAdapter = ListMessageAdapter(
            this@MessageListFragment::onItemClick,
            this@MessageListFragment::onItemAdd,
            this@MessageListFragment::onItemDelete
        )
        binding.apply {
            setting.setOnClickListener {
                startActivity(
                    Intent(
                        ACTION_NOTIFICATION_LISTENER_SETTINGS
                    )
                )
            }

            rcvMessage.apply {
                adapter = listMessageAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
        }

        imageChangeBroadcastReceiver =
            ReceiveBroadcastReceiver(
                viewModel.phoneNumbers,
                viewModel.databaseHelper,
                viewModel.chatDatabaseHelper,
                this@MessageListFragment::onCallBackListMessage,
                this@MessageListFragment::onCallBackMessage,
            )
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.ssa_ezra.whatsappmonitoring")
        activity?.registerReceiver(imageChangeBroadcastReceiver, intentFilter)

        activity?.registerReceiver(
            SmsReceiveBroadcastReceiver(
                viewModel.phoneNumbers,
                viewModel.databaseHelper,
                viewModel.chatDatabaseHelper,
                this@MessageListFragment::onCallBackListMessage,
                this@MessageListFragment::onCallBackMessage,
            ),
            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        )
    }

    private fun onCallBackListMessage(listMessageEntity: ListMessageEntity) {
        viewModel.getAllListMessage()
    }

    private fun onCallBackMessage(messageEntity: MessageEntity) {

    }

    private fun onItemAdd(listMessageEntity: ListMessageEntity) {
        val bundle = Bundle().apply {
            putSerializable(ListCustomerFragment.MODE_KEY, Mode.Add)
            putSerializable(
                KEY_MESSAGE_ADD_CUSTOMER,
                MessageModel.convertModel(listMessageEntity).apply {
                    this.isAdd = true
                })
        }
        navigate(R.id.addCustomerFragment, bundle)
    }

    private fun onItemDelete(listMessageEntity: ListMessageEntity) {
        showConfirmDialog(getString(R.string.remove_message)) {
            viewModel.removeListMessage(listMessageEntity)
        }
    }

    private fun onItemClick(listMessageEntity: ListMessageEntity) {
        val bundle = Bundle().apply {
            putSerializable(KEY_MESSAGE_DETAIL, MessageModel.convertModel(listMessageEntity))
        }
        navigate(R.id.detailMessageFragment, bundle)
    }


    override fun fireData() {
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(inflater, container, false)
    }

}