package com.example.opsc6312demo1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.opsc6312demo1.databinding.FragmentPost2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostFragment2 : Fragment()  {

    private var _binding: FragmentPost2Binding? = null
    private val binding get() = _binding
    private val postHandler = PostHandler(RetrofitClient.api)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPost2Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val result = postHandler.fetchPost()

            withContext(Dispatchers.Main) {
                result.onSuccess { post ->
                    binding?.textUserId?.text = post.userId.toString()
                    binding?.textPostId?.text = post.id.toString()
                    binding?.textTitle?.text = post.title
                    binding?.textBody?.text = post.body
                }
                result.onFailure { e ->
                    Log.e("RetrofitError", "Exception: ", e)
                    binding?.textBody?.text = "Failed: ${e.javaClass.simpleName} - ${e.message}"
                }
            }
        }

        binding!!.btnJsonHandler.setOnClickListener {
            val intent = Intent(requireContext(), HandleJSONActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}