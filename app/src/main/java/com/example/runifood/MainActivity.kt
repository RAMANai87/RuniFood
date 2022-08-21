package com.example.runifood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runifood.databinding.ActivityMainBinding
import com.example.runifood.databinding.AddNewItemBinding
import com.example.runifood.databinding.RemoveItemBinding
import com.example.runifood.databinding.UpdateItemBinding
import com.example.runifood.room.Food
import com.example.runifood.room.InterFaceDao
import com.example.runifood.room.MyDataBase

const val URL_IMAGE_BASIC = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"
// how to use recycler view :
// 1. create view of recyclerView in activity_main.xml
// 2. create item for recyclerView
// 3. create adapter and view holder for recyclerView
// 4. set adapter to recyclerView and set layout manager

@Suppress("UNCHECKED_CAST", "UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    private lateinit var foodDao: InterFaceDao
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        foodDao = MyDataBase.getDataBase(this).foodDao

        // for know user use the app for first time
        val sharedPreferences = getSharedPreferences("RuniFood", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("firstRun", true)) {
            firstRun()
            sharedPreferences.edit().putBoolean("firstRun", false).apply()
        }

        showAllData()

        binding.btnClearAllFood.setOnClickListener {
            removeAllFood()
        }
        binding.btnItemNewFood.setOnClickListener {
            addNewFoodDataBase()
        }
        binding.edtSearch.addTextChangedListener {
            searchOnDataBase(it.toString())
        }

    }

    // this is a function to search on food to find them
    private fun searchOnDataBase(editText: String) {
        if (editText.isNotEmpty()) {
            val searchOnFood = foodDao.searchFoods(editText)
            myAdapter.setData(ArrayList(searchOnFood))
        } else {
            // show all data when the edit text is empty
            val listFood = foodDao.getAllFoods()
            myAdapter.setData(ArrayList(listFood))

        }
    }
    // for add a new food to database and recycler view
    private fun addNewFoodDataBase() {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = AddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.btnDone.setOnClickListener {
            if (
                dialogBinding.foodName.length() > 0 &&
                dialogBinding.foodDistance.length() > 0 &&
                dialogBinding.foodLocation.length() > 0 &&
                dialogBinding.foodPrice.length() > 0
            ) {
                val foodName = dialogBinding.foodName.text.toString()
                val foodLocation = dialogBinding.foodLocation.text.toString()
                val foodPrice = dialogBinding.foodPrice.text.toString()
                val foodDistance = dialogBinding.foodDistance.text.toString()
                val foodRating: Int = (1..150).random()
                val ratingNum: Float = (1..5).random().toFloat()
                val randomUrlforPic = (1 until 12).random()
                val imgrandom = "$URL_IMAGE_BASIC$randomUrlforPic.jpg"

                val newFood = Food(
                    txtSubject = foodName,
                    txtDistance = foodDistance,
                    txtPrice = foodPrice,
                    FoodLocation = foodLocation,
                    urlImage = imgrandom,
                    vote = foodRating,
                    rating = ratingNum
                )

                myAdapter.addFood(newFood)
                foodDao.insertFood(newFood)
                showAllData()

                dialog.dismiss()
                binding.recyclerView.scrollToPosition(0)

            } else {
                Toast.makeText(this, "please enter your food information", Toast.LENGTH_LONG)
                    .show()
            }


        }
    }
    // use for remove all data in database
    private fun removeAllFood() {

        foodDao.deleteAllFoods()
        showAllData()

    }
    private fun showAllData() {

        val foodData = foodDao.getAllFoods()

        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }
    // this is a function to show all food in first time
    private fun firstRun() {

        val listFood = arrayListOf(
            Food(
                txtSubject = "Hamburger",
                txtDistance = "15",
                txtPrice = "3",
                FoodLocation = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                vote = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtDistance = "20",
                txtPrice = "2.1",
                FoodLocation = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                vote = 10,
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtDistance = "40",
                txtPrice = "1.4",
                FoodLocation = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                vote = 30,
                rating = 2f
            ),
            Food(
                txtSubject = "pizza",
                txtDistance = "10",
                txtPrice = "2.5",
                FoodLocation = "Zahedan, Iran",
                urlImage = "https://www.simplyrecipes.com/thmb/8caxM88NgxZjz-T2aeRW3xjhzBg=/2000x1125/smart/filters:no_upscale()/__opt__aboutcom__coeus__resources__content_migration__simply_recipes__uploads__2019__09__easy-pepperoni-pizza-lead-3-8f256746d649404baa36a44d271329bc.jpg",
                vote = 80,
                rating = 1.5f
            ),
            Food(
                txtSubject = "Sushi",
                txtDistance = "20",
                txtPrice = "3.2",
                FoodLocation = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                vote = 200,
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtDistance = "40",
                txtPrice = "3.7",
                FoodLocation = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                vote = 50,
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtDistance = "70",
                txtPrice = "3.5",
                FoodLocation = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                vote = 70,
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtDistance = "12",
                txtPrice = "3.6",
                FoodLocation = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                vote = 40,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtDistance = "10",
                txtPrice = "3.7",
                FoodLocation = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                vote = 15,
                rating = 5f
            ),
            Food(
                txtSubject = "Baryooni",
                txtDistance = "16",
                txtPrice = "10",
                FoodLocation = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                vote = 28,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtDistance = "11.5",
                txtPrice = "7.5",
                FoodLocation = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                vote = 27,
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtDistance = "12.5",
                txtPrice = "2.4",
                FoodLocation = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                vote = 35,
                rating = 2.5f
            )

        )
        foodDao.insertAllFood(listFood)

    }
    // this is a function to update data
    override fun onFoodClicked(food: Food, position: Int) {

        val dialogUpdate = AlertDialog.Builder(this).create()
        val dialogUpdateBinding = UpdateItemBinding.inflate(layoutInflater)
        dialogUpdate.setView(dialogUpdateBinding.root)
        dialogUpdate.setCancelable(true)
        dialogUpdate.show()

        dialogUpdateBinding.dialogEdtNameFood.setText(food.txtSubject)
        dialogUpdateBinding.dialogEdtNameLocation.setText(food.FoodLocation)
        dialogUpdateBinding.dialogEdtFoodDistance.setText(food.txtDistance)
        dialogUpdateBinding.dialogEdtFoodPrice.setText(food.txtPrice)

        dialogUpdateBinding.btnUpdateCancel.setOnClickListener {

            dialogUpdate.dismiss()

        }

        dialogUpdateBinding.btnUpdateDoneItem.setOnClickListener {
            if (
                dialogUpdateBinding.dialogEdtNameFood.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodPrice.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodDistance.length() > 0 &&
                dialogUpdateBinding.dialogEdtNameLocation.length() > 0
            ) {

                val foodName = dialogUpdateBinding.dialogEdtNameFood.text.toString()
                val foodLocation = dialogUpdateBinding.dialogEdtNameLocation.text.toString()
                val foodPrice = dialogUpdateBinding.dialogEdtFoodPrice.text.toString()
                val foodDistance = dialogUpdateBinding.dialogEdtFoodDistance.text.toString()

                // create a new food to update the item
                val newFood = Food(
                    id = food.id,
                    txtSubject = foodName,
                    txtDistance = foodDistance,
                    txtPrice = foodPrice,
                    FoodLocation = foodLocation,
                    urlImage = food.urlImage,
                    vote = food.vote,
                    rating = food.rating
                )
                // update the item
                myAdapter.updateFood(newFood, position)
                foodDao.updateFood(newFood)
                dialogUpdate.dismiss()

            } else {
                Toast.makeText(this, "please enter all of input", Toast.LENGTH_LONG).show()
            }


        }

    }
    // this is a function to remove an item from list
    override fun onFoodLongClicked(food: Food, pos: Int) {
        val dialogRemove = AlertDialog.Builder(this).create()
        val dialogRemoveBinding = RemoveItemBinding.inflate(layoutInflater)
        dialogRemove.setView(dialogRemoveBinding.root)
        dialogRemove.setCancelable(true)
        dialogRemove.show()

        dialogRemoveBinding.btnRemoveNo.setOnClickListener {
            dialogRemove.dismiss()
        }
        dialogRemoveBinding.btnRemoveYes.setOnClickListener {

            myAdapter.removeFood(food, pos)
            foodDao.deleteFood(food)
            showAllData()
            dialogRemove.dismiss()

        }
    }
}