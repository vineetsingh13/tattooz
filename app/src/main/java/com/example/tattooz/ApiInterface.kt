package com.example.tattooz

import com.example.tattooz.util.key
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("image/generation")
    fun respone(
        @Body body: requestData,
        @Header("Accept") Accept: String="application/json",
        @Header("Authorization") authorization: String="Bearer $key",
        @Header("Content-Type") contentType: String="application/json",
        @Header("X-Api-Version") api: String="v1"
        ) : Call<responseData>
}