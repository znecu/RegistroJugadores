package edu.ucne.composedemo.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.RegistroJugadores.presentation.jugadores.JugadorScreen
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.navigation.DrawerMenu
import edu.ucne.RegistroJugadores.presentation.navigation.Screen
import edu.ucne.RegistroJugadores.presentation.partidas.PartidaScreen
import edu.ucne.RegistroJugadores.presentation.partidas.edit.EditPartidaViewModel
import edu.ucne.RegistroJugadores.presentation.partidas.list.ListPartidaViewModel
import edu.ucne.RegistroJugadores.presentation.tictactoe.TicTacToeScreen
import kotlinx.coroutines.launch

@Composable
fun TicTacToeNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val editJugadorViewModel: EditJugadorViewModel = hiltViewModel()
    val listJugadorViewModel: ListJugadorViewModel = hiltViewModel()

    val editPartidaViewModel: EditPartidaViewModel = hiltViewModel()
    val listPartidaViewModel: ListPartidaViewModel = hiltViewModel()

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.Jugadores
        ) {
            composable<Screen.Jugadores> {
                JugadorScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    editJugadorViewModel,
                    listJugadorViewModel
                )
            }
            composable<Screen.Partidas> {
                PartidaScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    editPartidaViewModel,
                    listPartidaViewModel
                )
            }
            composable<Screen.TicTacToe> {
                TicTacToeScreen()
            }
        }
    }
}