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
import com.example.roomvmodelrcviewfragments.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val blDataBase = BLDataBase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBase = activity?.let { MainDataBase.getDataBase(it) }
        setText()
        dataBase?.let {
            blDataBase.updateDataInDataBase(
                it,
                viewLifecycleOwner,
                viewModel,
                binding,
                activity,
                this
            )
        }
        cancel()
    }

    private fun setText() {
        with(binding) {
            tvInfo.text = ""
            viewModel.nameOfProduct.observe(activity as LifecycleOwner) {
                edEditName.setText(it)
                val name = "NAME: $it\n"
                tvInfo.append(name)
            }
            viewModel.priceOfProduct.observe(activity as LifecycleOwner) {
                edEditPrice.setText(it)
                val price = "PRICE: $it"
                tvInfo.append(price)
            }
        }
    }

    private fun cancel() {
        binding.btnCancel.setOnClickListener {
            replaceFragment(MainFragment.newInstance())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SecondFragment()
    }

}