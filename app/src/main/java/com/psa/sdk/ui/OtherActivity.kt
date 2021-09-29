package com.psa.sdk.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.psa.sdk.R
import com.psa.sdk.models.DataExchanged
import com.psa.sdk.send.SenderWrapper
import com.psa.sdk.service.DataFlow
import com.psa.sdk.service.Singleton
import com.psa.sdk.util.Config
import com.psa.sdk.util.Event
import com.psa.sdk.util.observe

class OtherActivity:AppCompatActivity() {
    lateinit var button: Button
    lateinit var text : TextView
    var data= MutableLiveData<String>()
    private val flowProvider: DataFlow<DataExchanged>? =
        Singleton.Instance("com.psa.sdk.service.FlowHandler")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sender= SenderWrapper(this,object: Config {
            override fun getCapability(): String {
                return  "messages"
            }

            override fun getMessageRoute(): String {
                return "/messages"
            }
        })

        button=findViewById(R.id.send_message)
        text=findViewById(R.id.text)
        button.setOnClickListener {
            sender.sendMessage(DataExchanged(Event.CommandCharging,"hello from phone")){
                Log.e("Completed",it.toString())
            }
        }
        data.observe(this,{
            text.text=it
        })

        flowProvider?.messageFlow?.observe(lifecycleScope) {
            data.postValue(it.toString())
        }
    }
}