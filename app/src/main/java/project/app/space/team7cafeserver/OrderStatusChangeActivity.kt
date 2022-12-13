package project.app.space.team7cafeserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.app.space.team7cafeserver.Interface.ItemClickListener
import project.app.space.team7cafeserver.ViewHolder.OrderViewHolder
import project.app.team7cafe.Model.OrderRequest

class OrderStatusChangeActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val database = FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val users = database.getReference("User")
    val food = database.getReference("Food")
    val request: DatabaseReference = database.getReference("Request")
    val coupons = database.getReference("Coupons")

    lateinit var adapter: FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_status_change)


        recyclerView = findViewById(R.id.listOrder)
        recyclerView.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)
        layoutManager = WrapContentLayoutManager(this@OrderStatusChangeActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        loadOrders(auth.currentUser?.uid)

    }

    private fun loadOrders(uid: String?) {
        val options = FirebaseRecyclerOptions.Builder<OrderRequest>()
            .setQuery(request, OrderRequest::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<OrderRequest, OrderViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
                    return OrderViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.order_layout, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: OrderViewHolder,
                    position: Int,
                    model: OrderRequest
                ) {
                    holder.txtOrderId.text = adapter.getRef(position).key
                    holder.txtOrderStatus.text = convertCodeToStatus(model.status.toString())
                    holder.txtOrderTable.text = model.table_id.toString()
                    holder.txtOrderTotal.text = model.total.toString()

                    holder.setItemClickListener(object : ItemClickListener {
//                        override fun onClick(view: View, position: Int, isLongClick: Boolean) {
//                            val intent: Intent = Intent(this@OrderStatusChangeActivity, OrderDetailActivity::class.java)
//                            intent.putExtra("OrderRequestId", adapter.getRef(position).key)
//                            startActivity(intent)
//
////                            Toast.makeText(baseContext,""+adapter.getRef(position).key,Toast.LENGTH_SHORT).show()
//
//                        }
                    })
                }


            }
        adapter.notifyDataSetChanged()
        recyclerView.adapter=adapter

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.intent.equals("Update")){
            showUpdateDialog(adapter.getRef(item.order).key, adapter.getItem(item.order))
        }
        else if(item.intent.equals("Delete")){
            deleteOrder(adapter.getRef(item.order).key)
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteOrder(key: String?) {

    }

    private fun showUpdateDialog(key: String?, item: OrderRequest) {

    }

    fun convertCodeToStatus(status: String): String {
        when (status) {
            "0" -> {
                return "in queue"
            }
            "1" -> {
                return "in process"
            }
            "2" -> {
                return "gave it to waiter"
            }
            "3" -> {
                return "done"
            }
        }
        return ""
    }

}