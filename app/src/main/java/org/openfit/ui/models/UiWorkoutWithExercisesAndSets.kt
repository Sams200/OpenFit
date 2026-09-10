/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

/**
 * The [org.openfit.db.relations.WorkoutWithExercisesAndSets] model used only by the ui. The difference is the use
 * of [ImmutableList] instead of [List] in order to ensure the [Immutable] annotation and improve
 * composition performance.
 *
 * @see [org.openfit.db.relations.WorkoutWithExercisesAndSets]
 */
@Immutable
data class UiWorkoutWithExercisesAndSets(
    val workout: UiWorkout,
    val exercisesWithSets: ImmutableList<UiExerciseWithSets>
)
