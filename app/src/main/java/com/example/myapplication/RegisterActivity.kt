package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private var name: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var confirmPassword: EditText? = null
    private var btn: Button? = null
    private var txtRegister: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById(R.id.edtName)
        email = findViewById(R.id.edtEmail)
        password = findViewById(R.id.edtPassword)
        confirmPassword = findViewById(R.id.edtConfirmPassword)
        btn = findViewById(R.id.btnSignUp)
        txtRegister = findViewById(R.id.txtRegister)

        FirebaseApp.initializeApp(this)

        val mAuth = FirebaseAuth.getInstance()

        btn!!.setOnClickListener {
            validateAndLogin(mAuth)
        }

        txtRegister!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateAndLogin(mAuth: FirebaseAuth) {
        if(name?.text!!.isNotEmpty() && email?.text!!.isNotEmpty() && password?.text!!.isNotEmpty() && confirmPassword?.text!!.isNotEmpty()) {
            if (password?.text.toString() == confirmPassword?.text.toString()) {
                mAuth.createUserWithEmailAndPassword(
                    email?.text.toString(),
                    password?.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign up success, update UI with the signed-in user's information
                            val user = mAuth.currentUser
                            // You can save additional user information like name to the Firebase Database here
                            updateUI(user)
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT)
                                .show()
                            updateUI(null)
                        }
                    }
            }else{
                Toast.makeText(this, "Password Mismatch!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in
            // Redirect to the main activity or perform other actions
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is signed out
            // Update UI accordingly
        }
    }

}