/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.enums.userPreferences

/**
 * Based on standard BCP-47 tags
 */
enum class Language(val code: String) : DialogPreference {
    SYSTEM(""),
    CZECH("cs"),
    DUTCH("nl"),
    ENGLISH("en"),
    GERMAN("de"),
    ITALIAN("it"),
    PORTUGUESE_BRAZIL("pt-BR"),
    RUSSIAN("ru"),
    SIMPLIFIED_CHINESE("zh-CN"),
    SPANISH("es")
}