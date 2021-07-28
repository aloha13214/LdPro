package com.fsoc.template.common.extension

import org.greenrobot.eventbus.EventBus

//region eventbus
inline fun <reified T> Any.postEvent(t: T) {
    if (EventBus.getDefault().hasSubscriberForEvent(T::class.java)) {
        EventBus.getDefault().post(t)
    }
}

fun Any.registerEventBus() {
    if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this)
    }
}

fun Any.unregisterEventBus() {
    if (EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().unregister(this)
    }
}
