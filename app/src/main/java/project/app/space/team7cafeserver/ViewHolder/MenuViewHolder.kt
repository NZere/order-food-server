package project.app.space.team7cafeserver.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import project.app.space.team7cafeserver.Interface.ItemClickListener

import project.app.space.team7cafeserver.R

class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var itemClickListener: ItemClickListener
    var txtMenuName: TextView
    var imageView: ImageView? = null

    init {
        txtMenuName = itemView.findViewById(R.id.menu_name)
        imageView=itemView.findViewById(R.id.menu_image)
        itemView.setOnClickListener(this)

    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }

    override fun onClick(v: View) {
        itemClickListener.onClick(v, adapterPosition, false)
    }
}