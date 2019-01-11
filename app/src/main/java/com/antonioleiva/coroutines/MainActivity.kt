package com.antonioleiva.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener { doLogin(user.text.toString(), password.text.toString()) }
    }

    private fun doLogin(username: String, password: String) {

        GlobalScope.launch(Dispatchers.Main) {
            progress.visibility = View.VISIBLE

            val user = withContext(Dispatchers.IO) { userService.doLogin(username, password) }
            val currentFriends = withContext(Dispatchers.IO) { userService.requestCurrentFriends(user) }

            val finalUser = user.copy(friends = currentFriends)
            toast("User ${finalUser.name} has ${finalUser.friends.size} friends")

            progress.visibility = View.GONE
        }
    }
}