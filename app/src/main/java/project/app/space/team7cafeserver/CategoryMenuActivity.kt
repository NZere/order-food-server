package project.app.space.team7cafeserver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.common.internal.service.Common
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import project.app.space.team7cafeserver.databinding.ActivityCategoryMenuBinding
import project.app.space.team7cafeserver.Interface.ItemClickListener
import project.app.space.team7cafeserver.Model.Category
import project.app.space.team7cafeserver.ViewHolder.MenuViewHolder

class CategoryMenuActivity() : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCategoryMenuBinding
    private lateinit var auth: FirebaseAuth


    val database = FirebaseDatabase.getInstance()
    lateinit var txtFullName:TextView
    lateinit var recyler_menu: RecyclerView
    lateinit var layoutManager:LinearLayoutManager
    lateinit var categories:DatabaseReference
    lateinit var adapter:FirebaseRecyclerAdapter<Category, MenuViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityCategoryMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarCategoryMenu.toolbar)


        //init firebase
        categories = database.getReference("Category")


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_categories -> {
                    val intent=Intent(this@CategoryMenuActivity, CategoryMenuActivity::class.java)
                    startActivity(intent)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_cart -> {
//                    val intent=Intent(this@CategoryMenuActivity, CartActivity::class.java)
                    startActivity(intent)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_orders -> {
//                    val intent=Intent(this@CategoryMenuActivity, OrderListActivity::class.java)
                    startActivity(intent)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_coupons -> {
//                    val intent=Intent(this@CategoryMenuActivity, CouponActivity::class.java)
                    startActivity(intent)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sign_out -> {
                    val intent=Intent(this@CategoryMenuActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    return@setNavigationItemSelectedListener true
                }

                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }


        //set name of user
        var headerView: View =navView.getHeaderView(0)
        txtFullName=headerView.findViewById(R.id.txtFullName)
        val users = database.getReference("User")
        users.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                var name = it.child("name").value.toString()
                txtFullName.text = name
            }
        }
        recyler_menu= findViewById(R.id.recycler_menu)
        recyler_menu.setHasFixedSize(true)
//        layoutManager = LinearLayoutManager(this)

        layoutManager = WrapContentLayoutManager(this@CategoryMenuActivity, LinearLayoutManager.VERTICAL,false)
        recyler_menu.layoutManager = layoutManager

        loadMenu()


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_categories,R.id.nav_cart,R.id.nav_coupons, R.id.nav_orders, R.id.nav_sign_out
            ), drawerLayout
        )
    }

    private fun loadMenu() {
        val options = FirebaseRecyclerOptions.Builder<Category>()
            .setQuery(categories,Category::class.java )
            .setLifecycleOwner(this)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                    return MenuViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item, parent, false))
                }
                override fun onBindViewHolder(
                    viewHolder: MenuViewHolder,
                    position: Int,
                    model: Category
                ) {
                    viewHolder.txtMenuName.text= model.name
                    Picasso.with(baseContext).load(model.image).into(viewHolder.imageView)
                    var clickItem:Category= model
                    viewHolder.setItemClickListener(object :ItemClickListener{
                        override fun onClick(view: View, position: Int, isLongClick: Boolean) {
//                            val intent:Intent = Intent(this@CategoryMenuActivity, FoodListActivity::class.java)
//                            intent.putExtra("CategoryId", adapter.getRef(position).key)
//                            startActivity(intent)

                            Toast.makeText(baseContext,""+adapter.getRef(position).key,Toast.LENGTH_SHORT).show()
                        }
                    }
                    )
                }
            }
        recyler_menu.adapter=adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

}


