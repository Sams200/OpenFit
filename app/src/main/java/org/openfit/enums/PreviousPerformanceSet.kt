/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.enums

import org.openfit.models.Weight

/**
 * It is used to display the previous of a set in a [org.openfit.ui.components.ExerciseCard]
 */
data class PreviousPerformanceSet(
    val reps: Int = 0,
    val load: Weight = Weight.zero(),
    val time: Int = 0
)
