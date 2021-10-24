package com.example.mvijsonholder.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T : BaseIntent> : ViewModel() {

    abstract fun onTrackingEvent(eventType: T)
}
