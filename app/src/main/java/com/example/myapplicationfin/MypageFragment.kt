package com.example.myapplicationfin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplicationfin.AuthActivity
import com.example.myapplicationfin.MyApplication
import com.example.myapplicationfin.R
import com.example.myapplicationfin.databinding.FragmentMypageBinding
import java.io.File
import java.io.FileOutputStream

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("MypageFragment", "onCreateView() called")

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentMypageBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val intent = Intent(requireContext(), AuthActivity::class.java)

        binding.btnLogout.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            intent.putExtra("data", "logout")
            startActivity(intent)
        }

        binding.userEmail.text = MyApplication.email

        val requestGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                val calRatio = calculateInSampleSize(it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize))

                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = requireContext().contentResolver.openInputStream(it.data?.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)

                inputStream!!.close()
                inputStream = null

                bitmap?.let {
                    val imagePath = saveImageToFile(bitmap)
                    binding.profileImageView.setImageBitmap(bitmap)

                } ?: let {
                    Log.d("mobileApp", "bitmap NULL")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.profileImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        val imagePath = sharedPreferences.getString("imagePath", null)
        imagePath?.let {
            val bitmap = BitmapFactory.decodeFile(it)
            binding.profileImageView.setImageBitmap(bitmap)
        }

        return binding.root
    }

    private fun saveImageToFile(bitmap: Bitmap): String {
        val file = File(requireContext().filesDir, "profile_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        val imagePath = file.absolutePath
        editor.putString("imagePath", imagePath)
        editor.apply()
        return imagePath
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = requireContext().contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
