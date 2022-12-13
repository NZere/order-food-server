package project.app.space.team7cafeserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.button_login)
        loginBtn.setOnClickListener {
            performLogin()
        }

    }

    private fun performLogin() {
        // Getting input from the user
        val email = findViewById<EditText>(R.id.input_email)
        val password = findViewById<EditText>(R.id.input_password)

        // Null check on inputs
        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Enter all attributes!", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the Home Activity
                    //check orders

                    val users = database.getReference("User")
                    users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
                        if (it.exists()) {
                            var userType = it.child("userType").value.toString()
                            if (userType == "staff") {
//                                val intent = Intent(this, CategoryMenuActivity::class.java)
//                                startActivity(intent)

                                Toast.makeText(
                                    baseContext, "Authentication successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    baseContext, "Not privileged user",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext, "error",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

//
//    private fun checkOrders():Boolean{
//        val users = database.getReference("User")
//        var not_ordered=false
//        users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
//            if (it.exists()) {
////                Toast.makeText(baseContext, "here",
////                    Toast.LENGTH_SHORT).show()
//                users.child(auth.currentUser?.uid!!).child("Orders").addValueEventListener(object:
//                    ValueEventListener {
//
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        for (ds in snapshot.children){
//                            if (ds.child("is_ordered").value == false){
//                                not_ordered=true
//                            }
//                        }
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                    }
//                })
//            }
//        }.addOnFailureListener{
//            Toast.makeText(baseContext, "order number failed",
//                Toast.LENGTH_SHORT).show()
//        }
//
//        return not_ordered
//
//    }
}