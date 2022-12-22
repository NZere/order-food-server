package project.app.space.team7cafeserver

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.app.space.team7cafeserver.Interface.ItemClickListener
import project.app.space.team7cafeserver.Model.Coupon
import project.app.space.team7cafeserver.ViewHolder.CouponViewHolder

class CouponActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val database = FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val users = database.getReference("User")
    val food = database.getReference("Food")
    val request: DatabaseReference = database.getReference("Request")
    val coupons = database.getReference("Coupon")

    lateinit var adapter: FirebaseRecyclerAdapter<Coupon, CouponViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        recyclerView = findViewById(R.id.listCoupon)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)
        layoutManager = WrapContentLayoutManager(this@CouponActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        loadCoupons(auth.currentUser?.uid)
    }

    fun loadCoupons(uid: String?) {
        val options = FirebaseRecyclerOptions.Builder<Coupon>()
            .setQuery(coupons.orderByChild("user_id").equalTo(auth.currentUser?.uid), Coupon::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<Coupon, CouponViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
                    return CouponViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.coupon_layout, parent, false)
                    )
                }


                override fun onBindViewHolder(
                    holder: CouponViewHolder,
                    position: Int,
                    model: Coupon
                ) {
                    holder.txtCouponName.text = model.name.toString()
                    holder.txtCouponId.text= adapter.getRef(position).key
                    holder.btnCopyCoupon.setOnClickListener{
                        var clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        var clip= ClipData.newPlainText("coupon id",holder.txtCouponId.text.toString())
                        clipboardManager.setPrimaryClip(clip)

                        clip.description

                        Toast.makeText(this@CouponActivity,"Copied"+holder.txtCouponId.text.toString(), Toast.LENGTH_SHORT).show()
                    }
                    holder.setItemClickListener(object : ItemClickListener {
                        override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                            var clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            var clip= ClipData.newPlainText("coupon id",holder.txtCouponId.text.toString())
                            clipboardManager.setPrimaryClip(clip)

                            clip.description

                            Toast.makeText(this@CouponActivity,"Copied"+holder.txtCouponId.text.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        recyclerView.adapter=adapter
    }
}