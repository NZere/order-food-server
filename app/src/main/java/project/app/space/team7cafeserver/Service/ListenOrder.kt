package project.app.space.team7cafeserver.Service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.IBinder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import project.app.space.team7cafeserver.OrderStatusChangeActivity
import project.app.space.team7cafeserver.Model.OrderRequest

class ListenOrder : Service(), ChildEventListener {
    var database = FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    val users = database.getReference("User")
    var requests: DatabaseReference = database.getReference("Request")


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        requests= database.getReference("Request")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        requests.addChildEventListener(this)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        var request: OrderRequest? = snapshot.getValue(OrderRequest::class.java)
        showNotification(snapshot.key, request)
    }

    private fun showNotification(key: String?, request: OrderRequest?) {
        var intent = Intent(baseContext, OrderStatusChangeActivity::class.java)
        intent.putExtra("userId", request!!.user_id)
        var contentIntent = PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = Notification.Builder(baseContext)

        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setTicker("Cafe Team 7")
            .setContentInfo("Your Order is updated")
            .setContentText("Order $key was updated status to ${OrderStatusChangeActivity().convertCodeToStatus(
                request.status.toString()
            )}")
            .setContentIntent(contentIntent)
            .setContentInfo("Info")
            .setSmallIcon(R.drawable.notification_icon_background)

        var notificationManager:NotificationManager= baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1,builder.build())

    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        TODO("Not yet implemented")
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

}