/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.models.mappers

import org.openfit.db.entity.Exercise
import org.openfit.ui.models.UiExercise

fun Exercise.toUi(): UiExercise {
    return UiExercise(
        id = this.id,
        idExerciseDC = this.idExerciseDC,
        notes = this.notes,
        setMode = this.setMode,
        restTime = this.restTime,
        position = this.position,
        workoutId = this.workoutId
    )
}

fun UiExercise.toEntity(): Exercise {
    return Exercise(
        id = this.id,
        idExerciseDC = this.idExerciseDC,
        notes = this.notes,
        setMode = this.setMode,
        restTime = this.restTime,
        position = this.position,
        workoutId = this.workoutId
    )
}
