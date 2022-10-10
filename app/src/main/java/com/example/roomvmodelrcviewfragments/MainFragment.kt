package com.example.roomvmodelrcviewfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.roomvmodelrcviewfragments.database.BLDataBase
import com.example.roomvmodelrcviewfragments.database.MainDataBase
import com.example.roomvmodelrcviewfragments.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val blDataBase = BLDataBase()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBase = activity?.let { MainDataBase.getDataBase(it) }
        blDataBase.setDataBase(this, binding, dataBase)
        if (dataBase != null) {
            blDataBase.getProduct(dataBase, this, viewModel, binding, this)
        }
    }



    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}