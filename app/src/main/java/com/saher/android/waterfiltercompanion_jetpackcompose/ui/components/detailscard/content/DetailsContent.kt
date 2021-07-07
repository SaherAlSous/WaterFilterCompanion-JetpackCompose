package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.detailscard.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.R
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.detailscard.content.item.DetailsContentItem
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.utils.stringResourceWithFallback
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.utils.stringwithFallback

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    //Total Capacity
    totalCapacity: Int?,
    onTotalCapacityClick: () -> Unit,
    totalCapacityCandidate: String?,

    //Remaining Capacity
    remainingCapacity: Int?,
    onRemainingCapacityClick: () -> Unit,
    remainingCapacityCandidate: String?,

    //Installed on
    installOnFormatted: String?,
    onInstallOnFormatted: ()-> Unit,
    installedOnCandidateFormatted: String?,

    //Editmode Boolean
    editMode: Boolean
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(
                IntrinsicSize.Min
                /**
                 * we want to keep the height of the [Row] to the
                 * Minimum, instead of going till the end of the screen
                 */
                /**
                 * we want to keep the height of the [Row] to the
                 * Minimum, instead of going till the end of the screen
                 */
            )
    ) {
        /**
         * since we have three identical columns, we extracted them into
         * [DetailsContentItem] and added the needed details.
         */
        /**
         * since we have three identical columns, we extracted them into
         * [DetailsContentItem] and added the needed details.
         */
        val boxModifier = Modifier.weight(1f) // to align them equally inside the [Row]
        DetailsContentItem(
            value = stringResourceWithFallback(R.string.details_card_total_format, totalCapacity?.toString()),//<-- We are using the null check function
            candidateValue = stringResourceWithFallback(R.string.details_card_total_format,totalCapacityCandidate),
            label = stringResource(R.string.details_card_total_label),
            modifier = boxModifier,
            onClick = onTotalCapacityClick,
            editmode = editMode
        )

        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp))

        DetailsContentItem(
            value = stringResourceWithFallback(R.string.details_card_remaining_format, remainingCapacity?.toString()), //<-- We are using the null check function
            candidateValue = stringResourceWithFallback(R.string.details_card_remaining_format,remainingCapacityCandidate),
            label = stringResource(R.string.details_card_remaining_label),
            modifier = boxModifier,
            onClick = onRemainingCapacityClick,
            editmode = editMode
        )

        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp))

        DetailsContentItem(
            value = stringwithFallback(installOnFormatted) ,//<-- We are using the null check function
            candidateValue = stringwithFallback(installedOnCandidateFormatted),
            label = stringResource(R.string.details_card_installed_on_label),
            modifier = boxModifier,
            onClick = onInstallOnFormatted,
            editmode = editMode
        )
    }
}