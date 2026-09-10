/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2025-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.openfit.activities.MainActivity
import org.openfit.di.qualifiers.MainActivityClass

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @MainActivityClass
    fun provideMainActivityClass(): Class<out Activity> {
        return MainActivity::class.java
    }
}