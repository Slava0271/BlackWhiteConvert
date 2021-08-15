package com.example.converttoblack_white.convertedimagefragment

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.converttoblack_white.R
import com.example.converttoblack_white.databinding.FragmentConvertedImageBinding
import java.io.File
import java.io.FileNotFoundException


class ConvertedImageFragment : Fragment() {
    private lateinit var binding: FragmentConvertedImageBinding
    private lateinit var viewModel :  ConvertedImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_converted_image, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ConvertedImageFragmentFactory(
            application
        )

         viewModel =
            ViewModelProvider(this, viewModelFactory).get(ConvertedImageViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigateBack.observe(viewLifecycleOwner){
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val photo = ConvertedImageFragmentArgs.fromBundle(args!!).photo
        if (photo != null) {
            val bitmap =  loadBitmapFromFile(photo.last())
            viewModel.convertEvent.observe(viewLifecycleOwner){
                binding.convertedImageView.setImageBitmap(toGrayscale(bitmap))
            }
        }
    }
    fun toGrayscale(srcImage: Bitmap): Bitmap? {
        val bmpGrayscale =
            Bitmap.createBitmap(srcImage.width, srcImage.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmpGrayscale)
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        paint.setColorFilter(ColorMatrixColorFilter(cm))
        canvas.drawBitmap(srcImage, 0f, 0f, paint)
        return bmpGrayscale
    }

    private fun loadBitmapFromFile(filePath: String):Bitmap {
        val imgFile = File(filePath)
        Log.d("filePath", filePath)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            binding.convertedImageView.setImageBitmap(myBitmap)
            return myBitmap
        } else {
            Log.d("FileNotExists", ":0")
        }
        throw FileNotFoundException("File not found")
    }
}