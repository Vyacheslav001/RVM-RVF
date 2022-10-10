package com.example.roomvmodelrcviewfragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val nameOfProduct: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val priceOfProduct: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}