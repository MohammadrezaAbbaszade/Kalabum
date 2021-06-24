package com.noavaranpishroensheab.kalabum
import com.google.gson.annotations.SerializedName


data class NeshanAddress(
    @SerializedName("addresses")
    val addresses: List<Addresse>,
    @SerializedName("city")
    val city: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    @SerializedName("in_odd_even_zone")
    val inOddEvenZone: Boolean,
    @SerializedName("in_traffic_zone")
    val inTrafficZone: Boolean,
    @SerializedName("municipality_zone")
    val municipalityZone: String,
    @SerializedName("neighbourhood")
    val neighbourhood: String,
    @SerializedName("place")
    val place: Any,
    @SerializedName("route_name")
    val routeName: String,
    @SerializedName("route_type")
    val routeType: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("status")
    val status: String
)

data class Addresse(
    @SerializedName("components")
    val components: List<Component>,
    @SerializedName("formatted")
    val formatted: String
)

data class Component(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)