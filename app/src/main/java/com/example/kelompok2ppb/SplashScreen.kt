package com.example.kelompok2ppb

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kelompok2ppb.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

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

        // Tunggu animasi selesai lalu cek status login
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000) // Total waktu animasi (1 detik fade-in logo besar + 1 detik fade-in logo kecil)
    }

    private fun checkLoginStatus() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // Jika pengguna sudah login, pindah ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Jika belum login, pindah ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Menutup SplashScreen agar tidak kembali dengan tombol Back
    }
}
