package com.example.placesintheworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.placesintheworld.ui.theme.PlacesInTheWorldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlacesInTheWorldTheme {
                PlacesApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesApp() {
    val navController = rememberNavController()
    var currentViewType by remember { mutableStateOf(ViewType.STAGGERED_GRID) }
    var showMenu by remember { mutableStateOf(false) }

    // Usa valores por defecto del tema Material3
    val defaultTopBarColor = colorScheme.primary
    val defaultTopBarTitleColor = colorScheme.onPrimary

    var topBarColor by remember { mutableStateOf(defaultTopBarColor) }
    var topBarTitleColor by remember { mutableStateOf(defaultTopBarTitleColor) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        if (navBackStackEntry?.destination?.route == "home") {
            // Restablece a los valores por defecto
            topBarColor = defaultTopBarColor
            topBarTitleColor = defaultTopBarTitleColor
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor,
                    titleContentColor = topBarTitleColor,
                    actionIconContentColor = topBarTitleColor
                ),
                title = { Text("PlacesInTheWorld") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menú de navegación",
                            tint = topBarTitleColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = topBarTitleColor
                        )
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("LazyColumn") },
                            onClick = {
                                currentViewType = ViewType.LAZY_COLUMN
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Face, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("StaggeredGrid") },
                            onClick = {
                                currentViewType = ViewType.STAGGERED_GRID
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Home, contentDescription = null)
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack("home", inclusive = false) },
                containerColor = Color(0xFF6AB7FF)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Inicio")
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        paddingValues = paddingValues,
                        onPlaceClick = { place -> navController.navigate("detail/${place.id}") },
                        currentViewType = currentViewType
                    )
                }
                composable("detail/{placeId}") { backStackEntry ->
                    val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull() ?: 0
                    val place =
                        getSamplePlaces().find { it.id == placeId } ?: getSamplePlaces().first()
                    DetailScreen(
                        place = place,
                        onColorsExtracted = { vibrantColor, darkVibrantColor ->
                            topBarColor = vibrantColor
                            topBarTitleColor =
                                if (ColorUtils.calculateLuminance(vibrantColor.toArgb()) < 0.5) Color.White else Color.Black
                        }
                    )
                }
            }
        }
    }
}

enum class ViewType {
    LAZY_COLUMN, STAGGERED_GRID
}