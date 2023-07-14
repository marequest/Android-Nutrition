package com.mareq.ispitmealapp.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.database.UserEntity
import com.mareq.ispitmealapp.presentation.contract.IngredientContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.contract.UserContract
import com.mareq.ispitmealapp.presentation.viewmodel.IngredientViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val userViewModel : UserContract.UserViewModel by viewModel<UserViewModel>()
    private val mealsViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()
        makeFirstuser()

        supportActionBar?.hide();

        Handler().postDelayed({
            val homeScreen = Intent(this@MainActivity, HomeActivity::class.java)
            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)

            if(checkLogin(context = this.applicationContext)){
                startActivity(homeScreen)
            }else{
                startActivity(loginIntent)
            }

        }, 1000)
    }

    private fun makeFirstuser(){
        val list = mutableListOf(
            UserEntity(
                username = "123123",
                password = "123123",
                email = "mjovicic1720@raf.rs",
                firstName = "Marko",
                lastName = "Jovicic",
                profilePicture = "123.png"
            )
        )
        userViewModel.insertAll(list)
    }

    private fun checkLogin(context: Context): Boolean{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val userID: String = sharedPreferences.getString("userID", "").toString()

        return !(userID.isEmpty() || userID.trim() == "")
    }

    private fun initObservers() = with(mealsViewModel) {
        fetchDbMeal.observe(this@MainActivity) { meal ->
            println("ID ${meal.id}")
        }
    }
}