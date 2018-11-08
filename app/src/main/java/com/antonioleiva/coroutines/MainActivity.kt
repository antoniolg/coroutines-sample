package com.antonioleiva.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener { doLogin(user.text.toString(), password.text.toString()) }
    }

    private fun doLogin(username: String, password: String) {

        progress.visibility = View.VISIBLE

        userService.doLoginAsync(username, password) { user ->

            userService.requestCurrentFriendsAsync(user) { friends ->

                val finalUser = user.copy(friends = friends)
                toast("User ${finalUser.name} has ${finalUser.friends.size} friends")

                progress.visibility = View.GONE
            }

        }

    }
}