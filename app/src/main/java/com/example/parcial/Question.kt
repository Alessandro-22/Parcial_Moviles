package com.example.parcial.data

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val question: String,            // Pregunta que se va a mostrar
    val options: List<String>,       // Lista de opciones disponibles para la respuesta
    val correctAnswerIndex: Int,     // Índice de la respuesta correcta dentro de la lista de opciones
    val explanation: String          // Explicación detallada de la respuesta correcta
) : Parcelable {
    // Constructor secundario que permite recrear el objeto desde un Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",              // Leer la pregunta del Parcel
        parcel.createStringArrayList() ?: emptyList(), // Leer las opciones como un ArrayList de Strings
        parcel.readInt(),                       // Leer el índice de la respuesta correcta
        parcel.readString() ?: ""               // Leer la explicación
    )

    // Método para escribir los datos del objeto en un Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)            // Guardar la pregunta en el Parcel
        parcel.writeStringList(options)         // Guardar las opciones en el Parcel
        parcel.writeInt(correctAnswerIndex)     // Guardar el índice de la respuesta correcta
        parcel.writeString(explanation)         // Guardar la explicación en el Parcel
    }

    // Este método normalmente devuelve 0, no es usado en la mayoría de los casos
    override fun describeContents(): Int {
        return 0
    }

    // Objeto CREATOR que ayuda a recrear los objetos de tipo Question desde un Parcel
    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            // Llama al constructor secundario para recrear el objeto desde un Parcel
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            // Crea un array de objetos Question con el tamaño especificado
            return arrayOfNulls(size)
        }
    }
}
