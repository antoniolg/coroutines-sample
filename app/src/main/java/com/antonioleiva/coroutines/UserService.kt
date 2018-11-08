package com.antonioleiva.coroutines

import android.support.annotation.WorkerThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserService {

    fun doLoginAsync(username: String, password: String, callback: (User) -> Unit) {
        doAsync {
            val user = doLogin(username, password)
            uiThread {
                callback(user)
            }
        }
    }

    @WorkerThread
    fun doLogin(username: String, password: String): User {
        Thread.sleep(2000)
        return User(username, "http://lorempixel.com/200/200/people/1/")
    }

    fun requestCurrentFriendsAsync(user: User, callback: (List<User>) -> Unit) {
        doAsync {
            val friends = requestCurrentFriends(user)
            uiThread {
                callback(friends)
            }
        }
    }

    @WorkerThread
    fun requestCurrentFriends(user: User): List<User> {
        Thread.sleep(2000)
        return (1..10).map { User("Friend $it", "http://lorempixel.com/200/200/people/$it/") }
    }

    fun requestSuggestedFriendsAsync(user: User, callback: (List<User>) -> Unit) {
        doAsync {
            val friends = requestSuggestedFriends(user)
            uiThread {
                callback(friends)
            }
        }
    }

    @WorkerThread
    fun requestSuggestedFriends(user: User): List<User> {
        Thread.sleep(2000)
        return (1..10).map { User("Friend $it", "http://lorempixel.com/200/200/people/$it/") }
    }
}