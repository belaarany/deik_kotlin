package deik.belaarany.bitly.screens.linkList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import deik.belaarany.bitly.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LinkListFragment : Fragment() {
    protected var listString: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)

        listString.add("Link 1")
        listString.add("Link 2")
        listString.add("Link 2")

        val fragmentView: View = inflater.inflate(R.layout.fragment_links, container, false)
        val recyclerView: RecyclerView = fragmentView.findViewById<View>(R.id.rvLinks) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        val adapter = LinkListAdapter(listString)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        GlobalScope.launch {
            val animalNames: ArrayList<String> = ArrayList()
            val myBitlinks = fetchReq()

            for (i in 0 until myBitlinks.length()) {
                val item = myBitlinks.getJSONObject(i)

                Log.d("iterate", item["link"] as String)
                animalNames.add(item["link"] as String)

                //adapter.refreshData(item["link"] as String)
                //adapter.notifyDataSetChanged()
            }
        }

        return fragmentView
    }

    fun onItemClick(view: View?, position: Int) {
        Toast.makeText(
            activity,
            "You clicked me",
            Toast.LENGTH_SHORT
        ).show()
    }

    private suspend fun fetchReq() = suspendCoroutine<JSONArray> { cont ->
        val queue = Volley.newRequestQueue(activity)
        val url = "https://api-ssl.bitly.com/v4/groups/BkcbhIRShcK/bitlinks"

        val request: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                Log.i("onResponse", response.toString())

                cont.resume(response["links"] as JSONArray)
            },
            Response.ErrorListener { error ->
                Log.e("onErrorResponse", error.toString())
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Authorization"] = "Bearer 0134bcb4485a79174ad86e741e5ce09cd87db4b8"
                return headers
            }
        }
        queue.add(request)
    }
}