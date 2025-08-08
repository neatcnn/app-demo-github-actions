package com.example.opsc6312demo1

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var textUserId: TextView
    private lateinit var textPostId: TextView
    private lateinit var textTitle: TextView
    private lateinit var textBody: TextView
    private lateinit var btnJsonHandler: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textUserId = findViewById(R.id.textUserId)
        textPostId = findViewById(R.id.textPostId)
        textTitle = findViewById(R.id.textTitle)
        textBody = findViewById(R.id.textBody)
        btnJsonHandler = findViewById(R.id.btnJsonHandler)


        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        // Use coroutine for network request
        CoroutineScope(Dispatchers.IO).launch {
            val apiUrl = "https://jsonplaceholder.typicode.com/posts/1"
            val post = makeHttpGetRequest(apiUrl)

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                if (post != null) {
                    textUserId.text = post.userId.toString()
                    textPostId.text = post.id.toString()
                    textTitle.text = post.title
                    textBody.text = post.body
                } else {
                    textBody.text = getString(R.string.failed_to_fetch_post)
                }
            }
        }

        btnJsonHandler.setOnClickListener{
            val intent = Intent(this,HandleJSONActivity::class.java)
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

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.use { it.readText() }
                Gson().fromJson(response, Post::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        } finally {
            connection.disconnect()
        }
    }
}