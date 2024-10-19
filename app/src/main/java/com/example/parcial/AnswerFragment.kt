package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnswerFragment : Fragment() {
    companion object {
        private const val ARG_IS_CORRECT = "isCorrect" // Argumento para saber si la respuesta fue correcta
        private const val ARG_EXPLANATION = "explanation" // Argumento para pasar la explicación de la respuesta

        // Método para crear una nueva instancia del fragmento con los argumentos necesarios
        fun newInstance(isCorrect: Boolean, explanation: String): AnswerFragment {
            val fragment = AnswerFragment()
            val args = Bundle()
            args.putBoolean(ARG_IS_CORRECT, isCorrect) // Pasa si la respuesta es correcta
            args.putString(ARG_EXPLANATION, explanation) // Pasa la explicación de la respuesta
            fragment.arguments = args
            return fragment
        }
    }

    // Infla la vista y maneja la lógica de mostrar la respuesta y la explicación
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container, false)

        // Referencias a los elementos de la UI
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        val explanationTextView = view.findViewById<TextView>(R.id.explanationTextView)
        val nextButton = view.findViewById<Button>(R.id.nextButton)

        // Obtiene los argumentos pasados y los utiliza para mostrar el resultado y la explicación
        val isCorrect = arguments?.getBoolean(ARG_IS_CORRECT) ?: false
        val explanation = arguments?.getString(ARG_EXPLANATION) ?: ""

        // Mostrar si la respuesta fue correcta o incorrecta
        resultTextView.text = if (isCorrect) "¡Correcto!" else "Incorrecto"
        explanationTextView.text = explanation // Mostrar la explicación de la respuesta correcta o incorrecta

        // Configurar el botón "Next" para avanzar a la siguiente pregunta
        nextButton.setOnClickListener {
            (activity as MainActivity).moveToNextQuestion() // Llamar al método para ir a la siguiente pregunta
        }

        return view
    }
}
