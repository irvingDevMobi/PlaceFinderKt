package mx.com.irvinglop.placefinderkt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapResultsFragment : Fragment(), OnMapReadyCallback {

    val response by lazy { arguments?.getString(TAG) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mainMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onMapReady(map: GoogleMap?) {

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
