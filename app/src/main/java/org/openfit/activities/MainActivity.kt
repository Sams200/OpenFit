/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.openfit.db.repository.UserPreferencesRepository
import org.openfit.nav.NavigationHost
import org.openfit.services.WorkoutServiceManager
import org.openfit.ui.theme.OpenFitTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userPreferences: UserPreferencesRepository

    @Inject
    lateinit var workoutServiceManager: WorkoutServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        setContent {
            val theme by userPreferences.themeMode.collectAsStateWithLifecycle()
            val dynamicColor by userPreferences.materialMode.collectAsStateWithLifecycle()

            OpenFitTheme(
                dynamicColor = dynamicColor,
                themeMode = theme
            ) {
                NavigationHost()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            workoutServiceManager.stopService()
        }
    }
}