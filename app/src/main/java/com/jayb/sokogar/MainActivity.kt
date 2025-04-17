package com.jayb.sokogar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//ends

        val signinBtn= findViewById<Button>(R.id.signin)
        val signupBtn= findViewById<Button>(R.id.signup)
        val signoutBtn = findViewById<Button>(R.id.signout)
        val welcomeTv=findViewById<TextView>(R.id.welcome)

        signupBtn.setOnClickListener {
            startActivity(Intent(applicationContext,Signup::class.java))
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(applicationContext,SigninActivity::class.java))
        }



        val pref=getSharedPreferences("user_session", MODE_PRIVATE)
        val username=pref.getString("username","Guest")
        if (username != "Guest"){
            signinBtn.visibility=View.GONE
            signupBtn.visibility=View.GONE
            signoutBtn.visibility=View.VISIBLE
            welcomeTv.text="Welcome back, $username"
        }else{
            signinBtn.visibility=View.VISIBLE
            signupBtn.visibility=View.VISIBLE
            signoutBtn.visibility=View.GONE
        }
        signoutBtn.setOnClickListener {
            val pref =getSharedPreferences("user_session", MODE_PRIVATE)
            pref.edit().clear().apply()
            startActivity(Intent(applicationContext,MainActivity::class.java))

        }

        // Fetch recylerview and progressbar
        val progressBar = findViewById<ProgressBar>(R.id.progressbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //  Define your url
        val url = "https://jobsimba.pythonanywhere.com/api/get_product_details"

        //  Get the object of class APiHelper
        val helper = ApiHelper(applicationContext)

        //  We have a function called loadProducts, its inside ApiHelper
        //  Pass the 3 arguments which are url(api endpoint), recyclerview and progressbar
        helper.loadProducts(url, recyclerView, progressBar )


    }
}