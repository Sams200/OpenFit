/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.openfit.db.converters.ExerciseDCConverter
import org.openfit.db.converters.LocalDateTimeConverter
import org.openfit.db.converters.WeightConverter
import org.openfit.db.dao.DatasetDao
import org.openfit.db.dao.MeasurementDao
import org.openfit.db.dao.WorkoutDao
import org.openfit.db.entity.Exercise
import org.openfit.db.entity.ExerciseDC
import org.openfit.db.entity.Measurement
import org.openfit.db.entity.Set
import org.openfit.db.entity.Workout

@Database(
    entities = [Workout::class, Exercise::class, Set::class, Measurement::class, ExerciseDC::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 3, to = 4)
    ]
)
@TypeConverters(LocalDateTimeConverter::class, ExerciseDCConverter::class, WeightConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME = "openfit_database"

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    ALTER TABLE exercises
                    ADD COLUMN position INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    UPDATE exercises AS current
                    SET position = (
                        SELECT COUNT(*) - 1
                        FROM exercises AS previous
                        WHERE previous.workoutId = current.workoutId
                          AND previous.id <= current.id
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE INDEX IF NOT EXISTS index_exercises_workoutId_position
                    ON exercises(workoutId, position)
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE INDEX IF NOT EXISTS index_exercises_idExerciseDC
                    ON exercises(idExerciseDC)
                    """.trimIndent()
                )
            }
        }
    }

    abstract fun getWorkoutDao(): WorkoutDao

    abstract fun getMeasurementDao(): MeasurementDao

    abstract fun getDatasetDao(): DatasetDao
}
