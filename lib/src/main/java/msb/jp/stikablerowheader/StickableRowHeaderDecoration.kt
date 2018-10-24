package msb.jp.stikablerowheader

import android.graphics.Canvas
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by misyobun on 2018/10/22
 */

interface StickableRowHeaderDelegate {
    fun getHeaderPositionForItem(itemPosition: Int): Int
    fun getHeaderLayout(headerPosition: Int): Int
    fun bindHeaderData(header:View, headerPosition: Int)
    fun isHeader(itemPosition: Int): Boolean
}

open class StickableRowHeaderDecoration(var listener: StickableRowHeaderDelegate): RecyclerView.ItemDecoration() {

    var shadowRadius:Float = 10.0f

    var shadowColor = 0x1000000

    lateinit var currentHeader:View


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topView = parent.getChildAt(0)
        if (topView == null) { return }

        val topViewPosition = parent.getChildAdapterPosition(topView)
        if (topViewPosition == RecyclerView.NO_POSITION) { return }

        val prevHeaderPosition = listener.getHeaderPositionForItem(topViewPosition)
        if (prevHeaderPosition == -1) { return }


        currentHeader = getHeaderViewForItem(topViewPosition, parent)
        fixLayoutSize(parent, currentHeader)

        val contactPoint = currentHeader.bottom

        val childInContact = getChildInContact(parent, contactPoint)
        if (childInContact == null) { return }

        if (listener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeader, childInContact)
            return
        }

        drawHeader(c, currentHeader)
    }

    private fun drawHeader(c: Canvas, header:View) {
        c.save()
        c.translate(0f,0f)
        header.draw(c)
        drawShadow(header,c)
        c.restore()
    }

    private fun drawShadow(target:View, c: Canvas) {
        val paint = Paint()
        paint.setShadowLayer(shadowRadius,0.0f,2.0f, shadowColor)
        val layoutParams = target.layoutParams
        c.drawRect(0.0f,0.0f,layoutParams.width.toFloat(),layoutParams.height.toFloat(),paint)
    }

    private fun moveHeader(c: Canvas, currentHeader:View, nextHeader:View) {
        c.save()
        val diff = nextHeader.top - currentHeader.height
        c.translate(0.0f,diff.toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent:RecyclerView): View {
        val headerPosition = listener.getHeaderPositionForItem(itemPosition)
        val layoutRestId = listener.getHeaderLayout(headerPosition)

        val header = LayoutInflater.from(parent.context).inflate(layoutRestId, parent, false)
        listener.bindHeaderData(header,headerPosition)
        return header
    }

    private fun getChildInContact(parent:RecyclerView, contactPoint:Int): View? {
        var childInContact:View? = null
        run exit@ {
            parent.children.forEach {
                if ((it.bottom > contactPoint) && (it.top <= contactPoint)) {
                    childInContact = it
                    return@exit
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view:View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec);
        view.layout(0,0,view.measuredWidth,view.measuredHeight)
    }
}

