package com.example.tattooz

import com.google.gson.annotations.SerializedName

data class requestData(

    @SerializedName("prompt") val prompt: String,
    @SerializedName("aspect_ratio") val aspectRatio: String,
    @SerializedName("samples") val samples: Int,
    @SerializedName("quality") val quality: String,
    @SerializedName("style") val style: String
)
