package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.saher.android.waterfiltercompanion_jetpackcompose.R
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingBackground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingForeground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.utils.quantityStringResourceWithFallback
import kotlin.math.roundToInt

@Composable
fun RingIndicator(
    modifier: Modifier = Modifier,
    fill: Float,
    daysInUse: Int?,
 /*remaningCapacityPercentage: Int*/){//adding parameters of the files

    var remaningCapacityPercentage by remember {
        mutableStateOf(0)
    }
    ConstraintLayout(modifier) {

        val (percentageRef,daysInUseRef, labelRef) = createRefs() //creating a reference (like id) for the constraint layout

        Ring( // we took the Ring from main screen and added it here to include it in this layout
            modifier= modifier.fillMaxSize(), 
            // if you put .fillMaxSize() in the modifier in MainScreen.kt, the ring will take the whole screen 
            bgColor = MaterialTheme.colors.ColorRingBackground,
            fgColor = MaterialTheme.colors.ColorRingForeground,
            fill = fill
        ){ fgFill -> //the last parameter for [Ring]
            remaningCapacityPercentage = (fgFill * 100).roundToInt()
        }
        Text(
            modifier =Modifier.constrainAs(percentageRef){
                                                         top.linkTo(parent.top)
                                                         bottom.linkTo(parent.bottom)
                                                         start.linkTo(parent.start)
                                                         end.linkTo(parent.end) },
            text = stringResource(id = R.string.ring_indicator_remaining_capacity_percentage_format, remaningCapacityPercentage),
            style = MaterialTheme.typography.h2,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier=Modifier.constrainAs(daysInUseRef){
                                                       top.linkTo(parent.top,margin = 100.dp)
                                                       start.linkTo(parent.start)
                                                       end.linkTo(parent.end)
                                                       bottom.linkTo(percentageRef.top)
            } ,
            text = quantityStringResourceWithFallback( //to prevent a crash when a null is presented, we used this function.
                R.plurals.ring_indicator_days_in_use_format,
                daysInUse
            ).uppercase() ,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = stringResource(R.string.ring_indicator_remaining_capacity_label).uppercase(),
            style = MaterialTheme.typography.overline,
            modifier = Modifier.constrainAs(labelRef){
                top.linkTo(percentageRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 100.dp)
            },
            fontSize = 12.sp

        )

    }
}