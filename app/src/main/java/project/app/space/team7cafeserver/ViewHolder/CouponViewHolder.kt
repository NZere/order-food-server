package project.app.space.team7cafeserver.ViewHolder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.app.space.team7cafeserver.Interface.ItemClickListener
import project.app.space.team7cafeserver.R

class CouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txtCouponId: TextView
    var txtCouponName: TextView
    var btnCopyCoupon: Button
    private lateinit var itemClickListener: ItemClickListener

    init {
        txtCouponId = itemView.findViewById(R.id.coupon_item_id)
        txtCouponName = itemView.findViewById(R.id.coupon_item_name)
        btnCopyCoupon = itemView.findViewById(R.id.button_copy)
        itemView.setOnClickListener(this)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
}