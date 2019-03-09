package com.google.samples.apps.sunflower

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.samples.apps.sunflower.utilities.SingleProductDetailsAPI
import com.google.samples.apps.sunflower.utilities.UserProductAPI
import com.google.samples.apps.sunflower.workers.UsersProductList
import com.squareup.moshi.Json
import kotlinx.android.synthetic.main.property_purchase_form.*
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.Result.response
import android.R.string
import android.content.Context
import android.view.ViewGroup
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import org.jetbrains.anko.*
import org.jetbrains.anko.internals.AnkoInternals.getContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern
import org.jetbrains.anko.AnkoContext


class PropertyPurchaseFormActivity : AppCompatActivity() {

    val PREFS_FILENAME = "com.mba.product_customer_details.prefs"
    var prefs: SharedPreferences? = null
    val PROPERTY_DETAILS = "property_details"
    val PROPERTY_PREMIUM_AMOUNT = "property_premium_amount"
    val PROPERTY_COVER = "property_cover"
    val BASE_URL = "https://mbadigital-admin.safamdigital.com/"
    var count = 1
   // val context: Context = getContext()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.property_purchase_form)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

       val productCode=intent.getStringExtra("product_code")

        getProductField(productCode)

        var countValue =count
        Log.d("TAG", countValue.toString())

   /*    property_purchase_form_input_text_1.textChangedListener {
            afterTextChanged {
                // Do something here...
                 var busy = false
                Log.d("TAG", "I'm here")
                var propertyValue = property_purchase_form_input_text_1.text.toString()
                var parsedInt = propertyValue.toDouble()
                parsedInt = parsedInt * 0.0035
                val stringPropertyPremiumAmount = parsedInt.toString()
                this@PropertyPurchaseFormActivity.property_purchase_form_input_text_3.setText("N" + stringPropertyPremiumAmount)
            }

        }

        property_purchase_form_submit_button.onClick {

           /* val editor = prefs!!.edit()
            editor.putInt(PRODUCT_NAME, color)
            editor.apply()*/
            val editor = prefs!!.edit()
            editor.putString(PROPERTY_DETAILS, this@PropertyPurchaseFormActivity.property_purchase_form_input_text_2.text.toString())
            editor.putString(PROPERTY_PREMIUM_AMOUNT, this@PropertyPurchaseFormActivity.property_purchase_form_input_text_3.text.toString())
            editor.putString(PROPERTY_COVER, this@PropertyPurchaseFormActivity.property_purchase_form_input_layout_3.hint.toString())
            editor.apply()

