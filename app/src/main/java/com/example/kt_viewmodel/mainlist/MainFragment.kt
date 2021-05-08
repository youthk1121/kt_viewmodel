package com.example.kt_viewmodel.mainlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_viewmodel.R

/**
 * A fragment representing a list of Items.
 */
class MainFragment
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
    : Fragment() {
    private var listViewModel: ListViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var numberInputView: EditText? = null
    private var interActonListener: OnActivityInterActonListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActivityInterActonListener) {
            interActonListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Fragment create View", "[call]")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Fragment View created", "[call]")
        super.onViewCreated(view, savedInstanceState)
        // recycler
        listViewModel = ViewModelProvider(this, AndroidViewModelFactory(requireActivity().application)).get(ListViewModel::class.java)
        recyclerView = view.findViewById(R.id.list)
        val context = view.context
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        val recyclerViewAdapter = ItemRecyclerViewAdapter(viewLifecycleOwner)
        recyclerView?.setAdapter(recyclerViewAdapter)
        listViewModel!!.liveData.observe(viewLifecycleOwner, { list: List<ListItemValue?> -> recyclerViewAdapter.submitList(list) })

        // button
        numberInputView = view.findViewById(R.id.code_input)
        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener { v: View? ->
            if (recyclerView == null || recyclerView!!.adapter == null || numberInputView == null) {
                return@setOnClickListener
            }
            val number = numberInputView!!.text.toString()
            val index = listViewModel!!.getItemIndex(number)
            if (index < 0) {
                Toast.makeText(getContext(), "該当項目がありません", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val successDecrease = listViewModel!!.decreaseItemCount(index, 1)
            if (!successDecrease) {
                Toast.makeText(getContext(), "これ以上減らせません", Toast.LENGTH_SHORT).show()
            }
        }
        val clearButton = view.findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener { v: View? -> listViewModel!!.populateData() }
        val goButton = view.findViewById<Button>(R.id.go_to_count_list_button)
        goButton.setOnClickListener { v: View? ->
            if (interActonListener != null) {
                interActonListener!!.onSelectGoToCountList()
            }
        }
        Log.d("Fragment View created", String.format("  - ViewModel:%s", listViewModel.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listViewModel!!.saveList()
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