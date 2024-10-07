package com.iacc.manuelroa_20241004

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Usar un Handler para retrasar la transición a la siguiente actividad
        Handler(Looper.getMainLooper()).postDelayed({
            // Iniciar la BienvenidaActivity después del splash
            val intent = Intent(this@SplashActivity, BienvenidaActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad del splash para que no se regrese a esta pantalla
        }, 3000) // 3000 milisegundos = 3 segundos
    }
}
