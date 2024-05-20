package com.tupleinfotech.matrixclick

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: athulyatech
 * @Date: 5/20/24
 */

@SuppressLint("SetTextI18n")
class MatrixAdapter(private val size: Int, private val clickListener: (Int, Int) -> Unit) : RecyclerView.Adapter<MatrixAdapter.MatrixViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrixViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.matrix_item, parent, false)
        return MatrixViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatrixViewHolder, position: Int) {
        val row = position / size
        val col = position % size
        holder.bind(row, col, clickListener)
    }

    override fun getItemCount(): Int {
        return size * size
    }

    class MatrixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: Int, col: Int, clickListener: (Int, Int) -> Unit) {
            itemView.setOnClickListener {
                clickListener(row, col)
            }
        }
    }
}
/*class MatrixAdapter(private val size: Int, private val clickListener: (Int, Int, View) -> Unit) :
    RecyclerView.Adapter<MatrixAdapter.MatrixViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrixViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.matrix_item, parent, false)
        return MatrixViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatrixViewHolder, position: Int) {
        val row = position / size
        val col = position % size
        holder.bind(row, col, clickListener)
    }

    override fun getItemCount(): Int {
        return size * size
    }

    class MatrixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: Int, col: Int, clickListener: (Int, Int, View) -> Unit) {
            itemView.setOnClickListener {
                clickListener(row, col, itemView)
            }
        }
    }
}*/
