package mx.com.irvinglop.placefinderkt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import mx.com.irvinglop.placefinderkt.model.ApiFourSquareResponse

class MapResultsFragment : Fragment() {

    private var googleMap: GoogleMap? = null

    private val response by lazy { arguments?.getString(TAG) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mainMap) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            drawPoints()
        }
        return view
    }

    private fun drawPoints() {
        val fourSquareResponse = Gson().fromJson(response, ApiFourSquareResponse::class.java)
        if (fourSquareResponse.response.venues.isEmpty()) {
            Toast.makeText(activity, "No points found", Toast.LENGTH_SHORT).show()
        } else {
            val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            for (venue in fourSquareResponse.response.venues) {
                val point = LatLng(venue.location.latitude, venue.location.longitude)
                val marker = MarkerOptions().apply {
                    position(point)
                    title(venue.name)
                    icon(icon)
                }
                googleMap?.addMarker(marker)
            }
            val firstVenue = fourSquareResponse.response.venues.first()
            val cameraObjective = CameraUpdateFactory.newLatLngZoom(
                    LatLng(firstVenue.location.latitude, firstVenue.location.longitude), 14f)
            googleMap?.moveCamera(cameraObjective)
        }
    }

    companion object {
        const val TAG = "MapResultsFragment"
    }
}

fun mapResultsFragmentNewInstance(response: String): MapResultsFragment {
    val instance = MapResultsFragment()
    val arguments = Bundle()
    arguments.putString(MapResultsFragment.TAG, response)
    instance.arguments = arguments
    // Show companion object and apply function
    return instance
}
