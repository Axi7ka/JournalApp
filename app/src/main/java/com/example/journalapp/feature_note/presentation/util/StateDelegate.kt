package com.example.journalapp.feature_note.presentation.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface StateDelegate<T> {

    val state: StateFlow<T>

    var currentState: T

}

class DefaultStateDelegate<T>(initialState: T) : StateDelegate<T> {

    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    override val state: StateFlow<T>
        get() = _state.asStateFlow()

    override var currentState: T
        get() = _state.value
        set(value) {
            _state.value = value
        }

}