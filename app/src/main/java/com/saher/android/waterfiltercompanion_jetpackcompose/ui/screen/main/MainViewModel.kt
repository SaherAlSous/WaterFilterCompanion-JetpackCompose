package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    /**
     * To tell jetpack Compose that we want to keep updating this
     * value in the screen, we should make it as [MutableState]
     * otherwise it wont be updated on screen even if we increment
     * the value. we can't keep it counter:int = 0
     */
    var counter : Int by mutableStateOf(0)
        /**
         * if we want to have a strict access to this property by the setter
         * but still want it to be get. we put this method --> [private set]
         */
        private set

    fun increment(){
        counter++
    }
}
