package com.example.parcial

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.parcial.data.Question

class QuestionFragment : Fragment() {

    private lateinit var question: Question                 // Pregunta actual que se mostrará en el fragmento
    private lateinit var timerTextView: TextView            // Vista para mostrar el tiempo restante
    private var timeRemaining: Int = 10                     // Tiempo inicial (10 segundos)
    private lateinit var handler: Handler                   // Manejador para el cronómetro
    private lateinit var runnable: Runnable                 // Tarea que se ejecuta cada segundo para actualizar el tiempo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la pregunta pasada como argumento
        question = arguments?.getParcelable(ARG_QUESTION)!!
        timerTextView = view.findViewById(R.id.timerTextView)

        // Inicializar las vistas
        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val optionsRadioGroup: RadioGroup = view.findViewById(R.id.optionsRadioGroup)
        val submitButton: Button = view.findViewById(R.id.submitButton)

        // Establecer el texto de la pregunta
        questionTextView.text = question.question

        // Crear botones de opciones dinámicamente
        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(requireContext())
            radioButton.text = option
            radioButton.id = index
            optionsRadioGroup.addView(radioButton)
        }

        // Iniciar el temporizador
        startTimer()

        // Configurar el botón de envío para enviar la respuesta seleccionada
        submitButton.setOnClickListener {
            stopTimer() // Detener el cronómetro cuando se envía la respuesta
            val selectedOptionIndex = optionsRadioGroup.indexOfChild(view.findViewById(optionsRadioGroup.checkedRadioButtonId))
            val isCorrect = selectedOptionIndex == question.correctAnswerIndex // Verificar si la respuesta es correcta
            val explanation = question.explanation
            (activity as MainActivity).submitAnswer(isCorrect, explanation) // Enviar la respuesta a la actividad
        }
    }

    // Iniciar el temporizador para contar los 10 segundos
    private fun startTimer() {
        timeRemaining = 10 // Reiniciar el tiempo a 10 segundos
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (timeRemaining > 0) {
                    // Actualizar el texto del temporizador
                    timerTextView.text = "Tiempo restante: $timeRemaining"
                    timeRemaining--
                    handler.postDelayed(this, 1000) // Actualizar cada segundo
                } else {
                    // Si se acaba el tiempo, enviar la respuesta como incorrecta
                    stopTimer()
                    (activity as MainActivity).submitAnswer(
                        false,
                        "Se acabó el tiempo. La respuesta correcta es: ${question.options[question.correctAnswerIndex]}"
                    )
                }
            }
        }
        handler.post(runnable) // Iniciar el cronómetro
    }

    // Detener el cronómetro
    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    // Asegurarse de detener el cronómetro al destruir la vista del fragmento
    override fun onDestroyView() {
        super.onDestroyView()
        stopTimer()
    }

    companion object {
        private const val ARG_QUESTION = "question" // Clave para pasar la pregunta como argumento

        // Método para crear una nueva instancia de QuestionFragment con una pregunta
        fun newInstance(question: Question): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle().apply {
                putParcelable(ARG_QUESTION, question) // Pasar la pregunta como argumento
            }
            fragment.arguments = args
            return fragment
        }
    }
}
