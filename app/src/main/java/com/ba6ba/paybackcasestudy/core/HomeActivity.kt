package com.ba6ba.paybackcasestudy.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ba6ba.paybackcasestudy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}