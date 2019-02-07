package mx.com.irvinglop.placefinderkt.model

import com.google.gson.annotations.SerializedName

data class ApiFourSquareResponse(
        val meta: Meta,
        val response: Response
)

data class Meta(
        val code: Int,
        val requestId: String
)

data class Response(
        val venues: List<Venue>
)

data class Venue(
        val id: String,
        val name: String,
        //val contact:,
        val location: Location,
        //val categories: List<>,
        val verified: Boolean,
        /*val stats: ,
        val beenHere: ,
        val hereNow: ,*/
        val referralId: String,
        val venueChains: List<Any>,
        val hasPerk: Boolean
)

data class Location(
        @SerializedName("lat")
        val latitude: Double,
        @SerializedName("lng")
        val longitude: Double,
        val distance: Int,
        val cc: String,
        val city: String,
        val state: String,
        val country: String
)
