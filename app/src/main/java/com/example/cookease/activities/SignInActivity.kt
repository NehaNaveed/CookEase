package com.example.cookease.activities

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cookease.R
import com.example.cookease.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInActivity : AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySignInBinding = ActivitySignInBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                Log.d("GoogleAuth", "account: $account")
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                signInWithGoogle(credential)

            } catch (e: ApiException) {
                Log.d("GoogleAuth", e.toString())
            }
        }

        binding.signInButton.setOnClickListener(){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            launcher.launch(googleSignInClient.signInIntent)


        }

    }

    private fun signInWithGoogle(credential: AuthCredential) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult: AuthResult = auth.signInWithCredential(credential).await()
                val user = authResult.user
                updateUI(user)
                Log.d("GoogleAuth", "User signed in: ${user?.email}")
            } catch (e: Exception) {
                Log.d("GoogleAuth", "Error signing in: ${e.message}")
            }

        }
    }
//
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }


    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
//            Toast.makeText(this, "Hello ${user.displayName}", Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("string",user.displayName)
            startActivity(Intent(this, HomeScreenActivity::class.java).putExtras(bundle))
            finish()
        }

    }

}


