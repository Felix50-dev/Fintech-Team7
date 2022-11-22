package com.aad.fintech.ui.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.aad.fintech.R
import com.aad.fintech.databinding.ItemCountryBinding

class CountryAdapter(context: Context, val resourceId: Int, val countries: List<Country>) :
    ArrayAdapter<Country>(context, resourceId, countries) {

    class CountryViewHolder(view: View) {
        val code: TextView
        val name: TextView
        val flag: ImageView

        init {
            code = view.findViewById(R.id.code)
            name = view.findViewById(R.id.name)
            flag = view.findViewById(R.id.flag)
        }
    }

    override fun getItem(position: Int): Country {
        return countries[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView ?: LayoutInflater.from(context).inflate(resourceId, parent, false)
        var holder = CountryViewHolder(view)
        //var binding: ItemCountryBinding
        //binding = ItemCountryBinding.inflate(inflater, parent, false);

        try {
            val country: Country = getItem(position)
            holder.code.text = country.code
            holder.name.text = country.name
            holder.flag.setImageResource(country.flag)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

}