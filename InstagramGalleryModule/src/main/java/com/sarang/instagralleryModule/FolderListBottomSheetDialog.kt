package com.sarang.instagralleryModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sarang.instagralleryModule.databinding.FragmentFolderListBinding
import com.sarang.instagralleryModule.databinding.ItemFolderNameBinding

class FolderListBottomSheetDialog  : BottomSheetDialogFragment(){
    lateinit var viewBinding : FragmentFolderListBinding

    var listener : ((ImageData) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return layoutInflater.inflate(R.layout.fragment_folder_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentFolderListBinding.bind(view)

        val mediaContentResolver = MediaContentResolver.newInstance(requireContext())

        viewBinding.rv.adapter = FolderAdapter(object: ((ImageData) -> Unit){
            override fun invoke(imageData: ImageData) {
                listener?.invoke(imageData)
                dismiss()
            }
        }).apply {
            setFolders(mediaContentResolver.getFolderListImageData())
        }
    }
}

class FolderAdapter(val listener: (imageData: ImageData) -> Unit) : RecyclerView.Adapter<FolderViewHolder>() {
    private var folders = ArrayList<ImageData>()

    fun setFolders(list: ArrayList<ImageData>) {
        folders = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_folder_name, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.viewBinding.tv.text = folders[position].bucketDisplayName
        holder.viewBinding.root.setOnClickListener {
            listener.invoke(folders[position])
        }
    }
}

class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val viewBinding = ItemFolderNameBinding.bind(itemView)
}