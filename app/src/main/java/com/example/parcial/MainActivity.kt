package com.example.parcial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.parcial.data.Question

class MainActivity : AppCompatActivity() {

    // Índice para llevar la cuenta de la pregunta actual
    private var currentQuestionIndex = 0
    // Variable para almacenar la puntuación del usuario
    private var score = 0

    // Lista de preguntas del juego
    private val questions = listOf(
        Question("¿Cuál es la capital de Francia?", listOf("Londres", "Berlín", "París", "Madrid"), 2, "La capital de Francia es París."),
        Question("¿En qué año comenzó la Segunda Guerra Mundial?", listOf("1939", "1941", "1945", "1938"), 0, "La Segunda Guerra Mundial comenzó en 1939."),
        Question("¿Quién pintó la Mona Lisa?", listOf("Van Gogh", "Da Vinci", "Picasso", "Rembrandt"), 1, "La Mona Lisa fue pintada por Leonardo Da Vinci."),
        Question("¿Cuál es el océano más grande del mundo?", listOf("Atlántico", "Índico", "Ártico", "Pacífico"), 3, "El océano más grande es el Pacífico."),
        Question("¿Quién escribió 'Cien años de soledad'?", listOf("Gabriel García Márquez", "Mario Vargas Llosa", "Julio Cortázar", "Pablo Neruda"), 0, "Cien años de soledad fue escrito por Gabriel García Márquez."),
        Question("¿Cuál es el país más grande del mundo?", listOf("Rusia", "Canadá", "China", "Estados Unidos"), 0, "El país más grande del mundo es Rusia."),
        Question("¿Qué es la fotosíntesis?", listOf("Proceso de respiración", "Proceso de producción de energía en plantas", "Proceso de reproducción", "Proceso de nutrición"), 1, "La fotosíntesis es el proceso mediante el cual las plantas producen su energía."),
        Question("¿Cuál es la moneda de Japón?", listOf("Dólar", "Yen", "Euro", "Won"), 1, "La moneda de Japón es el Yen."),
        Question("¿Quién fue Albert Einstein?", listOf("Un físico", "Un químico", "Un biólogo", "Un matemático"), 0, "Albert Einstein fue un físico famoso por su teoría de la relatividad."),
        Question("¿Cuál es el continente más pequeño?", listOf("Asia", "África", "Oceanía", "Europa"), 2, "El continente más pequeño es Oceanía.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Iniciar el servicio de música para tener música de fondo en el juego
        Intent(this, MusicService::class.java).also { startService(it) }

        // Cargar el fragmento de bienvenida al iniciar la actividad, si no hay un estado guardado
        if (savedInstanceState == null) {
            showWelcomeFragment()
        }
    }

    // Mostrar el fragmento de bienvenida
    fun showWelcomeFragment() {
        replaceFragment(WelcomeFragment())
    }

    // Iniciar el juego: reiniciar la puntuación e índice de preguntas
    fun startGame() {
        currentQuestionIndex = 0
        score = 0
        showNextQuestion()
    }

    // Mostrar la siguiente pregunta o el fragmento final si ya se respondieron todas las preguntas
    fun showNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            // Mostrar la pregunta actual
            replaceFragment(QuestionFragment.newInstance(questions[currentQuestionIndex]))
        } else {
            // Si se terminaron las preguntas, mostrar el fragmento con la puntuación final
            showFinalScoreFragment()
        }
    }

    // Pasar a la siguiente pregunta incrementando el índice
    fun moveToNextQuestion() {
        currentQuestionIndex++
        showNextQuestion()
    }

    // Manejar la respuesta del usuario: si es correcta, aumentar la puntuación, luego mostrar la explicación
    fun submitAnswer(isCorrect: Boolean, explanation: String) {
        if (isCorrect) score++ // Aumentar la puntuación si la respuesta es correcta
        showAnswerFragment(isCorrect, explanation) // Mostrar el fragmento de respuesta con la retroalimentación
    }

    // Mostrar el fragmento que indica si la respuesta fue correcta o incorrecta
    private fun showAnswerFragment(correct: Boolean, explanation: String) {
        val answerFragment = AnswerFragment.newInstance(correct, explanation) // Crear una nueva instancia del fragmento con los datos correspondientes
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, answerFragment) // Reemplazar el fragmento actual con el de respuesta
            .addToBackStack(null) // Añadir a la pila de retroceso para poder volver a la pregunta si es necesario
            .commit()
    }

    // Mostrar el fragmento con la puntuación final cuando el usuario termina el juego
    fun showFinalScoreFragment() {
        replaceFragment(FinalScoreFragment.newInstance(score, questions.size)) // Crear el fragmento con la puntuación final
    }

    // Reemplazar el fragmento actual por otro, lo utilizamos en varias partes del código
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment) // Cambiar el fragmento en el contenedor de la actividad
            .commit()
    }
}
