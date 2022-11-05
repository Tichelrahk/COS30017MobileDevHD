package com.example.mealstoeat

import android.os.Parcelable
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.sql.Time

@Entity
data class Meal(
    @ColumnInfo(name = "meal_name") var meal_name: String,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "rating") var rating: Long,
    @ColumnInfo(name = "notes") var notes: String?
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

@Dao
interface MealDao {
    @Query("SELECT * FROM meal ORDER BY date DESC")
    fun loadAllMealsByTime(): List<Meal>

    @Query("SELECT * FROM meal ORDER BY rating DESC")
    fun loadAllMealsByRating(): List<Meal>

    @Query("SELECT * FROM meal ORDER BY meal_name ASC")
    fun loadAllMealsByName(): List<Meal>

    @Query("SELECT * FROM meal")
    fun loadAllMeals(): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(vararg meal: Meal)

    @Delete
    fun deleteMeal(vararg meal: Meal)

    @Query("SELECT * FROM meal WHERE id = :id")
    fun findMeal(id: Int): Meal

    @Update
    fun updateMeal(vararg  meal: Meal)
}