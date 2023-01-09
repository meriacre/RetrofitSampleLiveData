 package com.test.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.test.retrofitsample.model.AlbumItem
import com.test.retrofitsample.model.Albums
import retrofit2.Response
import retrofit2.create

 class MainActivity : AppCompatActivity() {

     lateinit var tv: TextView
     lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.tv_text)
        retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
      uploadAlbum()


    }

     private fun getRequestWithQueryParameter(){
         val responseLiveData: LiveData<Response<Albums>> = liveData {
             val response = retService.getSortedAlbums(3)
             emit(response)
         }

         responseLiveData.observe(this, Observer {
             val albumsList = it.body()?.listIterator()
             if (albumsList!=null){
                 while (albumsList.hasNext()){
                     val albumsItem = albumsList.next()
                     Log.d("MyTag", albumsItem.title)
                     val result = "" + "Album title: ${albumsItem.title}" + "\n" +
                             "" + "Album id: ${albumsItem.id}" + "\n" +
                             "" + "User id: ${albumsItem.userId}" + "\n\n\n"
                     tv.append(result)
                 }
             }
         })

     }

     private fun getRequestWithPathParameters(){
         //path parameter example:
         val pathResponse: LiveData<Response<AlbumItem>> = liveData {
             val response = retService.getAlbum(3)
             emit(response)
         }
         pathResponse.observe(this, Observer {
             val title = it.body()!!.title
             Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
         })
     }

     private fun uploadAlbum(){
         val album = AlbumItem(101, "Orice", 3)
         val postResponse: LiveData<Response<AlbumItem>> = liveData {
             val response = retService.updateAlbum(album)
             emit(response)
         }

         postResponse.observe(this, Observer {
             val receiveAlbumItem = it.body()
             val result = "" + "Album title: ${receiveAlbumItem!!.title}" + "\n" +
                     "" + "Album id: ${receiveAlbumItem.id}" + "\n" +
                     "" + "User id: ${receiveAlbumItem.userId}" + "\n\n\n"
             tv.text = result
         })

     }
}