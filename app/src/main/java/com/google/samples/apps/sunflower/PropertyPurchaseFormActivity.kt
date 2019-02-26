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
import android.widget.LinearLayout
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
        setContentView(R.layout.property_purchase_form)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

       val productCode=intent.getStringExtra("product_code")

       // getProductField(productCode)
        var countValue =count
        Log.d("TAG", countValue.toString())

       property_purchase_form_input_text_1.textChangedListener {
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
        }
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
                    layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                    padding = dip(90)
                    editText {
                        //id = productFieldId_1
                        hint = "Enter your name"
                    }.lparams(width = wrapContent) {
                        horizontalMargin = dip(5)
                        topMargin = dip(10)
                    }


                }

                val hexString = java.lang.Integer.toHexString(1700004)

                for (i in 0 until jsonArrayGuarantyCodeLength-1) {

                    var productFieldId_1 : Int = 1700004

                    val j= i+1

                    val buttonContainer = findViewById<LinearLayout>(productFieldId_2)

                    var guarantyCode : String? = null

                    if (j == 1) {
                         guarantyCode = jsonObjectGuarantyCode.getJSONObject("1").getString("guaranty_code")

                        productFieldId_1 = guarantyCode.substring(1).toInt()

                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }else if (j == 2){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("2").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 3){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("3").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 4){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("4").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 5){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("5").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 6){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("6").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 7){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("7").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }
                    else if (j == 8){

                        guarantyCode = jsonObjectGuarantyCode.getJSONObject("8").getString("guaranty_code")
                        val editText = EditText(getBaseContext())

                        editText.setText(guarantyCode)
                        editText.setTag(guarantyCode)

                        buttonContainer.addView(editText)
                    }




                    /*verticalLayout {
                        id = productFieldId_1
                    }*/
//note that the code below here may be executed anywhere after the above in your onCreate function
//verticalLayout is a Anko subclass of LinearLayout, so using the android class is valid.











                  }
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






