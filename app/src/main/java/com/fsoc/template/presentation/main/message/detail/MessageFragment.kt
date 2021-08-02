package com.fsoc.template.presentation.main.message.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.db.entity.MessageEntity
import com.fsoc.template.databinding.FragmentChatMessageBinding
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.message.detail.model.MessageAdapter
import com.fsoc.template.presentation.main.message.list.MessageListFragment.Companion.KEY_MESSAGE_DETAIL
import com.fsoc.template.presentation.main.message.list.adapter.MessageModel

class MessageFragment : BaseFragment<MessagesViewModel, FragmentChatMessageBinding>() {

    private lateinit var messageAdapter: MessageAdapter

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getListMessage(), ::observerListMessage)
        }
    }

    private fun observerListMessage(resource: Resource<List<MessageEntity>>) {
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
                    messageAdapter.setData(tmp)
                    binding.rcvMessage.scrollToPosition(0)
                } ?: run {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllListMessage()
    }

    override fun setUpView() {
        binding.apply {
            messageAdapter = MessageAdapter()
            rcvMessage.apply {
                adapter = messageAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            }
        }
    }

    override fun fireData() {
        arguments?.let {
            viewModel.modelMessage = it.getSerializable(KEY_MESSAGE_DETAIL) as? MessageModel
        }
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatMessageBinding {
        return FragmentChatMessageBinding.inflate(inflater, container, false)
    }

}