package com.example.githubusersearchapps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private var recyclerView : RecyclerView? = null
    private var username : String = ""
    private var position : Int = 0
    private lateinit var mainViewModel : MainViewModel

    companion object{
        private const val TAG = "FollowerFragment"
        const val ARG_USERNAME = ""
        const val ARG_POSITION = "section_position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView = view.findViewById(R.id.rv_following)
        recyclerView?.layoutManager = layoutManager

        arguments?.let {
            username = it.getString(ARG_USERNAME).toString()
            position = it.getInt(ARG_POSITION)
        }

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        if (position == 1){
            val username = arguments?.getString(ARG_USERNAME)
            mainViewModel.findUserFollowing(username ?: "null")
            mainViewModel.userFollowing.observe(viewLifecycleOwner, {userFollowing ->
                setData(userFollowing)
            })
        } else if (position == 2){
            val username = arguments?.getString(ARG_USERNAME)
            mainViewModel.findUserFollowers(username ?: "null")
            mainViewModel.userFollower.observe(viewLifecycleOwner, { userFollowers ->
                setData(userFollowers)
            })
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun setData(followers : List<UserFollowersResponse>){
        val listUsername = ArrayList<String>()
        val listAvatarUrl = ArrayList<String>()
        val listId = ArrayList<Int>()
        for (follower in followers){
            listUsername.add(follower.login)
            listAvatarUrl.add(follower.avatarUrl)
            listId.add(follower.id)
        }
        val adapter = FollowingAdapter(listUsername, listId, listAvatarUrl)
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView?.layoutManager = layoutManager
        recyclerView = view?.findViewById(R.id.rv_following)
        recyclerView?.adapter = adapter
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading){
            val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
            progressBar?.visibility = View.VISIBLE
        } else {
            val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
            progressBar?.visibility = View.GONE
        }
    }
}