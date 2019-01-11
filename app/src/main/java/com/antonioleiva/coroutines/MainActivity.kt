package com.antonioleiva.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.toast
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        login.setOnClickListener { doLogin(user.text.toString(), password.text.toString()) }
    }

    private fun doLogin(username: String, password: String) {

        launch {
            progress.visibility = View.VISIBLE

            val user = withContext(Dispatchers.IO) { userService.doLogin(username, password) }
            val currentFriends = async(Dispatchers.IO) { userService.requestCurrentFriends(user) }
            val suggestedFriends = async(Dispatchers.IO) { userService.requestSuggestedFriends(user) }

            val finalUser = user.copy(friends = currentFriends.await() + suggestedFriends.await())
            toast("User ${finalUser.name} has ${finalUser.friends.size} friends")

            progress.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}