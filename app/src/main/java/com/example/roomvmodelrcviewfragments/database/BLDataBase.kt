package com.example.roomvmodelrcviewfragments.database

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.example.roomvmodelrcviewfragments.*
import com.example.roomvmodelrcviewfragments.databinding.FragmentMainBinding
import com.example.roomvmodelrcviewfragments.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar

class BLDataBase {

    fun setDataBase(
        lifecycleOwner: LifecycleOwner,
        binding: FragmentMainBinding,
        dataBase: MainDataBase?
    ) {
        dataBase?.getDao()?.getAllProducts()?.asLiveData()?.observe(lifecycleOwner) { list ->
            showData(list, binding)
            addProduct(list, dataBase, binding)
            deleteProduct(dataBase, binding)
            clearListOfProducts(dataBase, binding)
        }
    }

    private fun showData(list: List<Product>, binding: FragmentMainBinding) {
        with(binding) {
            tvList.text = ""
            list.forEach {
                val text = "number: ${it.number}, name: ${it.name}, price: ${it.price}\n"
                tvList.append(text)
            }
        }

    }

    private fun addProduct(
        list: List<Product>,
        dataBase: MainDataBase,
        binding: FragmentMainBinding
    ) {
        with(binding) {
            btnSave.setOnClickListener {
                val product = Product(
                    setNumber(list, binding),
                    edName.text.toString(),
                    edPrice.text.toString()
                )
                Thread {
                    dataBase.getDao().insertProduct(product)
                }.start()
            }
        }
    }

    fun getProduct(
        dataBase: MainDataBase,
        viewLifecycleOwner: LifecycleOwner,
        viewModel: MainViewModel,
        binding: FragmentMainBinding,
        fragment: Fragment
    ) {
        with(binding) {
            btnChange.setOnClickListener {
                val searchName = edName.text.toString()
                dataBase.getDao().getAllProducts().asLiveData()
                    .observe(viewLifecycleOwner) { list ->
                        if (checkForItem(searchName, list)) {
                            viewModel.nameOfProduct.value = searchName
                            viewModel.priceOfProduct.value = getPrice(searchName, list)
                            fragment.replaceFragment(SecondFragment.newInstance())
                        } else {
                            Snackbar.make(
                                root,
                                "There is no such product",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    fun updateDataInDataBase(
        dataBase: MainDataBase,
        viewLifecycleOwner: LifecycleOwner,
        viewModel: MainViewModel,
        binding: FragmentSecondBinding,
        activity: FragmentActivity?,
        fragment: Fragment
    ) {
        dataBase.getDao().getAllProducts().asLiveData()
            .observe(viewLifecycleOwner) { list ->
                viewModel.nameOfProduct.observe(activity as LifecycleOwner) { name ->
                    binding.btnSaveChanges.setOnClickListener {
                        val newName = binding.edEditName.text.toString()
                        val newPrice = binding.edEditPrice.text.toString()
                        if (!checkForItem(newName, list) || name == newName) {
                            Thread {
                                dataBase.getDao()
                                    .updateProduct(getNumber(name, list), newName, newPrice)
                            }.start()
                            fragment.replaceFragment(MainFragment.newInstance())
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Such a product already exists",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
    }

    private fun Fragment.replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

    private fun deleteProduct(dataBase: MainDataBase, binding: FragmentMainBinding) {
        with(binding) {
            btnDelete.setOnClickListener {
                Thread {
                    dataBase.getDao().deleteProduct(edName.text.toString())
                }.start()
            }
        }
    }

    private fun clearListOfProducts(dataBase: MainDataBase, binding: FragmentMainBinding) {
        with(binding) {
            btnClearAll.setOnClickListener {
                tvList.text = ""
                Thread {
                    dataBase.getDao().deleteAllProducts()
                }.start()
            }
        }
    }

    private fun setNumber(list: List<Product>, binding: FragmentMainBinding): Int {
        with(binding) {
            val number = if (!checkForItem(edName.text.toString(), list)) {
                maxNumber(list) + 1
            } else {
                getNumber(edName.text.toString(), list)
            }
            return number
        }
    }

    private fun getNumber(name: String, list: List<Product>): Int {
        var number = 0
        list.forEach {
            when (name) {
                it.name -> number = it.number
            }
        }
        return number
    }

    private fun getPrice(name: String, list: List<Product>): String {
        var price = ""
        list.forEach {
            when (name) {
                it.name -> price = it.price
            }
        }
        return price
    }

    private fun maxNumber(list: List<Product>): Int {
        val listNumber: ArrayList<Int> = arrayListOf(0)
        list.forEach {
            it.number.let { number ->
                listNumber.add(number)
            }
        }
        return listNumber.max()
    }

    private fun checkForItem(name: String, list: List<Product>): Boolean {
        list.forEach {
            when (name) {
                it.name -> return true
            }
        }
        return false
    }
}