package com.example.tattooz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tattooz.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var quality=""
    var aspect=""
    var style=""
    var quantity=""

    private var assetUrls: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterQuality = ArrayAdapter.createFromResource(
            this,
            R.array.quality,
            android.R.layout.simple_spinner_item
        )
        adapterQuality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.qualitySpinner.adapter=adapterQuality

        binding.qualitySpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                quality=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val adapterAspectRatio = ArrayAdapter.createFromResource(
            this,
            R.array.aspect_ratio,
            android.R.layout.simple_spinner_item
        )
        adapterAspectRatio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.aspectSpinner.adapter=adapterAspectRatio

        binding.aspectSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                aspect=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val adapterStyle = ArrayAdapter.createFromResource(
            this,
            R.array.style,
            android.R.layout.simple_spinner_item
        )
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.styleSpinner.adapter=adapterStyle

        binding.styleSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                style=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val adapterQuantity = ArrayAdapter.createFromResource(
            this,
            R.array.quantity,
            android.R.layout.simple_spinner_item
        )
        adapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.quantitySpinner.adapter=adapterQuantity

        binding.quantitySpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                quantity=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.generateButton.setOnClickListener {

            //Log.d("value",quantity+style+aspect+quality)

            if(binding.promptText.text!!.isNotEmpty()){


                binding.generateButton.isClickable=false

                lifecycleScope.launch(Dispatchers.IO) {
                    try {

                        val req=requestData(
                            prompt = binding.promptText.text.toString(),
                            aspectRatio = aspect,
                            samples = quantity.toInt(),
                            quality = quality,
                            style = style
                        )

                        val time= measureTimeMillis {
                            async {
                                getResponse(req)
                                //Log.d("value",req.toString())
                            }
                        }

                    }finally {

                        withContext(Dispatchers.Main) {
                            binding.generateButton.isClickable = true
                        }
                    }
                }
            }else{

                Toast.makeText(this,"enter text", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        binding.promptText.text!!.clear()
    }


    private fun getResponse(query: requestData){

        runOnUiThread {
            binding.progressbar.visibility= View.VISIBLE
        }
        val service=Service.buildService(ApiInterface::class.java)


        val call=service.respone(query)


        call.enqueue(object : Callback<responseData> {
            override fun onResponse(call: Call<responseData>, response: Response<responseData>) {

                if(response.code()==200){
                    //println(response.body().toString())
                    Log.d("RESPONSE",response.body().toString())

                    val responseData = response.body() ?: return // Handle potential null response

                    // Extract asset URLs from the data array
                    assetUrls.clear() // Clear previous URLs before adding new ones
                    val dataArray = responseData.data

                    for (dataItem in dataArray) {
                        val assetUrl = dataItem.assetUrl
                        assetUrls.add(assetUrl)
                    }

                    val intent = Intent(this@MainActivity, ListActivity::class.java)
                    intent.putStringArrayListExtra("assetUrls", ArrayList(assetUrls))
                    startActivity(intent)

                    runOnUiThread {
                        binding.progressbar.visibility=View.GONE
                    }

                }else{
                    print(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<responseData>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }
}