package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FinalScoreFragment : Fragment() {

    companion object {
        // Argumentos que se pasarán para mostrar la puntuación final
        private const val ARG_SCORE = "score"
        private const val ARG_TOTAL_QUESTIONS = "totalQuestions"

        // Método estático para crear una nueva instancia del fragmento con la puntuación final
        fun newInstance(score: Int, totalQuestions: Int): FinalScoreFragment {
            val fragment = FinalScoreFragment()
            val args = Bundle().apply {
                // Pasar la puntuación y el total de preguntas al fragmento
                putInt(ARG_SCORE, score)
                putInt(ARG_TOTAL_QUESTIONS, totalQuestions)
            }
            fragment.arguments = args
            return fragment
        }
    }

    // Infla la vista y maneja la lógica para mostrar la puntuación final y reiniciar el juego
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_final_score, container, false)

        // Referencias a los elementos de la UI
        val scoreText = view.findViewById<TextView>(R.id.scoreText)
        val restartButton = view.findViewById<Button>(R.id.restartButton)

        // Recuperar los argumentos pasados (puntuación y total de preguntas) y mostrar el resultado
        arguments?.let {
            val score = it.getInt(ARG_SCORE)
            val totalQuestions = it.getInt(ARG_TOTAL_QUESTIONS)

            // Mostrar la puntuación final
            scoreText.text = "Tu puntuación es: $score de $totalQuestions"
        }

        // Configurar el botón "Reiniciar" para comenzar el juego nuevamente
        restartButton.setOnClickListener {
            (activity as? MainActivity)?.startGame() // Llamar al método para reiniciar el juego
        }

        return view
    }
}
