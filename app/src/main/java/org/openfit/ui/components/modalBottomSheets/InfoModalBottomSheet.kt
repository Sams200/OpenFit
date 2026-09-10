/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.components.modalBottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.openfit.R
import org.openfit.enums.InfoMode
import org.openfit.enums.userPreferences.ThemeMode
import org.openfit.ui.components.MarkdownText
import org.openfit.ui.components.animations.AlarmLottie
import org.openfit.ui.components.animations.StatsLottie
import org.openfit.ui.components.animations.TrainingLottie
import org.openfit.ui.theme.OpenFitTheme

/** A modal bottom sheet which explains concepts to the user.
 * @param infoMode A [InfoMode] enum holding the info to display. If [infoMode] is equal to
 * [InfoMode.DISMISS], then InfoModalBottomSheet is not displayed
 * @param onDismiss A lambda function triggered when user leaves [InfoModalBottomSheet]
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InfoModalBottomSheet(
    infoMode: InfoMode,
    keepAndroidCheckboxCheck: Boolean? = null,
    onKeepAndroidOpenCheckboxChange: ((Boolean) -> Unit)? = null,
    onDismiss: () -> Unit
) {
    if (infoMode != InfoMode.DISMISS) {
        val title = when (infoMode) {
            InfoMode.REST_TIMER -> stringResource(R.string.rest_time)
            InfoMode.TYPE_OF_SET -> stringResource(R.string.type_of_set)
            InfoMode.BEFORE_SAVING_STATS -> stringResource(R.string.statistics)
            InfoMode.MUSCLE_DISTRIBUTION -> stringResource(R.string.muscles_distribution)
            InfoMode.EXERCISES_DISTRIBUTION -> stringResource(R.string.exercises_distribution)
            InfoMode.KEEP_ANDROID_OPEN -> stringResource(R.string.openfit_is_under_threat)
        }

        val text = when (infoMode) {
            InfoMode.REST_TIMER -> stringResource(R.string.rest_time_desc)
            InfoMode.TYPE_OF_SET -> stringResource(R.string.type_of_set_desc)
            InfoMode.BEFORE_SAVING_STATS -> stringResource(R.string.statistics_desc)
            InfoMode.MUSCLE_DISTRIBUTION -> stringResource(R.string.muscle_distribution_desc)
            InfoMode.EXERCISES_DISTRIBUTION -> stringResource(R.string.exercises_distribution_desc)
            InfoMode.KEEP_ANDROID_OPEN -> stringResource(R.string.openfit_is_under_threat_desc)
        }

        ModalBottomSheet(
            onDismissRequest = onDismiss
        ) {
            LazyColumn(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLargeEmphasized,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    Card(
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            MarkdownText(text)
                        }
                    }
                }
                if (infoMode == InfoMode.KEEP_ANDROID_OPEN && keepAndroidCheckboxCheck != null) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.do_not_show_again))
                            Checkbox(
                                checked = keepAndroidCheckboxCheck,
                                onCheckedChange = onKeepAndroidOpenCheckboxChange
                            )
                        }
                    }
                }
                item {
                    when (infoMode) {
                        InfoMode.REST_TIMER -> AlarmLottie()
                        InfoMode.TYPE_OF_SET -> TrainingLottie()
                        InfoMode.BEFORE_SAVING_STATS -> StatsLottie()
                        InfoMode.MUSCLE_DISTRIBUTION -> StatsLottie()
                        InfoMode.EXERCISES_DISTRIBUTION -> StatsLottie()
                        InfoMode.KEEP_ANDROID_OPEN -> Image(
                            painter = painterResource(R.drawable.altered_deal),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(locale = "it")
@Composable
private fun InfoModalBottomSheetPreview() {
    OpenFitTheme(dynamicColor = false, themeMode = ThemeMode.DARK) {
        InfoModalBottomSheet(
            infoMode = InfoMode.KEEP_ANDROID_OPEN,
            keepAndroidCheckboxCheck = true
        ) {

        }
    }
}