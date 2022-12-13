package project.app.space.team7cafeserver.Interface

import android.view.View
import java.text.FieldPosition

interface ItemClickListener {
    fun onClick(view: View, position: Int, isLongClick:Boolean ){}
}