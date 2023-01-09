package com.test.retrofitsample

import com.test.retrofitsample.model.AlbumItem
import com.test.retrofitsample.model.Albums
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id") albumId: Int ): Response<AlbumItem>

    @POST("/albums")
    suspend fun updateAlbum(@Body album: AlbumItem): Response<AlbumItem>

}