package com.fsoc.template.presentation.main.message

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsoc.template.common.Resource
import com.fsoc.template.common.Status
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.observe
import com.fsoc.template.common.extension.withViewModel
import com.fsoc.template.data.db.entity.ListMessageEntity
import com.fsoc.template.databinding.FragmentMessageBinding
import com.fsoc.template.presentation.base.BaseActivity
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.message.adapter.ListMessageAdapter
import com.fsoc.template.presentation.main.message.adapter.MessageModel

class MessageListFragment : BaseFragment<MessageListViewModel, FragmentMessageBinding>() {

    private lateinit var listMessageAdapter: ListMessageAdapter
    val lst = arrayListOf<MessageModel>()

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = withViewModel(viewModelFactory) {
            observe(getListMessage(), ::observerListUser)
        }
    }

    private fun observerListUser(resource: Resource<List<ListMessageEntity>>) {
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

                println("resource : ${resource.data}")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllListMessage()
    }

    override fun setUpView() {
        listMessageAdapter = ListMessageAdapter(lst)
        binding.apply {
            setting.setOnClickListener {
                startActivity(
                    Intent(
                        BaseActivity.ACTION_NOTIFICATION_LISTENER_SETTINGS
                    )
                )
            }

            rcvMessage.apply {
                adapter = listMessageAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
        }
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