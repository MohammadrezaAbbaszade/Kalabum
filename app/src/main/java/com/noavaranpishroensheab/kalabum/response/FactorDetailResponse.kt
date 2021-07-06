package com.noavaranpishroensheab.kalabum.response
import com.google.gson.annotations.SerializedName


data class FactorDetailResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: FactorDetailData,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class FactorDetailData(
    @SerializedName("invoice")
    val invoice: Invoice
)

data class Invoice(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("notification_text")
    val notificationText: String,
    @SerializedName("offline_payments")
    val offlinePayments: List<Any>,
    @SerializedName("online_payments")
    val onlinePayments: List<OnlinePayment>,
    @SerializedName("payment_method")
    val paymentMethod: Int,
    @SerializedName("pre_payment")
    val prePayment: Int,
    @SerializedName("sms_text")
    val smsText: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("valid")
    val valid: Int,
    @SerializedName("valid_until")
    val validUntil: String
)

data class Item(
    @SerializedName("count")
    val count: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("invoice_id")
    val invoiceId: Int,
    @SerializedName("notes")
    val notes: Any,
    @SerializedName("options")
    val options: Options,
    @SerializedName("price_per_unit")
    val pricePerUnit: Int,
    @SerializedName("product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class OnlinePayment(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("authorize")
    val authorize: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("invoice_id")
    val invoiceId: Int,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("RRN")
    val rRN: Any,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("verified")
    val verified: Boolean
)

data class Options(
    @SerializedName("1")
    val x1: String,
    @SerializedName("2")
    val x2: String
)

data class Product(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: List<Option>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("tags")
    val tags: String
)

data class Option(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int
)