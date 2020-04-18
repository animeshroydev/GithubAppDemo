package com.raywenderlich.android.w00tze.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.raywenderlich.android.w00tze.app.Constants.fullUrlString
import com.raywenderlich.android.w00tze.app.Injection
import com.raywenderlich.android.w00tze.app.isNullOrBlankOrNullString
import com.raywenderlich.android.w00tze.model.Gist
import com.raywenderlich.android.w00tze.model.Repo
import com.raywenderlich.android.w00tze.model.User
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

object RemoteRepository : Repository {

    private const val TAG = "RemoteRepository"

    // user name that we are fetching data from
//    private const val LOGIN = "animeshroydev"
    private const val LOGIN = "w00tze"

    private val api = Injection.provideGithubApi()

    override fun getRepos(): LiveData<List<Repo>> {
        val liveData = MutableLiveData<List<Repo>>()

        api.getRepos(LOGIN).enqueue(object : Callback<List<Repo>> {

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>?) {
                if (response != null) {
                    liveData.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return liveData
    }

    override fun getGists(): LiveData<List<Gist>> {
        val liveData = MutableLiveData<List<Gist>>()

        api.getGists(LOGIN).enqueue(object : Callback<List<Gist>> {

            override fun onResponse(call: Call<List<Gist>>, response: Response<List<Gist>>?) {
                if (response != null) {
                    liveData.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<Gist>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return liveData
    }

    override fun getUser(): LiveData<User> {
        val liveData = MutableLiveData<User>()

        api.getUser(LOGIN).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>?) {
                if (response != null) {
                    liveData.value = null
                }

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return liveData
    }
}