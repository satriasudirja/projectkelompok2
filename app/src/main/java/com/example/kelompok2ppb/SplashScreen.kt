package com.example.kelompok2ppb

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Referensi elemen
        val largeLogo = findViewById<View>(R.id.largeLogo)
        val smallLogoWithText = findViewById<View>(R.id.smallLogoWithText)

        // Awal: Sembunyikan elemen kecil
        smallLogoWithText.alpha = 0f

        // Animasi untuk logo besar (fade in)
        val largeLogoAnimator = ObjectAnimator.ofFloat(largeLogo, "alpha", 0f, 1f)
        largeLogoAnimator.duration = 1000 // Durasi 1 detik

        // Animasi untuk logo kecil + teks (fade in setelah logo besar)
        val smallLogoAnimator = ObjectAnimator.ofFloat(smallLogoWithText, "alpha", 0f, 1f)
        smallLogoAnimator.duration = 1000 // Durasi 1 detik
        smallLogoAnimator.startDelay = 1000 // Mulai setelah 1 detik

        // Set animasi
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(largeLogoAnimator, smallLogoAnimator)
        animatorSet.start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Menutup SplashScreen agar tidak kembali dengan tombol Back
        }, 2000) // Total waktu (1 detik fade-in logo besar + 1 detik fade-in logo kecil)
    }
    }
