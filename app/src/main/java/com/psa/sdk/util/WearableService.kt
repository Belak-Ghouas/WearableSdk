package com.psa.sdk.util

import com.google.gson.Gson
import com.psa.sdk.models.DataExchanged
import com.psa.sdk.service.AbstractWearableService
import com.psa.sdk.service.DataListener
import com.psa.sdk.service.DataListenerImpl

class WearableService: AbstractWearableService() {
    override fun getListenerValue(): DataListener {
        return DataListenerImpl(DataExchanged::class.java,object :Builder<DataExchanged>{
            override fun build(byte: ByteArray, jClass: Class<DataExchanged>): DataExchanged {
                return Gson().fromJson(
                    String(byte),
                    jClass
                )
            }
        })
    }
}