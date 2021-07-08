package com.saher.android.waterfiltercompanion_jetpackcompose.watercontrol

import com.saher.android.waterfiltercompanion_jetpackcompose.datapresistance.LocalRespository

class ConsumeWaterUseCase (
    private val localRespository: LocalRespository
        ){

    /**
     * Takes in the current capacity, consumes the predefined amount, persists the
     * remaining capacity after consumption.
     *
     * Returns true if water was consumed, false otherwise.
     */

    suspend operator fun invoke(
        currentCapacity: Int?
    ): Boolean{
        if (currentCapacity == null) return false
        currentCapacity.takeIf { it>= UNITS_TO_CONSUME }?.let {
            val newCapacity = currentCapacity - UNITS_TO_CONSUME
            localRespository.setRemainingCapacity(newCapacity)
            return true
        }
        return false
    }

    companion object{
        // we consume 1 litre each time.
        const val UNITS_TO_CONSUME = 1
    }
}