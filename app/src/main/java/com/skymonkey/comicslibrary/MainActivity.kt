package com.skymonkey.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skymonkey.comicslibrary.ui.theme.ComicsLibraryTheme
import com.skymonkey.comicslibrary.view.CharactersBottomNav
import com.skymonkey.comicslibrary.view.CollectionScreen
import com.skymonkey.comicslibrary.view.LibraryScreen
import com.skymonkey.comicslibrary.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    data object Library: Destination("library")
    data object Collection: Destination("collection")
    data object CharacterDetail: Destination("character/{id}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryViewModel by viewModels<LibraryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController = navController, viewModel = libraryViewModel)
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController, viewModel: LibraryViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { snackbarHostState },
        bottomBar = { CharactersBottomNav(navController = navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(
                    navHostController = navController,
                    viewModel = viewModel,
                    paddingValues = paddingValues
                )
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->
                // TODO: navigate to character detail
            }
        }
    }
}