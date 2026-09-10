/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.openfit.enums.userPreferences.UnitSystem
import org.openfit.ui.screens.MainScreen
import org.openfit.ui.screens.beforeSaving.BeforeSavingScreen
import org.openfit.ui.screens.calendar.CalendarScreen
import org.openfit.ui.screens.editExercise.EditExerciseScreen
import org.openfit.ui.screens.editWorkout.EditWorkoutScreen
import org.openfit.ui.screens.exercises.ExercisesScreen
import org.openfit.ui.screens.infoExercise.InfoExerciseScreen
import org.openfit.ui.screens.infoWorkout.InfoWorkoutScreen
import org.openfit.ui.screens.measurements.MeasurementScreen
import org.openfit.ui.screens.settings.SettingsScreen
import org.openfit.ui.screens.shared.RequestPermissionScreen
import org.openfit.ui.screens.shared.SharedViewModel
import org.openfit.ui.screens.shared.SuccessScreen
import org.openfit.ui.screens.statistics.StatisticsScreen
import org.openfit.ui.screens.workout.WorkoutScreen

val LocalUnitSystem = compositionLocalOf { UnitSystem.METRIC }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationHost(
    sharedViewModel: SharedViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    val unitSystem by sharedViewModel.unitSystem.collectAsStateWithLifecycle()

    val showWelcomeScreen by sharedViewModel.showWelcomeScreen.collectAsStateWithLifecycle()

    val requestPermissionNextTime by sharedViewModel.requestPermissionNextTime.collectAsStateWithLifecycle()

    val startDestination = remember {
        Route.MainScreen
    }

    CompositionLocalProvider(LocalUnitSystem provides unitSystem) {
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                enterTransition = { scaleIn(tween(300), 0.9f) + fadeIn(tween(200)) },
                exitTransition = { scaleOut(tween(300), 1.1f) },
                popEnterTransition = { scaleIn(tween(300), 1.1f) },
                popExitTransition = { scaleOut(tween(300), 0.9f) + fadeOut(tween(200)) }
            ) {
                composable<Route.BeforeSavingScreen> {
                    BeforeSavingScreen(
                        navController = navController,
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.CalendarScreen> {
                    CalendarScreen(
                        navController = navController,
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.EditExerciseScreen> {
                    EditExerciseScreen(
                        navController = navController,
                        id = it.toRoute<Route.EditExerciseScreen>().id,
                        exerciseDCid = it.toRoute<Route.EditExerciseScreen>().exerciseDCid,
                    )
                }
                composable<Route.EditWorkoutScreen> {
                    EditWorkoutScreen(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.ExercisesScreen> {
                    ExercisesScreen(
                        addExercises = it.toRoute<Route.ExercisesScreen>().addExercises,
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.InfoExerciseScreen> {
                    InfoExerciseScreen(
                        id = it.toRoute<Route.InfoExerciseScreen>().id,
                        animatedVisibilityScope = this,
                        navController = navController
                    )
                }
                composable<Route.InfoWorkoutScreen> {
                    InfoWorkoutScreen(
                        animatedVisibilityScope = this,
                        navController = navController,
                        workoutId = it.toRoute<Route.InfoWorkoutScreen>().workoutId,
                    )
                }
                composable<Route.MainScreen> {
                    MainScreen(
                        navController = navController,
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.MeasurementScreen> {
                    MeasurementScreen(navigateBack = navController::navigateUp)
                }
                composable<Route.RequestPermissionScreen> {
                    RequestPermissionScreen(
                        navController = navController,
                        workoutId = it.toRoute<Route.RequestPermissionScreen>().workoutId,
                        requestPermissionNextTime = requestPermissionNextTime,
                        saveRequestPermissionAgainPreference = sharedViewModel::saveRequestPermissionAgainPreference
                    )
                }
                composable<Route.SettingsScreen> {
                    SettingsScreen(navController = navController)
                }
                composable<Route.SuccessScreen> {
                    SuccessScreen(
                        message = it.toRoute<Route.SuccessScreen>().message,
                        navController = navController
                    )
                }
                composable<Route.StatisticsScreen> {
                    StatisticsScreen(navController = navController)
                }
                composable<Route.WorkoutScreen> {
                    WorkoutScreen(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        animatedVisibilityScope = this
                    )
                }
            }
        }
    }

}