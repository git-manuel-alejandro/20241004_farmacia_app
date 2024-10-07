package com.iacc.manuelroa_20241004

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencias = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE)
        // obtiene el valor de "nombre_usuario"
        val nombreGuardado = preferencias.getString("nombre_usuario", null)

        // verifica si "nombreGuardado" tiene un valor
        if (nombreGuardado != null) {
            // si tiene valor dirige a la vista de Bienvenida
            val intent = Intent(this, BienvenidaActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // caso contrario, renderiza el activity main, el cual tiene el formulario
            setContentView(R.layout.activity_main)

            val campoNombre = findViewById<EditText>(R.id.campo_nombre)
            val botonGuardar = findViewById<Button>(R.id.boton_guardar)

            botonGuardar.setOnClickListener {
                val nombreUsuario = campoNombre.text.toString()
                if (nombreUsuario.isNotEmpty()) {
                    val editor = preferencias.edit()
                    editor.putString("nombre_usuario", nombreUsuario)
                    editor.apply()

                    val intent = Intent(this, BienvenidaActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
