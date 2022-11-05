package com.example.mealstoeat

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class MealRepository(context: Context) {
    var db: MealDao = AppDatabase.getInstance(context)?.mealDao()!!

    fun loadAllMeals(): List<Meal> {
        return db.loadAllMeals()
    }

    fun insertMeal(meal: Meal){
        db.insertMeal(meal)
    }

    fun deleteAll(){
        val meals = loadAllMeals()
        for (meal in meals){
            db.deleteMeal(meal)
        }
    }

    fun findMeal(id: Int): Meal{
        return db.findMeal(id)
    }

    fun updateMeal(meal: Meal) {
        db.updateMeal(meal)
    }

    fun deleteMeal(meal: Meal) {
        db.deleteMeal(meal)
    }
}