package com.example.tattooz

import com.google.gson.annotations.SerializedName

data class responseData(
    @SerializedName("id") val id: String,
    @SerializedName("self") val self: String,
    @SerializedName("status") val status: String,
    @SerializedName("failure_code") val failureCode: String?,
    @SerializedName("failure_reason") val failureReason: String?,
    @SerializedName("credits_used") val creditsUsed: Double,
    @SerializedName("credits_remaining") val creditsRemaining: Float,
    @SerializedName("data") val data: List<Asset>
)

data class Asset(
    @SerializedName("asset_id") val assetId: String,
    @SerializedName("self") val self: String,
    @SerializedName("asset_url") val assetUrl: String,
    @SerializedName("type") val type: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)
