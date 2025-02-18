package com.example.project_imc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Références aux vues
        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultText = findViewById<TextView>(R.id.resultText)
        val statusText = findViewById<TextView>(R.id.statusText)
        val statusImage = findViewById<ImageView>(R.id.statusImage)

        // Gestion automatique des marges
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calculateButton.setOnClickListener {
            val weightStr = weightInput.text.toString()
            val heightStr = heightInput.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                val weight = weightStr.toFloat()
                val height = heightStr.toFloat() / 100  // Conversion cm -> m

                if (height > 0) {
                    val imc = weight / (height * height)
                    resultText.text = "Votre IMC est: %.2f".format(imc)

                    // Déterminer le statut et l'image associée
                    val (status, imageRes) = when {
                        imc < 18.5 -> Pair("Insuffisance pondérale", R.drawable.maigre)
                        imc < 24.9 -> Pair("Poids normal", R.drawable.normal)
                        imc < 29.9 -> Pair("Surpoids", R.drawable.surpoids)
                        imc < 34.9 -> Pair("Obésité modérée", R.drawable.obese)
                        else -> Pair("Obésité sévère", R.drawable.t_obese)
                    }

                    statusText.text = status
                    statusImage.setImageResource(imageRes)
                    statusImage.visibility = ImageView.VISIBLE

                } else {
                    resultText.text = "Taille invalide"
                    statusText.text = ""
                    statusImage.visibility = ImageView.GONE
                }
            } else {
                resultText.text = "Veuillez entrer toutes les valeurs"
                statusText.text = ""
                statusImage.visibility = ImageView.GONE
            }
        }
    }
}
