package xyz.mustafaali.devqstiles.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import xyz.mustafaali.devqstiles.R
import xyz.mustafaali.devqstiles.model.Feature
import xyz.mustafaali.devqstiles.util.inflate

class FeaturesAdapter(val features: List<Feature>, val listener: (Feature) -> Unit) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.feature_item_layout))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int = 5

}