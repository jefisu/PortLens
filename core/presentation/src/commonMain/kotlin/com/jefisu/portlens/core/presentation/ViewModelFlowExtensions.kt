package com.jefisu.portlens.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

private const val VIEW_MODEL_STATE_IN_STOP_TIMEOUT_MILLIS = 5_000L

context(viewModel: ViewModel)
fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> = stateIn(
    scope = viewModel.viewModelScope,
    started = SharingStarted.WhileSubscribed(VIEW_MODEL_STATE_IN_STOP_TIMEOUT_MILLIS),
    initialValue = initialValue,
)
