package com.fsoc.template.presentation.main.messagetemplate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentMessageTemplateBinding
import com.fsoc.template.domain.entity.templatemessage.MessageTemplateModel
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel
import com.fsoc.template.presentation.main.messagetemplate.adapter.MessageTemplateAdapter

class MessageTemplateFragment : BaseFragment<MainViewModel, FragmentMessageTemplateBinding>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    private lateinit var messageTemplateAdapter: MessageTemplateAdapter
    private var mHeros: ArrayList<MessageTemplateModel>? = null
    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun setUpView() {
        mHeros = ArrayList()
        createMessageTempList()
        messageTemplateAdapter = MessageTemplateAdapter(mHeros as ArrayList<MessageTemplateModel>) { hero -> onClickItem(hero) }
        binding.apply {
            rcvItem.apply {
                adapter = messageTemplateAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
    private fun createMessageTempList() {
        mHeros?.add(MessageTemplateModel(getString(R.string.title_one), getString(R.string.description_one)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_two), getString(R.string.description_two)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_three), getString(R.string.description_three)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_four), getString(R.string.description_four)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_five), getString(R.string.description_five)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_six), getString(R.string.description_six)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_seven), getString(R.string.description_seven)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_eight), getString(R.string.description_eight)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_night), getString(R.string.description_night)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_ten), getString(R.string.description_ten)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_eleven), getString(R.string.description_eleven)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_twelve), getString(R.string.description_twelve)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_thirteen), getString(R.string.description_thirteen)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_fourteen), getString(R.string.description_fourteen)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_fifteen), getString(R.string.description_fifteen)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_sixteen), getString(R.string.description_sixteen)))
        mHeros?.add(MessageTemplateModel(getString(R.string.title_seventeen), getString(R.string.description_seventeen)))
    }
    private fun onClickItem(messageTemp: MessageTemplateModel) {
        binding.apply {
            tvNameTitle.text = messageTemp.nameTitle
            tvDescription.text = messageTemp.description
        }
    }

    override fun fireData() {
        // do nothing
    }

    override fun setUpBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessageTemplateBinding {
        return FragmentMessageTemplateBinding.inflate(inflater, container, false)
    }
}