package com.noavaranpishroensheab.kalabum.response
import com.google.gson.annotations.SerializedName


data class InvoiceListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: InvoiceListData,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class InvoiceListData(
    @SerializedName("requests")
    val requests: InvoiceListRequests
)

data class InvoiceListRequests(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<InvoiceListDataX>,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    @SerializedName("from")
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    @SerializedName("links")
    val links: List<InvoiceListLink>,
    @SerializedName("next_page_url")
    val nextPageUrl: Any,
    @SerializedName("path")
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int
)

data class InvoiceListDataX(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int
)

data class InvoiceListLink(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("label")
    val label: String,
    @SerializedName("url")
    val url: Any
)