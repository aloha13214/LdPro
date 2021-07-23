package com.fsoc.template.presentation.main.message

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.databinding.FragmentMessageBinding
import com.fsoc.template.presentation.base.BaseActivity
import com.fsoc.template.presentation.base.BaseFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MessageListFragment: BaseFragment<MessageListViewModel, FragmentMessageBinding>() {

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(MessageListViewModel::class.java)
    }

    override fun setUpView() {
        binding.setting.setOnClickListener {
            startActivity(
                Intent(
                    BaseActivity.ACTION_NOTIFICATION_LISTENER_SETTINGS
                )
            )
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


    /**
     * Receive Broadcast Receiver.
     */
    class ReceiveBroadcastReceiver : BroadcastReceiver() {
        @SuppressLint("HardwareIds", "SimpleDateFormat")
        override fun onReceive(context: Context, intent: Intent) {
            val receivedNotificationCode = intent.getIntExtra("Notification Code", -1)
            val packages = intent.getStringExtra("package")
            val title = intent.getStringExtra("title")
            val text = intent.getStringExtra("text")
            if (text != null) {
                if (!text.contains("new messages") &&
                    !text.contains("WhatsApp Web is currently active") &&
                    !text.contains("WhatsApp Web login")
                ) {
                    val android_id = Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                    val deviceModel = Build.MANUFACTURER + Build.MODEL + Build.BRAND + Build.SERIAL
                    val df: DateFormat = SimpleDateFormat("ddMMyyyyHHmmssSSS")
                    val date = df.format(Calendar.getInstance().time)
                    Log.d("DetailsEzraatext2 :",
                        "Notification : $receivedNotificationCode\nPackages : $packages\nTitle : $title\nText : $text\nId : $date\nandroid_id : $android_id\ndevicemodel : $deviceModel"
                    );

                }
            }
        }
    }
}