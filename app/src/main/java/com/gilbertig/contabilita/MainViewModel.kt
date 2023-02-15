package com.gilbertig.contabilita

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilbertig.contabilita.model.Data

class MainViewModel:ViewModel()
{

    private val mutableLiveData:MutableLiveData<Data> = MutableLiveData(Data())


    fun listenForUpdates():LiveData<Data> = mutableLiveData


    fun getData()=mutableLiveData.value!!

    fun changeVal(data: Data)
    {
        mutableLiveData.postValue(data)
    }

}