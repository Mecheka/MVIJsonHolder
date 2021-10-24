package com.example.mvijsonholder.ui

import com.example.mvijsonholder.common.BaseIntent

sealed class MainIntent : BaseIntent {
    object GetPosts : MainIntent()
}
