package msb.jp.stikablerowheader

/**
 * Created by misyobun on 2018/10/23
 */
import android.view.View
import androidx.recyclerview.widget.RecyclerView


enum class SectionAdapterViewType(val rawValue: Int) {
    ROWHEADER(0),
    ROW(1)
}


/**
 * Created by misyobun on 2018/10/22
 */
abstract class StickableRowHeaderAdapter : RecyclerView.Adapter<StickableRowHeaderAdapter.RootViewHolder>(),
    StickableRowHeaderDelegate {

    protected var items: List<BaseItem> = emptyList()


    override fun getItemCount(): Int {
        return items.size
    }

    fun update(items: List<BaseItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    abstract class RootViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var tmpItemPosition = itemPosition
        var headerPosition = -1
        do {
            if (this.isHeader(tmpItemPosition)) {
                headerPosition = tmpItemPosition
                break
            }
            tmpItemPosition -= 1
        } while (tmpItemPosition >= 0)
        return headerPosition
    }
}

