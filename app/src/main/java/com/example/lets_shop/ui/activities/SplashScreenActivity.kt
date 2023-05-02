package com.example.lets_shop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.lets_shop.R

class SplashScreenActivity : AppCompatActivity() {
    val activity: MainActivity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
        }, 2000)
    }

}
