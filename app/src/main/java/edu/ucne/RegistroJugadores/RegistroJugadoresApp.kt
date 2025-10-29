package edu.ucne.RegistroJugadores

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import edu.ucne.RegistroJugadores.data.remote.worker.MyWorkerFactory
import edu.ucne.RegistroJugadores.data.remote.worker.SyncWorker
import javax.inject.Inject

@HiltAndroidApp
class RegistroJugadoresApp() : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

