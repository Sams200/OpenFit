/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.screens.shared

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.openfit.R
import org.openfit.enums.SuccessMessage
import org.openfit.enums.userPreferences.ThemeMode
import org.openfit.nav.Route
import org.openfit.ui.components.GetAppNameInAnnotatedBuilder
import org.openfit.ui.components.OpenFitButton
import org.openfit.ui.components.OpenFitScaffold
import org.openfit.ui.components.animations.SuccessLottie
import org.openfit.ui.theme.OpenFitTheme

@Composable
fun SuccessScreen(
    message: SuccessMessage,
    navController: NavHostController
) {
    OpenFitScaffold { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            if (maxHeight > maxWidth) {
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .width(maxWidth)
                        .height(maxHeight),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    successScreenContent(
                        message = message,
                        navigateBack = navController::navigateUp,
                        maxHeight = maxHeight,
                        maxWidth = maxWidth
                    )
                }
            } else {
                LazyRow(
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .width(maxWidth)
                        .height(maxHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    successScreenContent(
                        message = message,
                        navigateBack = navController::navigateUp,
                        maxHeight = maxHeight,
                        maxWidth = maxWidth
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun LazyListScope.successScreenContent(
    message: SuccessMessage,
    navigateBack: () -> Unit,
    maxHeight: Dp,
    maxWidth: Dp
) {
    item {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = when (message) {
                    SuccessMessage.ROUTINE_SAVED -> stringResource(R.string.routine_saved)
                    SuccessMessage.WORKOUT_SAVED -> stringResource(R.string.workout_saved)
                    SuccessMessage.EXERCISE_SAVED -> stringResource(R.string.exercise_saved)
                },
                style = MaterialTheme.typography.displaySmallEmphasized,
                textAlign = TextAlign.Center
            )
            val size = remember(maxHeight, maxWidth) { min(maxHeight, maxWidth) / 2 }
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(4000))
            )
            Box(contentAlignment = Alignment.Center) {
                ElevatedCard(
                    modifier = Modifier
                        .size(size.times(1.2f))
                        .graphicsLayer {
                            this.rotationZ = rotation
                        },
                    shape = MaterialShapes.Cookie7Sided.toShape()
                ) { }
                SuccessLottie(Modifier.size(size))
            }
        }
    }

    item {
        ElevatedCard(
            modifier = Modifier.padding(20.dp),
            shape = MaterialTheme.shapes.extraLargeIncreased
        ) {
            Column(
                modifier = Modifier.padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        GetAppNameInAnnotatedBuilder(MaterialTheme.typography.titleLargeEmphasized)
                        append(" ")
                        append(stringResource(R.string.openfit_made_by_you))
                    },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                // Animated button

                OpenFitButton(
                    onClick = navigateBack,
                    text = stringResource(R.string.label_continue),
                    icon = painterResource(R.drawable.ic_arrow_forward),
                    elevated = false
                )
            }
        }
    }
}

@Preview(locale = "en")
@Composable
private fun SuccessScreenPreview() {
    OpenFitTheme(dynamicColor = false, themeMode = ThemeMode.DARK) {
        SuccessScreen(SuccessMessage.WORKOUT_SAVED, rememberNavController())
    }
}