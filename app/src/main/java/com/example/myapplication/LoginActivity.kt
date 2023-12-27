package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var password: EditText? = null
    private var btn: Button? = null
    private var txtRegister: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.edtEmail)
        password = findViewById(R.id.edtPassword)
        btn = findViewById(R.id.btnSignIn)
        txtRegister = findViewById(R.id.txtRegister)

        btn!!.setOnClickListener {
            validateAndLogin(mAuth)

        }

        txtRegister!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateAndLogin(mAuth: FirebaseAuth) {
        if (email?.text!!.isNotEmpty() && password?.text!!.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email?.text.toString(), password?.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT)
                            .show()
                        updateUI(null)
                    }
                }
        } else {
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is signed out
            // Update UI accordingly
        }
    }
}