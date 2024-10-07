package com.iacc.manuelroa_20241004

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

class BienvenidaActivity : AppCompatActivity() {

    private lateinit var botonCerrarSesion: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocalesAdapter
    private lateinit var mensajeObteniendoInformacion: TextView
    private val listaLocales = mutableListOf<Local>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        botonCerrarSesion = findViewById(R.id.boton_cerrar_sesion)
        recyclerView = findViewById(R.id.recycler_view)
        mensajeObteniendoInformacion = findViewById(R.id.mensaje_obteniendo_informacion)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocalesAdapter(listaLocales)
        recyclerView.adapter = adapter


        sharedPreferences = getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE)


        val nombreUsuario = sharedPreferences.getString("nombre_usuario", "").orEmpty().toUpperCase(
            Locale.ROOT)
        Log.d("BienvenidaActivity", "Nombre de usuario: $nombreUsuario")


        val mensajeBienvenida = findViewById<TextView>(R.id.mensaje_bienvenida)
        mensajeBienvenida.text = "Bienvenido $nombreUsuario"

        // Obtener informacion de la API
        ObtenerDatosAPI().execute()


        botonCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {

        sharedPreferences.edit().clear().apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    inner class ObtenerDatosAPI : AsyncTask<Void, Void, String?>() {
        override fun onPreExecute() {
            mensajeObteniendoInformacion.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
        override fun doInBackground(vararg params: Void?): String? {
            var result: String? = null
            try {
                val url = URL("https://midas.minsal.cl/farmacia_v2/WS/getLocalesTurnos.php")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("BienvenidaActivity", "Conexión exitosa con código: $responseCode")
                    val inputStream = connection.inputStream
                    result = inputStream.bufferedReader().use { it.readText() }
                    Log.d("BienvenidaActivity", "Respuesta de la API: $result")

                } else {
                    Log.e("BienvenidaActivity", "Error en la conexión, código de respuesta: $responseCode")
                }

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("BienvenidaActivity", "Error en doInBackground: ${e.message}")
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            mensajeObteniendoInformacion.visibility = View.GONE

            if (result != null) {
                try {
                    mostrarDatos(result)
                } catch (e: Exception) {
                    Log.e("BienvenidaActivity", "Error al parsear los datos: ${e.message}")
                    Toast.makeText(this@BienvenidaActivity, "Error al mostrar los datos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("BienvenidaActivity", "Error: el resultado de la API es nulo")
                Toast.makeText(this@BienvenidaActivity, "Error al obtener los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDatos(datos: String) {
        try {
            val jsonArray = JSONArray(datos)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val local = Local(
                    local_nombre = jsonObject.getString("local_nombre"),
                    comuna_nombre = jsonObject.getString("comuna_nombre"),
                    local_direccion = jsonObject.getString("local_direccion"),
                    local_telefono = jsonObject.getString("local_telefono")
                )
                listaLocales.add(local)
            }
            Log.d("BienvenidaActivity", "Datos parseados correctamente. Tamaño de la lista: ${listaLocales.size}")

            adapter.notifyDataSetChanged()
            recyclerView.visibility = View.VISIBLE
        } catch (e: Exception) {
            Log.e("BienvenidaActivity", "Error al mostrar los datos: ${e.message}")
        }
    }
}
