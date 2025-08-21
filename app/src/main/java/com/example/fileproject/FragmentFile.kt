package com.example.fileproject

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fileproject.databinding.AddFileDialogBinding
import com.example.fileproject.databinding.AddFolderDialogBinding
import com.example.fileproject.databinding.FragmentFileBinding
import com.example.fileproject.databinding.LongClickedDialogBinding
import java.io.File


@SuppressLint("ValidFragment")
class FragmentFile(val path: String) : Fragment(), fileAdapter.FileEvent {
    lateinit var binding: FragmentFileBinding
    lateinit var adapter: fileAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MainActivity.ourViewType == 0) {
            binding.btnShowType.setImageResource(R.drawable.ic_grid)
        } else {
            binding.btnShowType.setImageResource(R.drawable.ic_list)
        }

        val ourFile = File(path)
        binding.txtPath.text = ourFile.name + ">"

        if (ourFile.isDirectory) {

            val listOfFiles = arrayListOf<File>()
            listOfFiles.addAll(ourFile.listFiles()!!)
            listOfFiles.sort()

            adapter = fileAdapter(listOfFiles, this)
            binding.recyclerMain.adapter = adapter
            binding.recyclerMain.layoutManager = GridLayoutManager(
                context,

                MainActivity.ourSpanCount,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter.changeViewType(MainActivity.ourViewType)

            if (listOfFiles.size > 0) {

                binding.recyclerMain.visibility = View.VISIBLE
                binding.imgNoData.visibility = View.GONE


            } else {

                binding.recyclerMain.visibility = View.GONE
                binding.imgNoData.visibility = View.VISIBLE

            }
        }
        binding.btnAddFile.setOnClickListener {

            createNewFile()
        }

        binding.btnAddFolder.setOnClickListener {
            createNewFolder()
        }

        binding.btnShowType.setOnClickListener {

            if (MainActivity.ourViewType == 0) {

                MainActivity.ourViewType = 1
                MainActivity.ourSpanCount = 3

                adapter.changeViewType(MainActivity.ourViewType)
                binding.recyclerMain.layoutManager =
                    GridLayoutManager(context, MainActivity.ourSpanCount)

                binding.btnShowType.setImageResource(R.drawable.ic_list)

            } else if (MainActivity.ourViewType == 1) {

                MainActivity.ourViewType = 0
                MainActivity.ourSpanCount = 1

                adapter.changeViewType(MainActivity.ourViewType)
                binding.recyclerMain.layoutManager =
                    GridLayoutManager(context, MainActivity.ourSpanCount)

                binding.btnShowType.setImageResource(R.drawable.ic_grid)

            }

        }
    }

    private fun createNewFile() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val addFileBinding = AddFileDialogBinding.inflate(layoutInflater)
        dialog.setView(addFileBinding.root)

        dialog.show()

        addFileBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        addFileBinding.btnConfirm.setOnClickListener {

            addFileBinding.btnConfirm.isEnabled = false

            val nameOfNewFolder = addFileBinding.edtText.text.toString()
            val newFolder = File(path + File.separator + nameOfNewFolder)
            if (!newFolder.exists()) {
                if (newFolder.mkdirs()) {
                    adapter.addNewFile(newFolder)
                    binding.recyclerMain.scrollToPosition(0)
                }
            }
            dialog.dismiss()
        }
    }

    private fun createNewFolder() {

        val dialog = AlertDialog.Builder(requireContext()).create()

        val addFolderBinding = AddFolderDialogBinding.inflate(layoutInflater)
        dialog.setView(addFolderBinding.root)

        dialog.show()

        addFolderBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        addFolderBinding.btnConfirm.setOnClickListener {

            val nameOfNewFolder = addFolderBinding.edtText.text.toString()

            val newFile = File(path + File.separator + nameOfNewFolder)
            if (!newFile.exists()) {
                if (newFile.mkdir()) {
                    adapter.addNewFile(newFile)
                    binding.recyclerMain.scrollToPosition(0)
                    binding.recyclerMain.visibility = View.VISIBLE
                    binding.imgNoData.visibility = View.GONE
                }
            }

            dialog.dismiss()

        }

    }

    override fun onFileClicked(file: File, type: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        //here we must check that user sdk version is more than 7 or is less than if it we must get permission and send
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val fileProvider = FileProvider.getUriForFile(
                requireContext(),
                requireActivity().packageName + ".provider",
                file
            )
            intent.setDataAndType(fileProvider, type)
        } else {
            intent.setDataAndType(Uri.fromFile(file), type)
        }


        //before start the intent we need to get access from user for reading files
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        //i have add this code for check when user have not a application for open file return to it that is not find app
        val packageManager = requireContext()!!.packageManager
        if (intent.resolveActivity(packageManager) != null) {
            requireContext()!!.startActivity(intent)
        } else {
            // Show a message to the user
            Toast.makeText(
                context,
                "No app found to open this file. Please install a suitable app.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onFolderClicked(path: String) {
        Log.v("tagForShow", path)
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main_container, FragmentFile(path))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onLongClicked(file: File, position: Int) {

        val dialogDeleteBinding = LongClickedDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(dialogDeleteBinding.root)
        dialog.show()

        dialogDeleteBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.btnConfirm.setOnClickListener {

            if (file.exists()) {
                if (file.deleteRecursively()) {
                    adapter.removeFile(file, position)
                }
            }

            dialog.dismiss()
        }

    }
}