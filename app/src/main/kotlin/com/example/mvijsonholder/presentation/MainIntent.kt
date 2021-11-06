package com.example.mvijsonholder.presentation

import com.example.mvijsonholder.common.BaseIntent

sealed class MainIntent : BaseIntent {
    object GetPosts : MainIntent()
}
