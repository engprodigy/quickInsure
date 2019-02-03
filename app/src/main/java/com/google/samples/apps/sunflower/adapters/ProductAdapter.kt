package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.Users


class ProductAdapter(private val myDataset: ArrayList<Users>) :
        RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
   // class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProductAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.text_view, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.card_title.text = myDataset[position].product_name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var card_title: TextView

        init {
            card_title = itemView.findViewById<View>(R.id.card_title) as TextView
        }
    }
}
