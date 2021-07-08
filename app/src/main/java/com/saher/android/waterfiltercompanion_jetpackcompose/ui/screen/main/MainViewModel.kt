package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saher.android.waterfiltercompanion_jetpackcompose.R
import com.saher.android.waterfiltercompanion_jetpackcompose.common.date.DateHelper
import com.saher.android.waterfiltercompanion_jetpackcompose.datapresistance.DataModel
import com.saher.android.waterfiltercompanion_jetpackcompose.datapresistance.LocalRespository
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog.CapacityInputDialogConfig
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.confirmationdialog.ConfirmationDialogConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dateHelper: DateHelper,
    private val localRepository: LocalRespository
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
    var confirmationDialogConfig: ConfirmationDialogConfig? by mutableStateOf(null)

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
        loadDataAsync()
    }

    //loading data from repository
    private fun loadDataAsync(){
        viewModelScope.launch {
            loadDataSync()
        }
    }

    private suspend fun loadDataSync(){
        localRepository.getData().let { dataModel ->
            totalCapacity = dataModel.totalCapacity
            remainingCapacity= dataModel.remainingCapacity
            installedOn= dataModel.installedOn
        }
    }

    fun onEdit(){
        syncCandidateValues()
        editMode = true
    }
    fun onCancel(){
        leaveEditMode()
    }
    private fun leaveEditMode(){
        editMode = false
        clearCandidateValues()
    }

    fun onSave(){
        //creating a logical check for data instead of value check
        val tc = totalCapacityCandidate?.toIntOrNull()
        val rc = remainingCapacityCandidate?.toIntOrNull()
        val io = installedOnCandidate
        if (tc == null ||
            rc == null ||
            io == null ||
            io > System.currentTimeMillis() ||
            !areCapacityValuesValid(tc, rc)) return //TODO add message

//        if (totalCapacityCandidate.isNullOrEmpty()||
//                    remainingCapacityCandidate.isNullOrEmpty() ||
//                    installedOnCandidate == null){
//            return
//        }
        //saving data using coroutines
        viewModelScope.launch {
              val dataModel = DataModel(
                  totalCapacity = totalCapacityCandidate?.toIntOrNull(),
                  remainingCapacity = remainingCapacityCandidate?.toIntOrNull(),
                  installedOn = installedOnCandidate
              )
              localRepository.setData(dataModel)
              loadDataSync()
              leaveEditMode()
          }
    }

    //Clearing data functionality
    fun onClearData(){
        confirmationDialogConfig = ConfirmationDialogConfig(
            titleStringRes = R.string.clear_data_confirmation_dialog_title,
            onConfirm = ::onClearDataConfirm,
            onCancel = ::onClearDataCancel
        )
    }

    private fun onClearDataCancel(){
        //on cancel clicked -> close the dialog
        confirmationDialogConfig = null
    }

    private fun onClearDataConfirm(){
        //Repository function to clear data
        viewModelScope.launch {
            localRepository.clearData()
            loadDataSync()
            confirmationDialogConfig = null
            leaveEditMode()
            //TODO add a message
        }
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
