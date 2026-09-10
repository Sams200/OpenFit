/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.models.mappers

import kotlinx.collections.immutable.toImmutableList
import org.openfit.db.entity.ExerciseDC
import org.openfit.ui.models.UiExerciseDC

fun ExerciseDC.toUi(): UiExerciseDC {
    return UiExerciseDC(
        id = this.id,
        name = this.name,
        force = this.force,
        level = this.level,
        mechanic = this.mechanic,
        equipment = this.equipment,
        primaryMuscles = this.primaryMuscles.toImmutableList(),
        secondaryMuscles = this.secondaryMuscles.toImmutableList(),
        category = this.category,
        isCustomExercise = this.isCustomExercise
    )
}

fun UiExerciseDC.toEntity(): ExerciseDC {
    return ExerciseDC(
        id = this.id,
        name = this.name,
        force = this.force,
        level = this.level,
        mechanic = this.mechanic,
        equipment = this.equipment,
        primaryMuscles = this.primaryMuscles,
        secondaryMuscles = this.secondaryMuscles,
        category = this.category,
        isCustomExercise = this.isCustomExercise
    )
}