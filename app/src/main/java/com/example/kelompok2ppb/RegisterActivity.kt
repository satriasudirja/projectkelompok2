package com.example.kelompok2ppb.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kelompok2ppb.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Tombol Kirim Verifikasi Email
        binding.btnSendOtp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val noInduk = binding.etNoInduk.text.toString().trim()
            val nama = binding.etNama.text.toString().trim()

            if (validateInputs(email, noInduk, nama)) {
                sendEmailVerification(email)
            }
        }

        // Tombol Registrasi
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val noInduk = binding.etNoInduk.text.toString().trim()
            val nama = binding.etNama.text.toString().trim()

            if (validateInputs(email, noInduk, nama) && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Upload Foto
        binding.btnUploadFoto.setOnClickListener {
            Toast.makeText(this, "Fitur upload foto belum diimplementasikan", Toast.LENGTH_SHORT).show()
            // Tambahkan logika upload foto di sini (jika ada backend/cloud storage)
        }
    }

    /**
     * Fungsi untuk memvalidasi input user
     */
    private fun validateInputs(email: String, noInduk: String, nama: String): Boolean {
        if (email.isEmpty() || noInduk.isEmpty() || nama.isEmpty()) {
            Toast.makeText(this, "Email, No Induk, dan Nama harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (noInduk.length < 5) {
            Toast.makeText(this, "No Induk minimal 5 karakter!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * Fungsi untuk mengirim email verifikasi
     */
    private fun sendEmailVerification(email: String) {
        auth.createUserWithEmailAndPassword(email, "temporaryPassword123")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Email verifikasi telah dikirim. Silakan cek email Anda.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Gagal mengirim email verifikasi: ${emailTask.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Gagal membuat akun sementara: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /**
     * Fungsi untuk registrasi pengguna baru setelah email diverifikasi
     */
    private fun registerUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, "temporaryPassword123")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { registerTask ->
                                if (registerTask.isSuccessful) {
                                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT)
                                        .show()

                                    // Arahkan ke LoginActivity
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Gagal registrasi: ${registerTask.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Verifikasi email Anda terlebih dahulu!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Gagal login untuk proses verifikasi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
