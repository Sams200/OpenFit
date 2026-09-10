/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (c) 2024-2026. The OpenFit Contributors
 *
 * OpenFit is subject to additional terms covering author attribution and trademark usage;
 * see the ADDITIONAL_TERMS.md and TRADEMARK_POLICY.md files in the project root.
 */

package org.openfit.ui.screens


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavHostController
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.openfit.R
import org.openfit.enums.pages.MainScreenPages
import org.openfit.nav.Route
import org.openfit.ui.components.GetAppNameInAnnotatedBuilder
import org.openfit.ui.components.OpenFitScaffold
import org.openfit.ui.screens.home.HomeScreen
import org.openfit.ui.screens.profile.ProfileScreen


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.MainScreen(
    navController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val fabAction: () -> Unit = remember {
        {
            navController.navigate(Route.EditWorkoutScreen(0L)) {
                launchSingleTop = true
            }
        }
    }

    val pagerState = rememberPagerState(
        initialPage = MainScreenPages.HOME.ordinal,
        pageCount = { MainScreenPages.entries.size }
    )

    val coroutine = rememberCoroutineScope()

    val goToPage: (Int) -> Unit = remember {
        { pageIndex ->
            coroutine.launch {
                pagerState.animateScrollToPage(pageIndex)
            }
        }
    }


    OpenFitScaffold(
        title = buildAnnotatedString {
            GetAppNameInAnnotatedBuilder(MaterialTheme.typography.titleLargeEmphasized)
        },
        actions = persistentListOf(
            { navController.navigate(Route.SettingsScreen) { launchSingleTop = true } }
        ),
        actionsIcons = persistentListOf(
            painterResource(R.drawable.ic_settings)
        ),
        actionsElevated = persistentListOf(false),
        fabAction = if (pagerState.currentPage == MainScreenPages.HOME.ordinal) fabAction else null,
        fabIcon = painterResource(R.drawable.ic_add),
        fabDescription = stringResource(R.string.create_routine),
        fabText = stringResource(R.string.create_routine),
        bottomBar = {
            NavigationBar {
                MainScreenPages.entries.forEach { page ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == page.ordinal,
                        onClick = { goToPage(page.ordinal) },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = when (page) {
                                        MainScreenPages.HOME -> R.drawable.ic_home
                                        MainScreenPages.PROFILE -> R.drawable.ic_person
                                    }
                                ),
                                contentDescription = stringResource(
                                    id = when (page) {
                                        MainScreenPages.HOME -> R.string.home
                                        MainScreenPages.PROFILE -> R.string.profile
                                    }
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(
                                    id = when (page) {
                                        MainScreenPages.HOME -> R.string.home
                                        MainScreenPages.PROFILE -> R.string.profile
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            contentPadding = innerPadding
        ) { pageIndex ->
            when (pageIndex) {
                0 -> HomeScreen(navController, animatedVisibilityScope)
                1 -> ProfileScreen(navController, animatedVisibilityScope)
                else -> error("Invalid page index in main screen: $pageIndex. Number of pages: ${pagerState.pageCount}")
            }
        }
    }
}