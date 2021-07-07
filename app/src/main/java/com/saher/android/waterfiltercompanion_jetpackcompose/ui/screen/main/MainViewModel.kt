package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saher.android.waterfiltercompanion_jetpackcompose.R
import com.saher.android.waterfiltercompanion_jetpackcompose.common.date.DateHelper
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog.CapacityInputDialogConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dateHelper: DateHelper
) : ViewModel() {



    //Global State
    var editMode by mutableStateOf(false)
    var totalCapacity: Int? by mutableStateOf(null)
    var remainingCapacity : Int? by mutableStateOf(null)

    //Candidate values to be used by dialogs
    var totalCapacityCandidate: String? by mutableStateOf(null)
    var remainingCapacityCandidate: String? by mutableStateOf(null)
    var installedOnCandidate: Long? by mutableStateOf(null)

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

    //Dialogs
    var capacityInputDialogConfig: CapacityInputDialogConfig? by mutableStateOf(null)

    //Derived State
    val installedOnFormatted :String? by derivedStateOf {
        installedOn?.let {
            dateHelper.getFormattedDate(it)
        }
    }
    // making this value with the same format therefore we sync both current and saved
    val installedOnCandidateFormatted: String? by derivedStateOf {
        installedOnCandidate?.let {
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

    //Events channel to interact with Time picker Dialog through the sealed class EVENT
    private val eventChannel = Channel<Event>()

    //we can get the time more than once, that is why we didn't use consumeAsFlow
    val eventsFlow = eventChannel.receiveAsFlow()


    init {
        loadData()
    }

    fun loadData(){ //hardcoded
        totalCapacity = 200
        remainingCapacity = 200
        installedOn = 1625507070085L
    }

    fun onEdit(){
        syncCandidateValues()
        editMode = true
    }
    fun onCancel(){
        editMode = false
        clearCandidateValues()
    }

    fun onSave(){

    }

    fun onClearData(){

    }

    //Click Methods
    fun onTotalCapacityClick(){
        capacityInputDialogConfig= CapacityInputDialogConfig(
            titleStringRes = R.string.capacity_input_dialog_total_title,
            initialInput = totalCapacity?.toString(),
            onSubmit = {
                       totalCapacityCandidate = it
                       capacityInputDialogConfig = null //closing the dialog after saving the data
            },
            onDismiss = {
                capacityInputDialogConfig = null
            }
        )
    }

    fun onRemainingCapacityClick(){
        capacityInputDialogConfig= CapacityInputDialogConfig(
            titleStringRes = R.string.capacity_input_dialog_remaining_title,
            initialInput = remainingCapacity?.toString(),
            onSubmit = {
                       remainingCapacityCandidate =it
                       capacityInputDialogConfig = null //closing the dialog after saving the data
            },
            onDismiss = {
                capacityInputDialogConfig = null
            }
        )
    }

    fun onInstalledOnClick(){
        viewModelScope.launch {
            eventChannel.send(
                Event.ShowDatePicker(currentDateTimeStamp = installedOnCandidate){ timestamp ->
                    installedOnCandidate = timestamp
                }
            )
        }
    }

    //Syncing and clearing values
    private fun syncCandidateValues(){
        totalCapacityCandidate = totalCapacity?.toString()
        remainingCapacityCandidate = remainingCapacity?.toString()
        installedOnCandidate = installedOn
    }

    private fun clearCandidateValues(){
        totalCapacityCandidate = null
        remainingCapacityCandidate = null
        installedOnCandidate = null
    }

    // checking the values before execution
    private fun areCapacityValuesValid( tc: Int, rc: Int): Boolean{
        return tc> 0 && rc > 0 && tc >= rc
    }

    //to call the date picker after we injected it in Main Activity
    sealed class Event{
        class ShowDatePicker(val currentDateTimeStamp: Long?,val cb: (Long) -> Unit) : Event()
    }

}
