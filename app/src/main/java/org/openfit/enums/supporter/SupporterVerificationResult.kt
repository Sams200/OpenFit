/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.enums.supporter

enum class SupporterVerificationResult {
    VALID_COMPANION_APP_SIGNATURE,
    INVALID_COMPANION_APP_SIGNATURE,
    OPENFIT_APP_NOT_FOUND,
    OPENFIT_APP_SIGNATURE_ERROR,
    COMPANION_APP_NOT_FOUND,
    COMPANION_APP_SIGNATURE_ERROR,

    VALID_CODE,
    INVALID_CODE,
    PUBLIC_KEY_NOT_INITIALIZED_PROPERLY,
    INVALID_PUBLIC_KEY,
    INAPPROPRIATE_PUBLIC_KEY_STRING,
    ALGORITHM_NOT_AVAILABLE,
    INVALID_SIGNATURE_ENCODING,
    MALFORMED_CODE,
    MISSING_DOT_SEPARATOR,

    INVALID_PUBLIC_KEY_ENCODING,

    UNKNOWN_ERROR
}