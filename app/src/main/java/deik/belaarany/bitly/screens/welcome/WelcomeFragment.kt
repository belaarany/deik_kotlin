package deik.belaarany.bitly.screens.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import deik.belaarany.bitly.R
import deik.belaarany.bitly.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false)

        binding.playGameButton.setOnClickListener {
            val longUrl = binding.longUrlInput.getText().toString()

            Log.d("WelcomeFragment", "Long URL: " + longUrl)

            GlobalScope.launch {
                val link = fetchReq("https://goabela.com")

                Log.d("FromCoroutineLaunch", "link: " + link)

                val action = WelcomeFragmentDirections.actionWelcomeFragmentToCreatedFragment(link)
                findNavController().navigate(action)
            }
        }

        binding.showBitlinks.setOnClickListener {
            Log.d("showBitlinks", "Listener: clicked")
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToLinkListFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private suspend fun fetchReq(longUrl: String) = suspendCoroutine<String> { cont ->
        val queue = Volley.newRequestQueue(activity)
        val url = "https://api-ssl.bitly.com/v4/bitlinks"
        val paramJson = JSONObject()
        paramJson.put("long_url", longUrl)

        val request: JsonObjectRequest = object : JsonObjectRequest(Method.POST, url, paramJson,
                Response.Listener { response ->
                    Log.i("onResponse", response.toString())
                    Log.d("link", response["link"] as String)

                    cont.resume(response["link"] as String)
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