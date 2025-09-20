package edu.ucne.RegistroJugadores.di
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroJugadores.data.database.JugadorDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideJugadorDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JugadorDb::class.java,
            "Jugador.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideJugadorDao(jugadorDb: JugadorDb) = jugadorDb.jugadorDao()

    @Provides
    fun providePartidaDao(jugadorDb: JugadorDb) = jugadorDb.partidaDao()

    @Provides
    fun provideLogroDao(jugadorDb: JugadorDb) = jugadorDb.LogroDao()


}