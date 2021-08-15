package com.example.converttoblack_white.collectionfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.converttoblack_white.FilePath
import com.example.converttoblack_white.R
import com.example.converttoblack_white.collectionfragment.adapter.PhotoAdapter
import com.example.converttoblack_white.databinding.FragmentCollectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CollectionFragment : Fragment() {
    private lateinit var binding: FragmentCollectionBinding
    private lateinit var viewModel: CollectionFragmentViewModel
    private val photoAdapter = PhotoAdapter {
        navigate(
            CollectionFragmentDirections.actionCollectionFragmentToConvertedImageFragment(
                arrayOf(it.photoId.toString(), it.photoUri)
            )
        )
        Log.d("tetetete", "${it.photoId} ${it.photoUri}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_collection, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = CollectionFragmentFactory(
            application
        )

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CollectionFragmentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recycleViewPhotos.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycleViewPhotos.adapter = photoAdapter

        viewModel.users.observe(viewLifecycleOwner, { photoAdapter.submitList(it) })
        viewModel.navigateBack.observe(viewLifecycleOwner) { findNavController().popBackStack() }
        viewModel.addClickEvent.observe(viewLifecycleOwner) {
            dispatchPictureIntent()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFolderContent()
    }

    private fun navigate(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    private fun showFolderContent() {
        val directory: File = File(FilePath.getRepositoryPath())
        val files = directory.listFiles()
        Log.d("showFiles", "${files.map { it.name }}")
    }


    val REQUEST_IMAGE_CAPTURE = 1

    lateinit var currentPhotoPath: String


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            ?: throw FileNotFoundException("File not found")
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            FilePath.setFilePath(currentPhotoPath)
            Log.d("currentPath", currentPhotoPath)
        }
    }

    private fun dispatchPictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.converttoblack_white",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
}