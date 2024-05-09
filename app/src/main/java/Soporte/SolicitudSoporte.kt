package Soporte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tfgrubensaez.R
import com.example.tfgrubensaez.databinding.ActivitySolicitudSporteBinding
import com.google.firebase.firestore.FirebaseFirestore

class SolicitudSoporte : AppCompatActivity() {
    lateinit var binding: ActivitySolicitudSporteBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud_sporte)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_solicitud_sporte)

        binding.enviarTextoButton.setOnClickListener {
            val texto = binding.editTextTexto.text.toString().trim()

            if (texto.isNotEmpty()) {
                guardarTextoEnFirebase(texto)
            } else {
                Toast.makeText(this, "Por favor, ingresa un texto antes de enviar", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun guardarTextoEnFirebase(texto: String) {

        db.collection("Soporte")
            .add(mapOf("TextoSoporte" to texto))
            .addOnSuccessListener {
                Toast.makeText(this, "Enviado a soporte", Toast.LENGTH_SHORT).show()
                binding.editTextTexto.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al enviar la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}