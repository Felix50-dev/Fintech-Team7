package com.aad.fintech.ui.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.aad.fintech.R

class SexAdapter (context: Context, val resourceId: Int, val sex: List<Sex>) :
    ArrayAdapter<Sex>(context, resourceId, sex) {

    private val sexMList: MutableList<Sex> = ArrayList(sex)
    private var sexList: List<Sex> = ArrayList(sex)

    class SexViewHolder(view: View) {
        val wording: TextView

        init {
            wording = view.findViewById(R.id.wording)
        }
    }

    override fun getItem(position: Int): Sex {
        return sex[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView ?: LayoutInflater.from(context).inflate(resourceId, parent, false)
        var holder = SexViewHolder(view)

        try {
            val sx = getItem(position)
            holder.wording.text = sx.wording
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun convertResultToString(resultValue: Any) :String {
                return (resultValue as Sex).wording
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    filterResults.values = sexList
                    filterResults.count = sexList.size
                }
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                sexMList.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is Sex) {
                            sexMList.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    sexMList.addAll(sexList)
                    notifyDataSetInvalidated()
                }
            }
        }
    }

}