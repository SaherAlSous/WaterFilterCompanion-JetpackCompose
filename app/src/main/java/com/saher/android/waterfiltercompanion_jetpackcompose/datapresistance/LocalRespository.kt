package com.saher.android.waterfiltercompanion_jetpackcompose.datapresistance

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRespository(
    private val sharedPreferences: SharedPreferences
) {

    //saving data into preferences
    suspend fun setData(dataModel: DataModel){
       withContext(Dispatchers.IO){
           sharedPreferences.edit()
               .putInt(PREF_TOTAL_CAPACITY, dataModel.totalCapacity!!)
               .putInt(PREF_REMAINED_CAPACITY, dataModel.remainingCapacity!!)
               .putLong(PREF_INSTALLED_ON, dataModel.installedOn!!)
               .commit()
       }
    }

    suspend fun getData(): DataModel{
        return withContext(Dispatchers.IO){
            DataModel(
                totalCapacity = getIntOrNull(PREF_TOTAL_CAPACITY),
                remainingCapacity = getIntOrNull(PREF_REMAINED_CAPACITY),
                installedOn = getLongOrNull(PREF_INSTALLED_ON)
            )
        }
    }

    /**
     * we created these 2 methods to insert null into the loadData function in [MainViewModel]
     */

    private fun getIntOrNull(key:String):Int? {
        return if (sharedPreferences.contains(key)){
            sharedPreferences.getInt(key, 0)
        }else null
    }
    private fun getLongOrNull(key:String):Long? {
        return if (sharedPreferences.contains(key)){
            sharedPreferences.getLong(key, 0L)
        }else null
    }

    //Clearing data function for clearing data dialog
    suspend fun clearData(){
        withContext(Dispatchers.IO){
            sharedPreferences.edit()
                .remove(PREF_TOTAL_CAPACITY)
                .remove(PREF_REMAINED_CAPACITY)
                .remove(PREF_INSTALLED_ON)
                .commit()
        }
    }

    //When the use consume water, we want to set the new capacity
    suspend fun setRemainingCapacity(remainingCapacity: Int){
        withContext(Dispatchers.IO){
            sharedPreferences.edit()
                .putInt(PREF_REMAINED_CAPACITY, remainingCapacity)
                .commit() //We use commit not apply because we are in Coroutine.
        }
    }

    //after updating the remaining capacity above, we want to update the UI
    suspend fun getRemainingCapacity(): Int?{
        return withContext(Dispatchers.IO){
            getIntOrNull(PREF_REMAINED_CAPACITY)
        }
    }


    companion object{
        const val PREF_TOTAL_CAPACITY = "total_capacity"
        const val PREF_REMAINED_CAPACITY = "remaining_capacity"
        const val PREF_INSTALLED_ON = "installed_on"
    }
}