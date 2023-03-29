package com.example.githubusersearchapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersearchapps.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserDetailBinding
    private lateinit var userDetailViewModel : UserDetailViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
        private const val TAG = "UserDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "User Details"

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager : ViewPager2 = findViewById(R.id.view_pager)

        val username = intent.getStringExtra("username")
        sectionsPagerAdapter.username = username ?: "null"

        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        userDetailViewModel.findUserDetails(username ?: "null")

        userDetailViewModel.userData.observe(this, { userData ->
            setUserDetails(userData)
        })

        userDetailViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        
        viewPager.adapter = sectionsPagerAdapter
        val tabs : TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) {tabs, position ->
            if (position == 0){
                tabs.text = resources.getString(TAB_TITLES[position])
            } else {
                tabs.text = resources.getString(TAB_TITLES[position])
            }
        }.attach()

        actionBar.elevation = 10f

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setUserDetails(user : UserDetailResponse){
        binding.tvLogin.text = user.login
        binding.tvName.text = user.name
        binding.tvLocation.text = "\uD83D\uDCCD${user.location}"
        binding.tvUserFollowing.text = "${user.following} Following"
        binding.tvUserFollower.text = "${user.followers} Followers"
        Glide.with(this).load(user.avatarUrl).into(binding.avatarImg)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}