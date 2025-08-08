package com.example.opsc6312demo1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.opsc6312demo1.databinding.FragmentPostBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    data class Post(val userId: Int, val id: Int, val title: String, val body: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val post = makeHttpGetRequest("https://jsonplaceholder.typicode.com/posts/1")

            withContext(Dispatchers.Main) {
                if (post != null) {
                    binding.textUserId.text = post.userId.toString()
                    binding.textPostId.text = post.id.toString()
                    binding.textTitle.text = post.title
                    binding.textBody.text = post.body
                } else {
                    binding.textBody.text = "Failed to fetch post."
                }
            }
        }
        binding.btnJsonHandler.setOnClickListener {
            val intent = Intent(requireContext(), HandleJSONActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeHttpGetRequest(urlString: String): Post? {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.use { it.readText() }
                Gson().fromJson(response, Post::class.java)
            } else null
        } catch (e: Exception) {
            null
        } finally {
            connection.disconnect()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}