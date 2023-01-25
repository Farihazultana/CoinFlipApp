package com.example.coinflipapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import org.w3c.dom.Text
import kotlin.math.round

class MainActivity : AppCompatActivity() {
//    Initialize variables and define them
        private lateinit var coinImage: ImageView
        private lateinit var totalCount: TextView
        private lateinit var headsCount: TextView
        private lateinit var tailsCount: TextView
        private lateinit var headsPercent: TextView
        private lateinit var tailsPercent: TextView
        private lateinit var headsProgressBar: ProgressBar
        private lateinit var tailsProgressBar: ProgressBar
        private lateinit var simNumber: EditText

        private lateinit var simButton: Button

//    Counter variable to keep track of heads, tails, and total flips
        private var heads = 0
        private var tails = 0
        private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Setting a reference to our switch and buttons
        val simSwitch: SwitchCompat = findViewById(R.id.main_activity_sw_simulate)
        val flipButton: Button = findViewById(R.id.main_activity_bt_flip)
        val resetButton: Button = findViewById(R.id.main_activity_bt_reset)
        simButton = findViewById(R.id.main_activity_bt_simulate)

//        setting  listeners for our buttons
        simSwitch.setOnCheckedChangeListener{ buttonView, isChecked -> enableSim(isChecked) }
        flipButton.setOnClickListener { flip() }
        resetButton.setOnClickListener{ reset() }
        simButton.setOnClickListener { sim() }

//        set values to other views
        coinImage = findViewById(R.id.main_activity_iv_icon)
        totalCount = findViewById(R.id.main_activity_tv_total_flips)
        headsCount = findViewById(R.id.activity_main_tv_total_heads)
        tailsCount = findViewById(R.id.main_activity_tv_total_tails)
        headsPercent = findViewById(R.id.main_activity_tv_heads_percent)
        tailsPercent = findViewById(R.id.main_activity_tv_tails_percent)
        headsProgressBar = findViewById(R.id.main_activity_pb_heads_percent)
        tailsProgressBar = findViewById(R.id.main_activity_pb_tails_percent)
        simNumber = findViewById(R.id.main_activity_et_sim_number)
    }

//        Turn on/off simulation mode
        private fun enableSim(onState: Boolean){
//            Get the state of the switch
            if (onState){
//                Log.i("test","Switch is on!")
                simNumber.visibility = View.VISIBLE
                simButton.visibility = View.VISIBLE
            }else {
//                Log.i("test", "Switch is off!")
                simNumber.visibility = View.INVISIBLE
                simButton.visibility = View.INVISIBLE
            }
        }

//        Simulate a single coin flip
        private fun flip(){
            val randomNumber = (0..1).random()
//            update based on value
              if (randomNumber == 0) {
                  update("heads")
              }
              else {
                  update("tails")
              }
        }

//    Update the UI based on the previous coin flip
          private fun update(coinValue: String){
//              set the correct image for our coin flip
              if (coinValue == "heads"){
                  heads++
                  coinImage.setImageResource(R.drawable.ic_heads_icon)
              }
              else{
                  tails++
                  coinImage.setImageResource(R.drawable.ic_tails_icon)
              }
//              Increment total flips
                total++
//              Update textView to show results
                totalCount.text = "Total Flips: $total"
                headsCount.text = "Total Heads: $heads"
                tailsCount.text = "Total Tails: $tails"
//              Update the statistics
                updateStatistics()
          }
//          Update the statistics UI based on the previous coin flip
            private fun updateStatistics(){
                var headsPercentResult = 0.0
                var tailsPercentResult = 0.0

                if(total != 0) {
                     headsPercentResult = round((heads.toDouble() / total) * 10000)/100
                     tailsPercentResult = round((tails.toDouble() / total) * 10000)/100
                }

                headsPercent.text = "Heads: $headsPercentResult %"
                tailsPercent.text = "Tails: $tailsPercentResult %"

//              Update progress bars
                headsProgressBar.progress = headsPercentResult.toInt()
                tailsProgressBar.progress = tailsPercentResult.toInt()
            }



//        Reset all data for the simulation
        private fun reset(){
//        Change the imageView bhack tyo default
            coinImage.setImageResource(R.drawable.ic_flip_icon)

//          Set all counter vars back to 0
            total = 0
            heads = 0
            tails = 0

//          Update textView to show results
            totalCount.text = "Total Flips: $total"
            headsCount.text = "Total Heads: $heads"
            tailsCount.text = "Total Tails: $tails"

//          Update the statistics UI
            updateStatistics()
        }
//        Run the coin simulation for a number of flips
        private fun sim(){
              var numberToSim = 1
              if(simNumber.text.toString() != ""){
                  //            Get number to sim for a set number of flips
                   numberToSim = simNumber.text.toString().toInt()
              }

              simNumber.setText("")

//            Run the proper of flips for the simulation
              for (i in 1..numberToSim){
                  flip()
              }
//        Hide the Keyboard
             hideKeyboard()
        }

//    Hide the Keyboard
     private fun hideKeyboard(){
         val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         imm.hideSoftInputFromWindow(coinImage.windowToken, 0)
  }
}