package jp.msb.kib.stickablerowheader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import jp.msb.kib.stickablerowheader.adapter.SampleAdapter
import jp.msb.kib.stickablerowheader.databinding.ActivityMainBinding
import jp.msb.kib.stickablerowheader.vm.SampleViewModel
import msb.jp.stikablerowheader.BaseItem
import msb.jp.stikablerowheader.StickableRowHeaderDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SampleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[SampleViewModel::class.java]

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        val layoutManager = LinearLayoutManager(this)
        val sampleAdapter = SampleAdapter()
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = sampleAdapter
        binding.recyclerView.addItemDecoration(StickableRowHeaderDecoration(sampleAdapter))
        binding.viewModel = viewModel
        viewModel.items.observe(this, Observer { items ->

            sampleAdapter.update(items)
        })

        viewModel.onCreate(this.baseContext)
    }
}

data class Row(val title: String) : BaseItem

data class Header(val title: String) : BaseItem

