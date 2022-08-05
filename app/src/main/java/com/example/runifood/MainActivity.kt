package com.example.runifood

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runifood.databinding.ActivityMainBinding
import com.example.runifood.databinding.AddNewItemBinding
import com.example.runifood.databinding.RemoveItemBinding
import com.example.runifood.databinding.UpdateItemBinding

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // how to use recycler view :
        // 1. create view of recyclerView in activity_main.xml
        // 2. create item for recyclerView
        // 3. create adapter and view holder for recyclerView
        // 4. set adapter to recyclerView and set layout manager
        val listFood = arrayListOf(

            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://www.simplyrecipes.com/thmb/8caxM88NgxZjz-T2aeRW3xjhzBg=/2000x1125/smart/filters:no_upscale()/__opt__aboutcom__coeus__resources__content_migration__simply_recipes__uploads__2019__09__easy-pepperoni-pizza-lead-3-8f256746d649404baa36a44d271329bc.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),

            )
        myAdapter = FoodAdapter(listFood.clone() as ArrayList<Food>, this)

        binding.recyclerView.adapter = myAdapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.btnItemNewFood.setOnClickListener {
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
                    val randomUrlforPic = (0..11).random()
                    val imgrandom = listFood[randomUrlforPic].urlImage

                    val newFood = Food(
                        foodName,
                        foodDistance,
                        foodPrice,
                        foodLocation,
                        imgrandom,
                        foodRating,
                        ratingNum
                    )
                    myAdapter.addFood(newFood)

                    dialog.dismiss()
                    binding.recyclerView.scrollToPosition(0)
                } else {
                    Toast.makeText(this, "please enter your food information", Toast.LENGTH_LONG)
                        .show()
                }


            }
        }

        binding.edtSearch.addTextChangedListener { edittextinput ->

            if (edittextinput!!.isNotEmpty()){
                // filter the data
                val cloneList = listFood.clone() as ArrayList<Food>
                val filtredList = cloneList.filter { foodItem ->
                    foodItem.txtSubject.contains( edittextinput )
                }

                myAdapter.setData( ArrayList( filtredList ) )
            }else{
                // show all data when the edit text is empty
                myAdapter.setData( listFood.clone() as ArrayList<Food> )

            }

        }

    }
    // this is a function to update data
    override fun onFoodclicked(food: Food, position: Int) {

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
            ){

                val foodName = dialogUpdateBinding.dialogEdtNameFood.text.toString()
                val foodLocation = dialogUpdateBinding.dialogEdtNameLocation.text.toString()
                val foodPrice = dialogUpdateBinding.dialogEdtFoodPrice.text.toString()
                val foodDistance = dialogUpdateBinding.dialogEdtFoodDistance.text.toString()

                // create a new food to update the item
                val newFood = Food(foodName, foodDistance, foodPrice, foodLocation, food.urlImage, food.vote, food.rating)
                // update the item
                myAdapter.updateFood(newFood, position)
                dialogUpdate.dismiss()

            }else{
                Toast.makeText(this, "please enter all of input", Toast.LENGTH_LONG).show()
            }




        }

    }
    // this is a function to remove an item from list
    override fun onFoodLongclicked(food: Food, pos: Int) {
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
            dialogRemove.dismiss()

        }
    }
}