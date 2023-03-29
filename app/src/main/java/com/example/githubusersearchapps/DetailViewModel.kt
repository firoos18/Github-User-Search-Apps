package com.example.githubusersearchapps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userFollowing = MutableLiveData<List<UserFollowersResponse>>()
    val userFollowing : LiveData<List<UserFollowersResponse>> = _userFollowing

    private val _userFollower = MutableLiveData<List<UserFollowersResponse>>()
    val userFollower : LiveData<List<UserFollowersResponse>> = _userFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUserFollowers(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowersResponse>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponse>>,
                response: Response<List<UserFollowersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun findUserFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowersResponse>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponse>>,
                response: Response<List<UserFollowersResponse>>
            ) {
                if (response.isSuccessful) {
                   _isLoading.value = false
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }
}