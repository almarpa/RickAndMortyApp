package com.almarpa.rickandmortyapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.almarpa.rickandmortyapp.ui.core.BackgroundPrimaryColor
import com.almarpa.rickandmortyapp.ui.core.BackgroundSecondaryColor
import com.almarpa.rickandmortyapp.ui.core.BackgroundTertiaryColor
import com.almarpa.rickandmortyapp.ui.core.DefaultTextColor
import com.almarpa.rickandmortyapp.ui.core.Green
import com.almarpa.rickandmortyapp.ui.core.bottomnavigation.BottomBarItem
import com.almarpa.rickandmortyapp.ui.core.bottomnavigation.BottomBarItem.Characters
import com.almarpa.rickandmortyapp.ui.core.bottomnavigation.BottomBarItem.Episodes
import com.almarpa.rickandmortyapp.ui.core.bottomnavigation.NavigationBottomWrapper
import org.jetbrains.compose.resources.painterResource
import rickandmortyapp.composeapp.generated.resources.Res
import rickandmortyapp.composeapp.generated.resources.ricktoolbar

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    val items = listOf(Episodes(), Characters())
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigation(items, navController) },
    ){ padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationBottomWrapper(navController, mainNavController)
        }
    }
}

@Composable
fun TopBar() {
    Box(modifier = Modifier.fillMaxWidth().background(BackgroundPrimaryColor), contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(Res.drawable.ricktoolbar),
            null,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun BottomNavigation(items: List<BottomBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = BackgroundSecondaryColor, contentColor = Green) {
        items.forEach { item ->
            NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                indicatorColor = Green,
                selectedIconColor = BackgroundTertiaryColor,
                unselectedIconColor = Green
            ),
                icon = item.icon,
                label = { Text(item.title, color = DefaultTextColor) },
                onClick = {
                    navController.navigate(route = item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true)
        }
    }
}
