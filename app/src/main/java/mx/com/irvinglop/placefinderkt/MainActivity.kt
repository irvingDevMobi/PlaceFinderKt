package mx.com.irvinglop.placefinderkt

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActionButton.setOnClickListener { doRequest() }
    }

    private fun doRequest() {
        val query = mainEditText.text.toString()
        if (query.isNotBlank()) {
            val queue = Volley.newRequestQueue(this)
            val url = getServiceConfig(query)
            val stringRequest = StringRequest(Request.Method.GET, url,
                                              Response.Listener<String> { processResponse(it) },
                                              Response.ErrorListener { processError(it) })
            queue.add(stringRequest)
        }
    }

    private fun processResponse(response: String) {
        Log.d("Response", response)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer,
                                    mapResultsFragmentNewInstance(response))
        fragmentTransaction.commit()
    }

    private fun processError(error: VolleyError) {
        Log.e("ResponseError", error.localizedMessage)
    }

    private fun getServiceConfig(query: String): String =
            Uri.parse("https://api.foursquare.com/v2/venues/search")
                    .buildUpon()
                    .appendQueryParameter("client_id",
                                          "HOSIY11XMXHWFADXIPQTF5HRZA3YIWIFGRAOA5NIGXOY3CWI")
                    .appendQueryParameter("client_secret",
                                          "OGATJNY0E0JY15PRXYD5MQ2WW3EMFLRAWFHLAOQYSTMVKMHM")
                    .appendQueryParameter("v", "20130815")
                    .appendQueryParameter("ll", HARDCODE_LOCATION)
                    .appendQueryParameter("query", query)
                    .build().toString()
}

const val HARDCODE_LOCATION = "19.395209" + "," + "-99.1544203"
