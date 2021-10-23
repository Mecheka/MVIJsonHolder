package com.example.mvijsonholder.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : BaseIntent> : ViewModel() {

    private var _intent: T? = null

    protected val event: T
        get() = _intent as T

    abstract fun onTrackingEvent(eventType: T)
}
