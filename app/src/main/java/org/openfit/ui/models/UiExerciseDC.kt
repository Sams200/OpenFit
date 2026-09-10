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
import kotlinx.collections.immutable.persistentListOf
import org.openfit.enums.exercise.Category
import org.openfit.enums.exercise.Equipment
import org.openfit.enums.exercise.Force
import org.openfit.enums.exercise.Level
import org.openfit.enums.exercise.Mechanic
import org.openfit.enums.exercise.Muscle

/**
 * The [org.openfit.db.entity.ExerciseDC] model used only by the ui. The difference is the use
 * of [ImmutableList] instead of [List] in order to ensure the [Immutable] annotation and improve
 * composition performance.
 *
 * @see [org.openfit.db.entity.ExerciseDC]
 */
@Immutable
data class UiExerciseDC(
    val id: String = "",
    val name: String = "",
    val force: Force? = null,
    val level: Level = Level.BEGINNER,
    val mechanic: Mechanic? = null,
    val equipment: Equipment? = null,
    val primaryMuscles: ImmutableList<Muscle> = persistentListOf(),
    val secondaryMuscles: ImmutableList<Muscle> = persistentListOf(),
    val category: Category = Category.POWERLIFTING,
    val isCustomExercise: Boolean = false
)