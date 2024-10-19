package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class WelcomeFragment : Fragment() {

    // Inflamos el layout para este fragmento cuando se crea la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    // Configuramos el comportamiento del botón de inicio una vez que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encontramos el botón de inicio dentro del layout
        val startButton: Button = view.findViewById(R.id.startButton)

        // Cuando el botón se presiona, comenzamos el juego llamando al método startGame en la MainActivity
        startButton.setOnClickListener {
            (activity as MainActivity).startGame() // Llama al método startGame en MainActivity
        }
    }
}
