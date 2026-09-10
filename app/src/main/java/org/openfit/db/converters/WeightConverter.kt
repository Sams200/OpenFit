/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.db.converters

import androidx.room.TypeConverter
import org.openfit.models.Weight

class WeightConverter {
    @TypeConverter
    fun fromDouble(value: Double?): Weight? = value?.let { Weight.kilograms(it) }

    @TypeConverter
    fun fromWeight(weight: Weight?): Double? = weight?.inKilograms
}
