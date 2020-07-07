package com.example.mvvm_kotlin_ut.userlist.viewmodel

import androidx.lifecycle.*
import com.example.mvvm_kotlin_ut.network.NetworkAPIService
import com.example.mvvm_kotlin_ut.userlist.model.Data
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class UserListViewModel(private val dispatcher: CoroutineDispatcher,
                        private val apiService: NetworkAPIService)
                        :ViewModel(),LifecycleObserver {

    var loading : MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    var userListMutableLiveData  = MutableLiveData<List<Data>>()
    var selectedUserMutableLiveData = MutableLiveData<Data>()


    fun fetchUserListInfo(page : Int) {
        viewModelScope.launch(dispatcher) {
            try{
                val response = apiService.fetchUsers(page)
                if(response.isSuccessful){
                    userListMutableLiveData.postValue(response.body()?.data)
                    loading.postValue(false)
                }else{
                    loading.postValue(false)
                    errorOnAPI.postValue("Something went wrong::${response.message()}")
                }

            }catch (e : Exception){
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }

        }
    }


    fun fetchUserDetailInfo(id : Int ) {

        viewModelScope.launch(dispatcher) {

            try{
                val response = apiService.fetchSelectedUsers(id)
                if(response.isSuccessful){
                    selectedUserMutableLiveData.postValue(response.body()?.data)
                    loading.postValue(false)
                }else{
                    loading.postValue(false)
                    errorOnAPI.postValue("Something went wrong::${response.message()}")
                }
            }catch (e : Exception){
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }
        }
    }



    fun fetchError(): LiveData<String> = errorOnAPI
    fun fetchLoadStatus(): LiveData<Boolean> = loading
    fun fetchUsersLiveData():LiveData<List<Data>> = userListMutableLiveData

}