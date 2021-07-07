package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saher.android.waterfiltercompanion_jetpackcompose.common.date.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dateHelper: DateHelper
) : ViewModel() {



    //Global State
    var editMode by mutableStateOf(false)
    var totalCapacity: Int? by mutableStateOf(null)
    var remainingCapacity : Int? by mutableStateOf(null)

    /**
     * will use Unix Time Stamp
     * also will use derivedStateof method from Jetpack Compose
     * [https://developer.android.com/jetpack/compose/side-effects#derivedstateof]
     *
     */
    var installedOn: Long? by mutableStateOf(null)

    /**
     * everytime the value changes, the values below are updated by
     * JetPack Compose
     */

    //Derived State
    val installedOnFormatted :String? by derivedStateOf {
        installedOn?.let {
            dateHelper.getFormattedDate(it)
        }
    }

    //Days Used
    val daysInUse: Int? by derivedStateOf {
        installedOn?.let {
            dateHelper.getDaysSince(it)
        }
    }

    val waterFill: Float by derivedStateOf {
        //since values above aren't values.
        val tc = totalCapacity
        val rc= remainingCapacity
        if (tc != null && rc != null && areCapacityValuesValid(tc = tc,rc =  rc)){
            rc.toFloat() / tc.toFloat()
        }else{
            0f
        }
    }


    init {
        loadData()
    }

    fun loadData(){ //hardcoded
        totalCapacity = 200
        remainingCapacity = 200
        installedOn = 1625507070085L
    }

    fun onEdit(){
        editMode = true
    }
    fun onCancel(){
        editMode = false
    }

    fun onSave(){

    }

    fun onClearData(){

    }

    // checking the values before execution
    private fun areCapacityValuesValid( tc: Int, rc: Int): Boolean{
        return tc> 0 && rc > 0 && tc >= rc
    }

}
