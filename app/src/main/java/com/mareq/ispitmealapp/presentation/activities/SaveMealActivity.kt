package com.mareq.ispitmealapp.presentation.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import com.mareq.ispitmealapp.data.model.entities.Meal
import com.mareq.ispitmealapp.presentation.contract.IngredientContract
import com.mareq.ispitmealapp.presentation.viewmodel.IngredientViewModel
import com.mareq.ispitmealapp.presentation.dialogs.OpenCameraDialog

class SaveMealActivity : AppCompatActivity() {

    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()
    private val ingredientsViewModel : IngredientContract.IngredientViewModel by viewModel<IngredientViewModel>()

    private lateinit var mealImage : ImageView
    private lateinit var mealTitle : TextView
    private lateinit var saveMealButton : Button

    private lateinit var dateButton: Button
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var numberPicker: NumberPicker

    private var mealId : Long = 0
    private var CAMERA_PIC_REQUEST : Int = 1337
    private lateinit var selectedMeal : Meal
    private var daySelected : String = ""
    private var fetchMeal : MealEntity? = null
    private var mealDate : Long = 0
    private var mealType : String? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_meal)

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        init()
    }

    private fun init() {
        initViews()
        initDatePicker()
        initObservers()
        initListeners()

        mealId = intent.getLongExtra("id", 0)
        init(mealId)
    }

    private fun initViews(){
        mealImage = findViewById(R.id.ivMealItemImage)
        mealTitle = findViewById(R.id.tvMealTitle)
        saveMealButton = findViewById(R.id.saveUserMealButton)
        dateButton = findViewById(R.id.datePickerButton)
        dateButton.text = getTodaysDate();
        numberPicker = findViewById(R.id.categoryPicker)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val imageBitmap = data.extras?.get("data") as? Bitmap
                if (imageBitmap != null) {
                    mealImage.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    private fun initListeners(){
        dateButton.setOnClickListener { datePickerDialog.show() }

        mealImage.setOnClickListener {
            val saveMealDialog: OpenCameraDialog = OpenCameraDialog()
            saveMealDialog.show(supportFragmentManager, "tag")
        }

        saveMealButton.setOnClickListener {
            prepareMeal()
        }

        val kategorija = arrayOf<String>("doručak", "ručak", "užina", "večera")
        numberPicker.maxValue = kategorija.size - 1
        numberPicker.minValue = 0
        numberPicker.displayedValues = kategorija

        numberPicker.setOnValueChangedListener(NumberPicker.OnValueChangeListener(){ numberPicker, oldValue, newValue ->
            mealType = kategorija[newValue]
        })
    }

    private fun initDatePicker(){
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                val date = makeDateString(day, month, year)
                dateButton.text = date

                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.time)
                val unixTimestamp = selectedDate.timeInMillis / 1000

                daySelected = dayOfWeek
                mealDate = unixTimestamp
            }

        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)

    }

    private fun prepareMeal() {
        val mealEntity = MealEntity(
            id = 0,
            mealTitle = selectedMeal.mealTitle,
            mealThumbnail = bitmapToBase64(getBitmapFromImageView(imageView = mealImage)),
            tags = selectedMeal.tags,
            ytLink = selectedMeal.ytLink,
            day = daySelected,
            date = mealDate,
            category = selectedMeal.category,
            userId = sharedPreferences?.getString("userID", "").toString(),
            mealType = mealType ?: ""
        )

        mealViewModel.insert(mealEntity)
        mealViewModel.getMealByTitle(selectedMeal.mealTitle)
    }

    private fun getBitmapFromImageView(imageView : ImageView) : Bitmap? {
        val drawable = imageView.drawable ?: return null

        return if(drawable is BitmapDrawable){
            drawable.bitmap
        }else{
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0,0,canvas.width,canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun clearListOfEmptyIngredients(ingredients : List<Ingredient>) : List<Ingredient> {
        return ingredients.filter {it.ingredientName.isNotEmpty() || it.measure.isNotEmpty()}
    }

    private fun extractIngredients(ingredients: List<Ingredient>, mID: Long?) : List<IngredientEntity> {
        return ingredients.map {
            ingredient ->
            IngredientEntity(
                id = 0,
                mealId = mID!!,
                ingredientName = ingredient.ingredientName,
                measure = ingredient.measure
            )
        }
    }

    private fun init(id : Long){
        makeCall(id)
    }

    private fun makeCall(id : Long) = with(mealViewModel){
        getMealById(id)
    }

    private fun bitmapToBase64(bitmap: Bitmap?) : String {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64String : String) : Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


    private fun initObservers() = with(mealViewModel){
        meal.observe(this@SaveMealActivity) { meal ->
            selectedMeal = meal[0]
            mealImage.let {
                Glide
                    .with(this@SaveMealActivity)
                    .load(meal[0].mealThumbnail)
                    .error("Not Available")
                    .into(it)
            }
            mealTitle.text = meal[0].mealTitle
        }

        fetchDbMeal.observe(this@SaveMealActivity) { fetchedMeal ->
            fetchMeal = fetchedMeal
            fetchMeal?.let {
                var ingrds = clearListOfEmptyIngredients(selectedMeal.ingredients)
                val id = fetchMeal?.id
                var ingredientsList = extractIngredients(ingrds, id)
                ingredientsViewModel.insertAll(ingredientsList)
                Toast.makeText(this@SaveMealActivity, "Successfully Saved Meal", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getMonthFormat(month: Int): String? {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"
    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month += 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "${getMonthFormat(month)} $day $year"
    }
}