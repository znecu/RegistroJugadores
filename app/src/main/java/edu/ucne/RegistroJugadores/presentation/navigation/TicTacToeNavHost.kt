package edu.ucne.RegistroJugadores.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.RegistroJugadores.presentation.jugadores.JugadorScreen
import edu.ucne.RegistroJugadores.presentation.logros.LogroScreen
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroViewModel
import edu.ucne.RegistroJugadores.presentation.logros.list.ListLogroViewModel
import edu.ucne.RegistroJugadores.presentation.tictactoe.TicTacToeScreen
import kotlinx.coroutines.launch

@Composable
fun TicTacToeNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val editLogroViewModel: EditLogroViewModel = hiltViewModel()
    val listLogroViewModel: ListLogroViewModel = hiltViewModel()

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.TicTacToe
        ) {
            composable<Screen.Jugadores> {
                JugadorScreen()
            }

//            composable<Screen.Partidas> {}

            composable<Screen.Logros> {
                LogroScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    editLogroViewModel,
                    listLogroViewModel
                )
            }

            composable<Screen.TicTacToe> {
                TicTacToeScreen()
            }
        }
    }
}