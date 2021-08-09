package com.example.kt_viewmodel.countlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kt_viewmodel.databinding.FragmentCountListBinding
import java.util.Arrays

/**
 * A fragment representing a list of Items.
 */
class CountListFragment: Fragment() {
    private val countListViewModel: CountListViewModel by viewModels()
    private lateinit var binding: FragmentCountListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("Fragment create View", "[call]")
        binding = FragmentCountListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Fragment View created", "[call]")
        super.onViewCreated(view, savedInstanceState)
        // recycler
        if (countListViewModel.itemList == null || countListViewModel.itemList!!.isEmpty()) {
            countListViewModel.itemList = dummyList
        }
        binding.countList.layoutManager = LinearLayoutManager(view.context)
        binding.countList.adapter = CountListRecyclerAdapter(this, countListViewModel)
        Log.d("Fragment View created", String.format("  - ViewModel:%s", countListViewModel.toString()))
    }

    private val dummyList: List<CountListItemValue>
        get() {
            val item1 = CountListItemValue("name1", "1", 1)
            val item2 = CountListItemValue("name2", "2", 2)
            val item3 = CountListItemValue("name3", "3", 3)
            val item4 = CountListItemValue("name4", "4", 4)
            val item5 = CountListItemValue("name5", "5", 5)
            val item6 = CountListItemValue("name6", "6", 6)
            val item7 = CountListItemValue("name7", "7", 7)
            val item8 = CountListItemValue("name8", "8", 8)
            val item9 = CountListItemValue("name9", "9", 9)
            val item10 = CountListItemValue("name10", "10", 10)
            return Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10)
        }

    companion object {
        fun newInstance(): CountListFragment {
            val fragment = CountListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}