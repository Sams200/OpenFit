/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.screens.beforeSaving

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.openfit.db.entity.Workout
import org.openfit.db.relations.WorkoutWithExercisesAndSets
import org.openfit.db.repository.UserPreferencesRepository
import org.openfit.db.repository.WorkoutRepository
import org.openfit.di.qualifiers.IoDispatcher
import org.openfit.enums.SetMode
import org.openfit.enums.WorkoutState
import org.openfit.helpers.DataHelper
import org.openfit.models.Weight
import org.openfit.nav.Route
import org.openfit.services.WorkoutServiceManager
import org.openfit.ui.models.UiExerciseWithSets
import org.openfit.ui.models.UiWorkout
import org.openfit.ui.models.mappers.toEntity
import org.openfit.ui.models.mappers.toUi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class BeforeSavingScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
    private val workoutServiceManager: WorkoutServiceManager,
    private val dataHelper: DataHelper,
    userPreferencesRepository: UserPreferencesRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val useScrollWheelForInput = userPreferencesRepository.useScrollWheelForInput

    val dismissScrollWheelInputAutomatically =
        userPreferencesRepository.dismissScrollWheelInputAutomatically

    private val runningWorkoutId = savedStateHandle.toRoute<Route.BeforeSavingScreen>().runningWorkoutId


    private val _exercises = MutableStateFlow<List<UiExerciseWithSets>>(emptyList())
    val exercises = _exercises.asStateFlow()

    private val _volume = MutableStateFlow(Weight.zero())
    val volume = _volume.asStateFlow()

    private val _workout = MutableStateFlow(UiWorkout())
    val workout = _workout.asStateFlow()

    init {
        viewModelScope.launch {
            val pendingWorkout = workoutRepository.getPendingWorkout()
            val runningWorkoutWithExercises =
                pendingWorkout ?: workoutRepository.getWorkoutWithExercisesAndSets(runningWorkoutId)

            val e = runningWorkoutWithExercises.exercisesWithSets

            _exercises.update { e }

            _workout.update {
                runningWorkoutWithExercises.workout.copy(completed = LocalDateTime.now())
            }

            val volume = dataHelper.fetchVolumeFromWorkout(
                WorkoutWithExercisesAndSets(Workout(), e.map { it.toEntity() })
            )

            _volume.update {
                volume
            }
        }
    }





    fun setTimeElapsed(timeElapsed: Int) {
        _workout.update { currentWorkout ->
            currentWorkout.copy(timeElapsed = timeElapsed)
        }
    }

    fun updateWorkoutTitle(string: String) {
        _workout.update { currentWorkout ->
            currentWorkout.copy(title = string)
        }
    }

    fun isTitleEmpty(): Boolean {
        return workout.value.title.isEmpty()
    }

    fun isTitleTooLong(): Boolean {
        return workout.value.title.length >= 30
    }

    fun updateWorkoutNotes(newNotes: String) {
        _workout.update { currentWorkout ->
            currentWorkout.copy(notes = newNotes)
        }
    }

    fun detachWorkoutFromRoutine() {
        _workout.update { currentWorkout ->
            currentWorkout.copy(routineId = Random.nextLong())
        }
        _routine.update {
            UiWorkout()
        }
    }

    fun updateCompletedDate(selectedDateMillis: Long?) {
        if (selectedDateMillis != null) {
            _workout.update { currentWorkout ->
                currentWorkout.copy(
                    completed = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(selectedDateMillis),
                        ZoneOffset.UTC
                    )
                )
            }
        }
    }


    private val _routine = MutableStateFlow(UiWorkout())
    val routine = _routine.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                _routine.update {
                    workoutRepository.getRoutineFromRoutineID(workout.value.routineId).toUi()
                }
                delay(200.milliseconds)
            }
        }
    }



    fun saveExercisesWithWorkout() {
        workoutServiceManager.stopService()

        viewModelScope.launch(ioDispatcher) {
            workoutRepository.setPendingWorkout(null)
            workoutRepository.addWorkoutWithExercisesAndSets(
                WorkoutWithExercisesAndSets(
                    workout = workout.value.copy(
                        id = runningWorkoutId,
                        state = WorkoutState.COMPLETED
                    ).toEntity(),
                    exercisesWithSets = exercises.value.map { exercise ->
                        exercise.toEntity().copy(
                            sets = exercise.toEntity().sets.map {
                                // This keeps only relevant data on the actual type of set
                                when (exercise.exercise.setMode) {
                                    SetMode.DURATION -> it.copy(reps = 0, load = Weight.zero())
                                    SetMode.BODYWEIGHT -> it.copy(
                                        elapsedTime = 0,
                                        load = Weight.zero()
                                    )
                                    SetMode.BODYWEIGHT_WITH_LOAD -> it.copy(elapsedTime = 0)
                                    SetMode.LOAD -> it.copy(elapsedTime = 0)
                                }
                            }
                        )
                    }
                )
            )
        }
    }
}