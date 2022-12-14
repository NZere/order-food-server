package project.app.space.team7cafeserver.ViewHolder

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import project.app.space.team7cafeserver.Interface.ItemClickListener
import project.app.space.team7cafeserver.R

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnCreateContextMenuListener {
    var txtOrderId: TextView
    var txtOrderStatus: TextView
    var txtOrderTable: TextView
    var txtOrderTotal: TextView
    private lateinit var itemClickListener: ItemClickListener


    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }


    init {
        txtOrderId = itemView.findViewById(R.id.order_item_id)
        txtOrderStatus = itemView.findViewById(R.id.order_item_status)
        txtOrderTable = itemView.findViewById(R.id.order_item_table)
        txtOrderTotal = itemView.findViewById(R.id.order_item_total)
        itemView.setOnClickListener(this)
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onClick(v: View) {
        itemClickListener!!.onClick(v, adapterPosition, true)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle("Select the action")
        menu.add(0,0,adapterPosition, "Update")
        menu.add(0,1,adapterPosition, "Delete")
    }
}