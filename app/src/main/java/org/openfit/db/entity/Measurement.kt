/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.db.entity

import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.openfit.models.Weight
import java.time.LocalDateTime

@Entity(tableName = "measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bodyWeight: Weight = Weight.zero(),
    @get:IntRange(0, 100) val bodyFatPercentage: Int = 0,
    @get:IntRange(0, 100) val muscleMassPercentage: Int = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val notes: String = ""
)
