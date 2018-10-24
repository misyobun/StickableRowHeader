package jp.msb.kib.stickablerowheader.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.msb.kib.stickablerowheader.Header
import jp.msb.kib.stickablerowheader.R
import jp.msb.kib.stickablerowheader.Row
import msb.jp.stikablerowheader.BaseItem

/**
 * Created by misyobun on 2018/10/23
 */
class SampleViewModel() : ViewModel() {
    private var mutableItems = MutableLiveData<List<BaseItem>>()
    val items = mutableItems
    fun onCreate(context: Context) {
        val value = ItemRepositoryImpl(context).fetchItems()
        mutableItems.postValue(value)
    }
}

interface ItemRepository {
    fun fetchItems(): List<BaseItem>
}

class ItemRepositoryImpl(val context: Context) : ItemRepository {
    override fun fetchItems(): List<BaseItem> {

        val items = ArrayList<BaseItem>()

        val header1 = Header(context.resources.getString(R.string.sanshiro_001_h))
        val header2 = Header(context.resources.getString(R.string.sanshiro_002_h))
        val header3 = Header(context.resources.getString(R.string.sanshiro_003_h))
        val header4 = Header(context.resources.getString(R.string.sanshiro_004_h))
        val header5 = Header(context.resources.getString(R.string.sanshiro_005_h))
        val header6 = Header(context.resources.getString(R.string.sanshiro_006_h))

        val sentence1 = Row(context.resources.getString(R.string.sanshiro_001))
        val sentence2 = Row(context.resources.getString(R.string.sanshiro_002))
        val sentence3 = Row(context.resources.getString(R.string.sanshiro_003))
        val sentence4 = Row(context.resources.getString(R.string.sanshiro_004))
        val sentence5 = Row(context.resources.getString(R.string.sanshiro_005))
        val sentence6 = Row(context.resources.getString(R.string.sanshiro_006))

        items.add(header1)
        items.add(sentence1)
        items.add(header2)
        items.add(sentence2)
        items.add(header3)
        items.add(sentence3)
        items.add(header4)
        items.add(sentence4)
        items.add(header5)
        items.add(sentence5)
        items.add(header6)
        items.add(sentence6)

        return items
    }
}