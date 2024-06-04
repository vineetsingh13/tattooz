package com.example.tattooz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tattooz.adapters.ListAdapter
import com.example.tattooz.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var assetUrls: MutableList<String>
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        assetUrls=intent.getStringArrayListExtra("assetUrls")!!.toMutableList()
        //Log.d("URLS",assetUrls.toString())

        binding.gridView.layoutManager=LinearLayoutManager(this)
        val lsAdapter=ListAdapter(assetUrls,this)
        binding.gridView.adapter=lsAdapter

    }

}