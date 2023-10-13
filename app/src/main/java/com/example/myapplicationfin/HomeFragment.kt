package com.example.myapplicationfin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationfin.databinding.FragmentHomeBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        var mutableList: MutableList<ItemHomeModel>

        binding.btnSearch.setOnClickListener {
            var keyword = binding.edtProduct.text.toString()
            val call: Call<MyModel> = MyApplication.networkService.getList(
                "8f70de5cbc97429a834f9d47e4bb62cd",
                1,
                10,
                "json",
                keyword
            )
            Log.d("mobileApp","${call.request()}_")

            call.enqueue(object : Callback<MyModel> {
                override fun onResponse(call: Call<MyModel>, response: Response<MyModel>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()!!.Ggmindmedinst[1].row
                        Log.d("mobileApp", "${response.body()}")
                        if (responseBody != null) {
                            binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
                            binding.retrofitRecyclerView.adapter = MyHomeAdapter(requireContext(), response.body()!!.Ggmindmedinst[1].row)
                            // 나머지 처리
                        } else {
                            Log.d("mobileApp", "Response body is null")
                        }
                    } else {
                        Log.d("mobileApp", "Response failed: ${response.code()}")
                    }
                }


                override fun onFailure(call: Call<MyModel>, t: Throwable) {
                    Log.d("mobileApp", "Request failed: ${t.message}")
                }
            })

        }

        mutableList = mutableListOf<ItemHomeModel>()
        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.retrofitRecyclerView.adapter = MyHomeAdapter(requireContext(), mutableList)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}