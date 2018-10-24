package jp.msb.kib.stickablerowheader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import jp.msb.kib.stickablerowheader.Header
import jp.msb.kib.stickablerowheader.R
import jp.msb.kib.stickablerowheader.Row
import jp.msb.kib.stickablerowheader.databinding.ViewHeaderBinding
import jp.msb.kib.stickablerowheader.databinding.ViewRowBinding

import msb.jp.stikablerowheader.SectionAdapterViewType
import msb.jp.stikablerowheader.StickableRowHeaderAdapter

/**
 * Created by misyobun on 2018/10/23
 */
class SampleAdapter: StickableRowHeaderAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        if (viewType == SectionAdapterViewType.ROWHEADER.rawValue) {
            return HeaderViewHolder(parent)
        } else {
            return RowViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        if (items.size > position) {
            val item = items[position]
            if (holder is HeaderViewHolder && item is Header) {
                holder.bind(item)
            }
            if (holder is RowViewHolder && item is Row) {
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if (item is Header) {
            return SectionAdapterViewType.ROWHEADER.rawValue
        }
        return SectionAdapterViewType.ROW.rawValue
    }


    override fun getHeaderLayout(headerPosition: Int): Int {
        return R.layout.view_header
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        val targetItem = items.get(headerPosition)
        val item = targetItem as? Header
        if (item is Header) {
            val headerText = header.findViewById<TextView>(R.id.headerTitle)
            headerText.setText(item.title)
        }
    }

    override fun isHeader(itemPosition: Int): Boolean {
        val item = items.get(itemPosition)
        if (item is Header) {
            return true
        }
        return false
    }
}

class RowViewHolder(
    private val parent: ViewGroup,
    private val binding: ViewRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_row, parent, false)
) : StickableRowHeaderAdapter.RootViewHolder(binding.root) {
    fun bind(row: Row) {
        binding.row = row
    }
}

class HeaderViewHolder(
    private val parent: ViewGroup,
    private val binding: ViewHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.view_header,parent,false)
) : StickableRowHeaderAdapter.RootViewHolder(binding.root) {
    fun bind(header: Header) {
        binding.header = header
    }
}