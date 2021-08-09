package com.example.kt_viewmodel.mainlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kt_viewmodel.R
import com.example.kt_viewmodel.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class MainFragment: Fragment() {
    private val listViewModel: ListViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    private lateinit var interActonListener: OnActivityInterActonListener
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActivityInterActonListener) {
            interActonListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("Fragment create View", "[call]")
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Fragment View created", "[call]")
        super.onViewCreated(view, savedInstanceState)
        // recycler
        binding.list.layoutManager = LinearLayoutManager(view.context)
        val recyclerViewAdapter = ItemRecyclerViewAdapter(viewLifecycleOwner)
        binding.list.adapter = recyclerViewAdapter
        listViewModel.itemList.observe(viewLifecycleOwner, { list -> recyclerViewAdapter.submitList(list) })

        binding.button.setOnClickListener {
            if (binding.list.adapter == null) {
                return@setOnClickListener
            }
            val number = binding.codeInput.text.toString()
            val index = listViewModel.getItemIndex(number)
            if (index < 0) {
                Toast.makeText(requireContext(), "該当項目がありません", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val successDecrease = listViewModel.decreaseItemCount(index, 1)
            if (!successDecrease) {
                Toast.makeText(requireContext(), "これ以上減らせません", Toast.LENGTH_SHORT).show()
            }
        }
        val clearButton = view.findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener { listViewModel.populateData() }
        val goButton = view.findViewById<Button>(R.id.go_to_count_list_button)
        goButton.setOnClickListener {
            interActonListener.onSelectGoToCountList()
            
        }
        Log.d("Fragment View created", String.format("  - ViewModel:%s", listViewModel.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listViewModel.saveList()
    }

    interface OnActivityInterActonListener {
        fun onSelectGoToCountList()
    }

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}