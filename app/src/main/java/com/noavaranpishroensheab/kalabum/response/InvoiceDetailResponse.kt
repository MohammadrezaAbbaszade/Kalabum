package com.noavaranpishroensheab.kalabum.response
import com.google.gson.annotations.SerializedName


data class InvoiceDetailResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: InvoiceDetailData,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class InvoiceDetailData(
    @SerializedName("invoice")
    val invoice: InvoiceDetailInvoice
)

data class InvoiceDetailInvoice(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: List<InvoiceDetailItem>,
    @SerializedName("notification_text")
    val notificationText: String,
    @SerializedName("offline_payments")
    val offlinePayments: List<Any>,
    @SerializedName("online_payments")
    val onlinePayments: List<Any>,
    @SerializedName("payment_method")
    val paymentMethod: Int,
    @SerializedName("payment_url")
    val paymentUrl: String,
    @SerializedName("pre_payment")
    val prePayment: Int,
    @SerializedName("sms_text")
    val smsText: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("valid")
    val valid: Boolean,
    @SerializedName("valid_until")
    val validUntil: String
)

data class InvoiceDetailItem(
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
    val notes: String,
    @SerializedName("options")
    val options: Any,
    @SerializedName("price_per_unit")
    val pricePerUnit: Int,
    @SerializedName("product")
    val product: InvoiceDetailProduct,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class InvoiceDetailProduct(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: List<InvoiceDetailOption>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("tags")
    val tags: String
)

data class InvoiceDetailOption(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int
)