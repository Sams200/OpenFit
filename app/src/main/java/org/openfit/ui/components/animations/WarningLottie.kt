/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.components.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.openfit.R
import org.openfit.enums.userPreferences.ThemeMode
import org.openfit.ui.theme.OpenFitTheme

@Composable
fun WarningLottie() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.warning_lottie))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 0.5f
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Preview
@Composable
private fun WarningLottiePreview() {
    OpenFitTheme(dynamicColor = false, themeMode = ThemeMode.DARK) {
        WarningLottie()
    }
}