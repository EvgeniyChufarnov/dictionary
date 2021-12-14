package com.github.evgeniychufarnov.model

sealed class ScreenState<out T> {
    class Success<T>(val value: T) : ScreenState<T>()
    object Loading : ScreenState<Nothing>()
    object Error : ScreenState<Nothing>()
}