            val intent = Intent (this@PropertyPurchaseFormActivity, kyc_form_activity::class.java)
            // start your next activity
            startActivity(intent)
        }*/
    }

    private fun getProductField(productCodeFieldValue:String) {

        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
                .build()

        var api = retrofit.create(SingleProductDetailsAPI::class.java)
        var call = api.setProductCode("J5bI7RkAmE7qOMAwqvFZ2.WxwaBWODgix0uy6ry2VLatls3zV.3I2", productCodeFieldValue)
//        var call = api.productFields
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
               // override fun onResponse(call: Call<Json>?, response: Response<Json>?) {
                var productField = response?.body()

                val productFieldJson = JSONObject(productField?.string())   //   JSONArray

                val jsonObjectGuarantyCode =  productFieldJson.getJSONObject("data").getJSONObject("guaranty_code")

                var jsonArrayGuarantyCodeLength = jsonObjectGuarantyCode.length()
                var jsonArrayGuarantyCode = jsonObjectGuarantyCode.names()

                var productFieldId_2 : Int = 78899
                verticalLayout {
                    id = productFieldId_2
                    tag = "verticalLayout"
                    layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                    padding = dip(90)



                }

                //var guarantyCalcArray = arrayOf<String>()
                var guarantyCalcArray : MutableList<String> = mutableListOf<String>()
                var guarantyCodeArray : MutableList<String> = mutableListOf<String>()
                //var guarantyCodeMap = mutableMapOf<String, String>()
                var inputTypeMap = mutableMapOf<String, String>()
                var guarantyCalcCounter : Int = 0

                for (i in 0 until jsonArrayGuarantyCodeLength) {

                    var productFieldId_1 : Int = 1700004

                    val j= i



                   val buttonContainer = findViewById<LinearLayout>(productFieldId_2)

                   // val buttonContainer    = findViewWithTag<LinearLayout>("verticalLayout")

                    var guarantyCode : String? = null

                    var guarantyName : String? = null

                    var guarantyType : String? = null

                    var guarantyCalc : String? = null

                    var guarantyLimit : String? = null

                    var guarantyOption : String? = null

                    var description1 : String? = null

                    if (j == 0) {
                         guarantyCode = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_code")
                         guarantyName =  jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_name")
                         guarantyType = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_type")
                         guarantyCalc = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_calc")
                         guarantyLimit = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_limit")
                         guarantyOption = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_option")
                         description1 = jsonObjectGuarantyCode.getJSONObject("1").getString("description1")
                       // productFieldId_1 = guarantyCode.substring(1).toInt()

                        if( guarantyType  == "SF" || guarantyType == "SI" || guarantyType == "AI") {

                            if (guarantyCalc != "") {

                                guarantyCalcArray.add(guarantyCalc)
                                val lastStringPosition: Int = guarantyCalc.indexOf("}")
                                var firstStringPosition: Int = guarantyCalc.indexOf("{")
                                firstStringPosition += 1
                                val subSequence = guarantyCalc.subSequence(firstStringPosition, lastStringPosition)
                                //guarantyCodeMap[guarantyCode] = subSequence.toString()
                                guarantyCodeArray.add(guarantyCode)

                                       }


                             val editText = EditText(getBaseContext())

                             editText.setTag(guarantyCode)

                             buttonContainer.addView(editText)

                             inputTypeMap[guarantyCode] = guarantyType
                             }else if (guarantyType  == "MF") {

                            var delimiter1 = ":"
                            var delimiter2 = "|"

                            //val inputsString = guarantyCalc.split(delimiter1, delimiter2)
                            val inputsString = guarantyCalc.split(delimiter2)
                            for (j in 0 until inputsString.size) {

                                val radioButton = RadioButton(getBaseContext())
                                radioButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                radioButton.setText(inputsString[j])
                                //radioButton1.setTag(guarantyCode)
                                radioButton.id = j
                                buttonContainer.addView(radioButton)
                            }


                        } else if (guarantyType  == "TL") {

                            val textView = TextView(getBaseContext())

                            textView.setTag(guarantyCode)
                            textView.textSize = 20f
                            textView.text = guarantyName + ": " + "  " + description1

                            buttonContainer.addView(textView)



                        }



                    }else if (j == 1){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_code")
                        guarantyName =  jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("2").getString("description1")

                        if( guarantyType  == "SF" || guarantyType == "SI" || guarantyType == "AI") {

                            if (guarantyCalc != "") {

                                guarantyCalcArray.add(guarantyCalc)

                                val lastStringPosition: Int = guarantyCalc.indexOf("}")
                                var firstStringPosition: Int = guarantyCalc.indexOf("{")
                                firstStringPosition += 1
                                val subSequence = guarantyCalc.subSequence(firstStringPosition, lastStringPosition)
                              //  guarantyCodeMap[guarantyCode] = subSequence.toString()
                                guarantyCodeArray.add(guarantyCode)

                            }
                            val editText = EditText(getBaseContext())

                            editText.setTag(guarantyCode)

                            buttonContainer.addView(editText)

                            inputTypeMap[guarantyCode] = guarantyType
                        }else if (guarantyType  == "MF") {

                            var delimiter1 = ":"
                            var delimiter2 = "|"

                            //val inputsString = guarantyCalc.split(delimiter1, delimiter2)
                            val inputsString = guarantyCalc.split(delimiter2)
                            for (j in 0 until inputsString.size) {

                                val radioButton = RadioButton(getBaseContext())
                                radioButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                radioButton.setText(inputsString[j])
                                //radioButton1.setTag(guarantyCode)
                                radioButton.id = j
                                buttonContainer.addView(radioButton)
                            }


                        }else if (guarantyType  == "TL") {

                            val textView = TextView(getBaseContext())

                            textView.setTag(guarantyCode)
                            textView.textSize = 20f
                            textView.text = guarantyName + ": " + "  " + description1

                            buttonContainer.addView(textView)



                        }

                    }
                    else if (j == 2) {

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_code")
                        guarantyName = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("3").getString("description1")

                        if (guarantyType == "SF" || guarantyType == "SI" || guarantyType == "AI") {

                            if (guarantyCalc != "") {

                                guarantyCalcArray.add(guarantyCalc)

                                val lastStringPosition: Int = guarantyCalc.indexOf("}")
                                var firstStringPosition: Int = guarantyCalc.indexOf("{")
                                firstStringPosition += 1
                                val subSequence = guarantyCalc.subSequence(firstStringPosition, lastStringPosition)
                               // guarantyCodeMap[guarantyCode] = subSequence.toString()
                                guarantyCodeArray.add(guarantyCode)

                            }

                            val editText = EditText(getBaseContext())

                            editText.setTag(guarantyCode)

                            buttonContainer.addView(editText)

                            inputTypeMap[guarantyCode] = guarantyType

                        }else if (guarantyType  == "MF") {

                            var delimiter1 = ":"
                            var delimiter2 = "|"

                            //val inputsString = guarantyCalc.split(delimiter1, delimiter2)
                            val inputsString = guarantyCalc.split(delimiter2)
                            for (j in 0 until inputsString.size) {

                                val radioButton = RadioButton(getBaseContext())
                                radioButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                radioButton.setText(inputsString[j])
                                //radioButton1.setTag(guarantyCode)
                                radioButton.id = j
                                buttonContainer.addView(radioButton)
                            }


                        }else if (guarantyType  == "TL") {

                            val textView = TextView(getBaseContext())

                            textView.setTag(guarantyCode)
                            textView.textSize = 20f
                            textView.text = guarantyName + ": " + "  " + description1

                            buttonContainer.addView(textView)



                        }

                    }
                    else if (j == 3) {

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_code")
                        guarantyName = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("4").getString("description1")

                        if (guarantyType == "SF" || guarantyType == "SI" || guarantyType == "AI") {

                            if (guarantyCalc != "") {

                                guarantyCalcArray.add(guarantyCalc)

                                val lastStringPosition: Int = guarantyCalc.indexOf("}")
                                var firstStringPosition: Int = guarantyCalc.indexOf("{")
                                firstStringPosition += 1
                                val subSequence = guarantyCalc.subSequence(firstStringPosition, lastStringPosition)
                              //  guarantyCodeMap[guarantyCode] = subSequence.toString()
                                guarantyCodeArray.add(guarantyCode)

                            }

                            val editText = EditText(getBaseContext())

                            editText.setTag(guarantyCode)

                            buttonContainer.addView(editText)

                            inputTypeMap[guarantyCode] = guarantyType
                        }else if (guarantyType  == "MF") {

                            var delimiter1 = ":"
                            var delimiter2 = "|"

                            //val inputsString = guarantyCalc.split(delimiter1, delimiter2)
                            val inputsString = guarantyCalc.split(delimiter2)
                            for (j in 0 until inputsString.size) {

                                val radioButton = RadioButton(getBaseContext())
                                radioButton.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                radioButton.setText(inputsString[j])
                                //radioButton1.setTag(guarantyCode)
                                radioButton.id = j
                                buttonContainer.addView(radioButton)
                            }


                        }else if (guarantyType  == "TL") {

                            val textView = TextView(getBaseContext())

                            textView.setTag(guarantyCode)
                            textView.textSize = 20f
                            textView.text = guarantyName + ": " + "  " + description1

                            buttonContainer.addView(textView)



                        }
                    }
                    else if (j == 4){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_code")
                        guarantyName =  jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("5").getString("description1")

                    }
                    else if (j == 5){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_code")
                        guarantyName =  jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("6").getString("description1")

                    }
                    else if (j == 6){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_code")
                        guarantyName =  jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("7").getString("description1")

                    }
                    else if (j == 7){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_code")
                        guarantyName =  jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_name")
                        guarantyType = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_type")
                        guarantyCalc = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_calc")
                        guarantyLimit = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_limit")
                        guarantyOption = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_option")
                        description1 = jsonObjectGuarantyCode.getJSONObject("8").getString("description1")

                    }

                      //editText.getTag(guarantyCode!!.toInt())
                    // var guarantyCodeVal  = editText.tag
                   // Log.d("TAG", guarantyCodeVal.toString())
                    /*editText.textChangedListener {
                        afterTextChanged {
                            // Do something here...
                            var busy = false
                            Log.d("TAG", "I'm here")
                            var propertyValue = editText.text.toString()
                            var parsedInt = propertyValue.toDouble()
                            parsedInt = parsedInt * 0.0035
                            val stringPropertyPremiumAmount = parsedInt.toString()
                            this@PropertyPurchaseFormActivity.property_purchase_form_input_text_3.setText("N" + stringPropertyPremiumAmount)
                        }

                    }*/



                  }


                val guarantyCalcArrayTrack = guarantyCalcArray


                val buttonContainer = findViewById<LinearLayout>(productFieldId_2)

           //     val guarantyCodeMapTracker =   guarantyCodeMap

                //val editText2  = buttonContainer.findViewWithTag<EditText>("C1700009")

                for (i in 0 until jsonArrayGuarantyCodeLength) {

                    var viewType =  buttonContainer.getChildAt(i)

                    var viewTypeTag =  viewType.getTag()

                    //val editText = EditText(getBaseContext())

                    if( inputTypeMap[viewTypeTag] == "SF" || inputTypeMap[viewTypeTag] == "SI" || inputTypeMap[viewTypeTag] == "AI" ) {

                        val editText2 = buttonContainer.findViewWithTag<EditText>(viewTypeTag)

                        var inputTypeMapTwo = mutableMapOf<String, CharSequence>()


                        for (j in 0 until guarantyCalcArrayTrack.size) {

                            if (guarantyCalcArrayTrack[j].contains(viewTypeTag.toString())) {

                                var stringPositionTracker: Int = guarantyCalcArrayTrack[j].indexOf("*")

                                val subSequence = guarantyCalcArrayTrack[j].subSequence(0, stringPositionTracker)

                                Log.d("TAG", subSequence.toString())
                                Log.d("TAG", viewTypeTag.toString())
                                //   if( inputTypeMap[viewTypeTag] == "SF" || inputTypeMap[viewTypeTag] == "SI" || inputTypeMap[viewTypeTag] == "AI" ) {

                                // var mapValue = guarantyCodeMap[viewTypeTag]
                                //val editText3 = buttonContainer.findViewWithTag<EditText>(guarantyCodeArray[j])


                                inputTypeMapTwo[guarantyCodeArray[j]] = subSequence


                                //   }
                            }
                        }
                        var tracker = 0
                        var tagText: MutableList<String> = mutableListOf<String>()
                        var textFormula: MutableList<String> = mutableListOf<String>()
                        if (inputTypeMapTwo.isNotEmpty()) {
                            for ((k, v) in inputTypeMapTwo) {
                                tagText.add(k)
                                textFormula.add(v.toString())
                                tracker++
                            }
                            if (tracker == 1){
                                editText2.textChangedListener {
                                    afterTextChanged {
                                        // Do something here...
                                        var busy = false
                                        Log.d("TAG", "I'm here")
                                        var propertyValue = editText2.text.toString()
                                        var parsedInt = propertyValue.toDouble()
                                        parsedInt = parsedInt * textFormula[0].toString().toDouble()
                                        val stringPropertyPremiumAmount = parsedInt.toString()
                                        val editText3 = buttonContainer.findViewWithTag<EditText>(tagText[0])
                                        editText3.setText("N" + stringPropertyPremiumAmount)
                                    }

                                }

                             }

                            if (tracker == 2){
                                editText2.textChangedListener {
                                    afterTextChanged {
                                        // Do something here...
                                        var busy = false
                                        Log.d("TAG", "I'm here")
                                        var propertyValue = editText2.text.toString()
                                        var parsedInt = propertyValue.toDouble()
                                        parsedInt = parsedInt * textFormula[0].toString().toDouble()
                                        val stringPropertyPremiumAmount = parsedInt.toString()
                                        val editText3 = buttonContainer.findViewWithTag<EditText>(tagText[0])
                                        editText3.setText("N" + stringPropertyPremiumAmount)

                                        var parsedInt2 = propertyValue.toDouble()
                                        parsedInt2 = parsedInt2 * textFormula[1].toString().toDouble()
                                        val stringPropertyPremiumAmount2 = parsedInt2.toString()
                                        val editText4 = buttonContainer.findViewWithTag<EditText>(tagText[1])
                                        editText4.setText("N" + stringPropertyPremiumAmount2)
                                    }

                                }

                            }
                        }
                    }
                    val rlmain = LinearLayout(getBaseContext())

                }


                // var viewType3 =  rlmain.getChildAt(1)

                //val rlmain = LinearLayout(getBaseContext())



               /* var jsonObjectGuarantyCode  =  jsonObjectData.getJSONObject("guaranty_code")
                var jsonObjectGuarantyCodeList   = jsonObjectGuarantyCode.getJSONObject("1")
                var guaranty_code = jsonObjectGuarantyCodeList.getString("guaranty_code")
                var jsonArrayGuarantyCode = jsonObjectGuarantyCode.names()
                var jsonArrayGuarantyCodeLength = jsonObjectGuarantyCode.length()*/


            }

            override  fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
               // override   fun onFailure(call: Call<Json>?, t: Throwable?) {
                Log.v("Error", t.toString())
            }
        })
      }


    }






