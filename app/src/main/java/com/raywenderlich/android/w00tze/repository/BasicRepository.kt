package com.raywenderlich.android.w00tze.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.raywenderlich.android.w00tze.app.Constants.fullUrlString
import com.raywenderlich.android.w00tze.model.Gist
import com.raywenderlich.android.w00tze.model.Repo
import com.raywenderlich.android.w00tze.model.User
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

object BasicRepository : Repository {

    private const val TAG = "BasicRepository"

    // user name that we are fetching data from
    private const val LOGIN = "animeshroydev"

    override fun getRepos(): LiveData<List<Repo>> {
        val liveData = MutableLiveData<List<Repo>>()
        FetchRepoAsyncTask { repos ->
            liveData.value = repos
        }.execute()
        return liveData
    }

    override fun getGists(): LiveData<List<Gist>> {
        val liveData = MutableLiveData<List<Gist>>()
        FetchGistsAsyncTask { gists ->
            liveData.value = gists
        }.execute()
        return liveData
    }

    override fun getUser(): LiveData<User> {
        val liveData = MutableLiveData<User>()

        val user = User(
                1234L,
                "w00tze",
                "w00tze",
                "W00tzeWootze",
                "https://avatars0.githubusercontent.com/u/36771440?v=4")

        liveData.value = user

        return liveData
    }

    // get repos
    private fun fetchRepos() : List<Repo>? {
        try {
            val url = Uri.parse(fullUrlString("/users/${LOGIN}/repos")).toString()
            val jsonString = getUrlAsString(url)

            Log.i(TAG, "Repo data: $jsonString")

            return parseRepos(jsonString)
        } catch (e: IOException) {
            Log.e(TAG, "Error retrieving repos: ${e.localizedMessage}")
        }
        catch (e: JSONException) {
            Log.e(TAG, "Error retrieving repos: ${e.localizedMessage}")
        }
    return null
    }

    // parse JSON repos
    private fun parseRepos(jsonString: String): List<Repo> {
        val repos = mutableListOf<Repo>()

        val reposArray = JSONArray(jsonString)
        for (i in 0 until reposArray.length()) {
            val repoObject = reposArray.getJSONObject(i)
            // getting the repo names of now
            val repo = Repo(repoObject.getString("name"))
            repos.add(repo)
        }
        return repos
    }

    // all network request must me in background
    private class FetchRepoAsyncTask(val callback: ReposCallback) : AsyncTask<ReposCallback, Void, List<Repo>>() {
        override fun doInBackground(vararg params: ReposCallback?): List<Repo>? {
            return fetchRepos()
        }

        override fun onPostExecute(result: List<Repo>?) {
            super.onPostExecute(result)
            if (result != null) {
                callback(result)
            }
        }
    }

    // get gists
    private fun fetchGists() : List<Gist>? {
        try {
            val url = Uri.parse(fullUrlString("/users/${LOGIN}/gists")).toString()
            val jsonString = getUrlAsString(url)

            Log.i(TAG, "Gists data: $jsonString")

            val gists = mutableListOf<Gist>()

            for (i in 0 until 100) {
                val gist = Gist("2018-02-23T17:42:52Z", "w00t")
                gists.add(gist)
            }

            return gists
        } catch (e: IOException) {
            Log.e(TAG, "Error retrieving gists: ${e.localizedMessage}")
        }
        return null
    }

    // all network request must me in background
    private class FetchGistsAsyncTask(val callback: GistsCallback) : AsyncTask<GistsCallback, Void, List<Gist>>() {
        override fun doInBackground(vararg params: GistsCallback?): List<Gist>? {
            return fetchGists()
        }

        override fun onPostExecute(result: List<Gist>?) {
            super.onPostExecute(result)
            if (result != null) {
                callback(result)
            }
        }
    }
}