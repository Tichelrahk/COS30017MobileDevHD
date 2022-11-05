package com.example.mealstoeat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cloudinary.android.MediaManager
import com.squareup.picasso.Picasso
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import java.time.Clock
import java.time.ZoneId

class DetailActivity : AppCompatActivity() {

    private val previewImage by lazy { findViewById<ImageView>(R.id.detailImg) }

    private val getImageURIResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            previewImage.setImageURI(uri)
            previewImage.tag = uri.toString()
        }
    }

    private fun selectImage() = getImageURIResult.launch("image/*")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val receivedMeal = intent.getIntExtra("record", 0)

        val detailImg = findViewById<ImageView>(R.id.detailImg)
        val detailName = findViewById<EditText>(R.id.detailName)
        val detailRating = findViewById<EditText>(R.id.detailRating)
        val detailNotes = findViewById<EditText>(R.id.notes)
        val fabRemove = findViewById<View>(R.id.fab_remove)

        val mealDB = MealRepository(this)

        if (receivedMeal != 0){
            val meal = mealDB.findMeal(receivedMeal)
            detailName.setText(meal.meal_name)
            detailRating.setText(meal.rating.toString())

            Picasso.get().load(MediaManager.get().url().generate(meal.image)).into(detailImg)
            detailNotes.setText(meal.notes)


            fabRemove.setOnClickListener(){
                mealDB.deleteMeal(meal)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            detailName.setText("Create Meal")
        }

        detailImg.setOnClickListener(){
            selectImage()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        val mealDB = MealRepository(this)
        val receivedMeal = intent.getIntExtra("record", 0)

        val detailImg = findViewById<ImageView>(R.id.detailImg)
        val detailName = findViewById<EditText>(R.id.detailName)
        val detailRating = findViewById<EditText>(R.id.detailRating)
        val detailNotes = findViewById<EditText>(R.id.notes)
        val time = Clock.system(ZoneId.of("Australia/Melbourne")).millis()

        var meal = Meal("","", time, 0, "")


        if (receivedMeal != 0) {
            meal = mealDB.findMeal(receivedMeal)
        }

        detailImg.tag?.let {
            uploadImg(detailImg, meal)
        }

        meal.meal_name = detailName.text.toString()
        meal.rating = detailRating.text.toString().toLong()
        meal.notes = detailNotes.text.toString()

        mealDB.insertMeal(meal)
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {

        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }

        return uri.path?.substring(uri.path!!.lastIndexOf('/') + 1)
    }

    private fun uploadImg(detailImg: ImageView, meal: Meal) {
        val imgPath = detailImg.tag.toString()

            val imgName = getFileName(this, Uri.parse(imgPath))?.substringBeforeLast(".")
            val pId = "School/COS30017MobileDev/${imgName}"

            val requestId = MediaManager.get().upload(Uri.parse(imgPath))
                .unsigned("fllrk1nz")
                .option("public_id", pId)
                .dispatch();

            meal.image = pId
    }
}