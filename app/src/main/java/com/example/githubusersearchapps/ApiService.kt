package com.example.githubusersearchapps

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers ("Authorization: token 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")
    @GET("search/users?")
    fun getUsers(
        @Query("q") q : String
    ) : Call<SearchResponse>

    @Headers("Authorization: token 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")
    @GET("users/{user}")
    fun getUserDetail(
        @Path("user") user : String
    ) : Call<UserDetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{user}/followers")
    fun getUserFollowers(
        @Path("user") user : String
    ) : Call<List<UserFollowersResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{user}/following")
    fun getUserFollowing(
        @Path("user") user : String
    ) : Call<List<UserFollowersResponse>>
}