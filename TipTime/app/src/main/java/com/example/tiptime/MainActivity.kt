package com.example.tiptime

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        //    override fun onCreate(savedInstanceState: Bundle?) {
        //        super.onCreate(savedInstanceState)            ------>
        //                                                ^ binding = ActivityMainBinding.inflate(layoutInflater)
        //        setContentView(R.layout.activity_main)     -----> binding.root
        //    }
            private lateinit var binding: ActivityMainBinding
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)
                // binding.root --- > root of the hierarchy of views in your app
                setContentView(binding.root)
                binding.calculateButton.setOnClickListener{ calculateTip() }
//   comment this
                binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
                }
            }
             @RequiresApi(Build.VERSION_CODES.N)
             private fun calculateTip() {
                //val stringInTextField = binding.costOfService.text
                // Editable to a String
                val stringInTextField = binding.costOfServiceEditText.text.toString()
                val cost= stringInTextField.toDoubleOrNull()
                if (cost == null || cost == 0.0) {
                    displayTip(0.0)
                    return
                }
                val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
                    R.id.option_twenty_percent -> 0.20
                    R.id.option_eighteen_percent -> 0.18
                    else -> 0.15
                }
                var tip = cost * tipPercentage
                if (binding.roundUpSwitch.isChecked) {
                    tip = kotlin.math.ceil(tip)
                }
                displayTip(tip)
            }
            @RequiresApi(Build.VERSION_CODES.N)
            private fun displayTip(tip : Double) {
                val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
                //Tip Amount: %s <---> $10
                binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
            }
// Copy and paste this helper method into your MainActivity class. You can insert it right before
// the closing brace of the MainActivity class. The handleKeyEvent() is a private helper function
// that hides the onscreen keyboard if the keyCode input parameter is equal to KeyEvent.KEYCODE_ENTER.
// The InputMethodManager controls if a soft keyboard is shown, hidden, and allows the user to choose
// which soft keyboard is displayed. The method returns true if the key event was handled, and returns
// false otherwise.


            private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Hide the keyboard
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    return true
                }
                return false
            }
                /* Instead of using findViewById multiple time to get various view , we can create and initialize
                    a binding object once ---> ActivityMainBinding


                    // Old way with findViewById()
                        val myButton: Button = findViewById(R.id.my_button)
                        myButton.text = "A button"

                        // Better way with view binding
                        val myButton: Button = binding.myButton
                        myButton.text = "A button"

                        // Best way with view binding and no extra variable
                        binding.myButton.text = "A button"
            */

}
