package com.example.cookease.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookease.R
import com.example.cookease.activities.MealsScreenActivity.Companion.KEY_CATEGORY
import com.example.cookease.adapter.CategoryAdapter
import com.example.cookease.databinding.ActivityHomeBinding
import com.example.cookease.setInProgress
import com.example.cookease.viewmodel.CategoriesViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private val catViewModel by lazy {
         ViewModelProvider(this)[CategoriesViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val bundle = intent.extras

        val fullText = getString(R.string.find_text)
        val wordToColor = "recipes"

        val spannableString = SpannableString(fullText)
        val startIndex =
            fullText.indexOf(wordToColor)              // Start and end index of the word to color
        val endIndex = startIndex + wordToColor.length

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#CC5500")),   // Change to your desired color
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.ITALIC),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.headerText.text = spannableString

        val user = bundle?.getString(KEY_CATEGORY)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CategoryAdapter(
            catList = emptyList(), onItemClick = { category ->

                val catName = category.strCategory
                val sendBundle = Bundle()
                sendBundle.putString(
                    KEY_CATEGORY,
                    catName
                )          //sending category name to meals screen activity
                startActivity(
                    Intent(
                        this@HomeScreenActivity,
                        MealsScreenActivity::class.java
                    ).putExtras(sendBundle)
                )
            }

        )
        recyclerView.adapter = adapter


        lifecycleScope.launch {                                    // coroutine function call
            catViewModel.getData()
        }
        catViewModel.categoryList.observe(this) { categories ->
            categories.let { adapter.updateCategories(it) }
        }

        catViewModel.inProgress.observe(this) { inProgress ->
            binding.progressBar.setInProgress(inProgress)
        }
        binding.signOut.setOnClickListener {
            // Sign out from Firebase
            Firebase.auth.signOut()

            // Sign out from Google Sign-In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut().addOnCompleteListener {
                Toast.makeText(this, "Signed out of $user", Toast.LENGTH_LONG).show()

                // After signing out, start the SignInActivity and finish the current one
                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }


        binding.searchButton.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            catViewModel.filterCategories(searchText)
        }
    }


}

