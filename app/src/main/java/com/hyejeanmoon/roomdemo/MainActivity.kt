package com.hyejeanmoon.roomdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hyejeanmoon.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnDeleteAndInsert.setOnClickListener {
            launch {
                AppDatabase.getInstance(applicationContext).getUserDao()
                    .deleteAllAndInsertUser(generateUser())
            }
        }

        binding.btnInsert.setOnClickListener {
            launch {
                AppDatabase.getInstance(applicationContext).getUserDao().insertUser(generateUser())
            }
        }

        binding.btnInsertOne.setOnClickListener {
            launch {
                AppDatabase.getInstance(applicationContext).getUserDao().insertOneUser(User(
                    id = 0,
                    firstName = "你好",
                    lastName = "世界",
                    birthday = Date(1589214300000),
                    nationality = "!!!"
                ))
            }
        }

        binding.btnDelete.setOnClickListener {
            launch {
                val allUsers = AppDatabase.getInstance(applicationContext).getUserDao().getAll()
                AppDatabase.getInstance(applicationContext).getUserDao().deleteUser(allUsers)
            }
        }

        binding.btnUpdate.setOnClickListener {
            launch {
                AppDatabase.getInstance(applicationContext).getUserDao().updateUser(
                    User(
                        id = 0,
                        firstName = "Hello",
                        lastName = "World",
                        birthday = Date(1589214300000),
                        nationality = "!!!"
                    )
                )
            }
        }

        binding.btnShow.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, ShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateUser(): List<User> {
        var users: ArrayList<User> = ArrayList()

        val firstNamePrefix = "firstName "
        val lastNamePrefix = "lastName "
        val nationalityPrefix = "nationality "

        for (i in 0 until 50) {
            val user = User(
                id = i,
                firstName = "$firstNamePrefix$i",
                lastName = "$lastNamePrefix$i",
                birthday = Date(1589214300000),
                nationality = "$nationalityPrefix$i"
            )
            users.add(user)
        }
        return users.toList()
    }
}
