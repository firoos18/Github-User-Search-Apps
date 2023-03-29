package com.example.githubusersearchapps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    private val _userData = MutableLiveData<UserDetailResponse>()
    val userData : LiveData<UserDetailResponse> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "USERDETAILVIEWMODEL"
    }

    fun findUserDetails(login : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object : retrofit2.Callback<UserDetailResponse>{
            override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }
}