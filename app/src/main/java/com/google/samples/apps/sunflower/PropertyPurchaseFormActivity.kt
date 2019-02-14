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
import kotlinx.android.synthetic.main.property_purchase_form.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class PropertyPurchaseFormActivity : AppCompatActivity() {

    val PREFS_FILENAME = "com.mba.product_customer_details.prefs"
    var prefs: SharedPreferences? = null
    val PROPERTY_DETAILS = "property_details"
    val PROPERTY_PREMIUM_AMOUNT = "property_premium_amount"
    val PROPERTY_COVER = "property_cover"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_purchase_form)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

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
}


