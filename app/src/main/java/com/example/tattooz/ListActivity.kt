package com.example.tattooz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ListActivity : AppCompatActivity() {

    private lateinit var assetUrls: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        assetUrls=intent.getStringArrayListExtra("assetUrls")!!.toMutableList()
        //Log.d("URLS",assetUrls.toString())
    }
}