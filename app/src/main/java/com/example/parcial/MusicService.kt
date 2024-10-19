package com.example.parcial

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    // Inicialización del servicio y del reproductor de música
    override fun onCreate() {
        super.onCreate()
        // Crear y configurar el MediaPlayer para reproducir el archivo MP3
        mediaPlayer = MediaPlayer.create(this, R.raw.tuangelito)
        mediaPlayer.isLooping = true // Configurar para que la música se repita indefinidamente
        mediaPlayer.start() // Iniciar la reproducción de música
    }

    // Se asegura que el servicio continúe ejecutándose incluso si es destruido accidentalmente
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // El servicio se reinicia automáticamente si el sistema lo detiene
    }

    // Este método no es relevante ya que no se necesita vinculación con una actividad
    override fun onBind(intent: Intent?): IBinder? {
        return null // El servicio no está diseñado para estar vinculado a una actividad
    }

    // Limpieza y liberación de recursos al destruir el servicio
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop() // Detener la música
        mediaPlayer.release() // Liberar los recursos del MediaPlayer
    }
}
