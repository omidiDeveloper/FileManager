package com.example.fileproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fileproject.MainActivity.Companion.ourViewType
import java.io.File
import java.net.URLConnection

class fileAdapter (val data : ArrayList<File> , val fileEvent: FileEvent) : RecyclerView.Adapter<fileAdapter.FileViewHolder>() {
    private var ourViewType = 0
    inner class FileViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val txt = itemView.findViewById<TextView>(R.id.textView)
        val img = itemView.findViewById<ImageView>(R.id.imageView)

        @SuppressLint("SetTextI18n")
        fun onBind(file: File){

            var fileType = ""
            txt.text = file.name
            img.setImageResource(R.drawable.ic_folder)

            if (file.isDirectory){
                img.setImageResource(R.drawable.ic_folder)
            }else{

                when
                {
                    isImage(file.path) ->{
                        img.setImageResource(R.drawable.ic_image)
                        fileType = "image/*"
                    }

                    isVideo(file.path) -> {
                        img.setImageResource(R.drawable.ic_video)
                        fileType = "video/*"
                    }
                    isMusic(file.path) -> {
                        img.setImageResource(R.drawable.ic_sound)
                        fileType = "audio/*"
                    }
                    isZip(file.name) -> {
                        img.setImageResource(R.drawable.ic_zip)
                        fileType = "application/zip"
                    }
                    isRar(file.name) -> {
                        img.setImageResource(R.drawable.ic_rar)
                        fileType = "application/x-rar-compressed"
                    }
                    else -> {
                        img.setImageResource(R.drawable.ic_file)
                        fileType = "text/*"
                    }
                }



            }

            itemView.setOnClickListener{
                if (file.isDirectory){
                    fileEvent.onFolderClicked(file.path)
                }else{
                    fileEvent.onFileClicked(file , fileType)
                }
            }

            itemView.setOnLongClickListener {
                fileEvent.onLongClicked(file, adapterPosition)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view: View

        if (viewType == 0) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_liniear, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_grid, parent, false)
        }

        return FileViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return ourViewType
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    fun isImage(path : String?) : Boolean{
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType?.startsWith("image") == true
    }

    fun isVideo(path: String?) : Boolean{
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType?.startsWith("video") == true
    }

    fun isMusic(path: String?) : Boolean{
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType?.startsWith("audio") == true
    }

    fun isZip(name: String?) : Boolean{
        return name?.contains(".zip") == true
    }
    fun isRar(name: String?) : Boolean{
        return name?.contains(".rar") == true
    }

    fun addNewFile(newFile: File){
        data.add(0 , newFile)
        notifyItemInserted(0)
    }

    fun changeViewType(newViewType: Int) {
        ourViewType = newViewType
        notifyDataSetChanged()
    }

    fun removeFile(oldFile: File, position: Int) {
        data.remove(oldFile)
        notifyItemRemoved(position)
    }

    interface FileEvent{
        fun onFileClicked(file: File , type : String)
        fun onFolderClicked(path : String)
        fun onLongClicked(file: File, position: Int)
    }


}