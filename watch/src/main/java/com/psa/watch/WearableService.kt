package com.psa.watch

import com.google.gson.Gson
import com.psa.wsdk.models.DataExchanged
import com.psa.wsdk.service.AbstractWearableService
import com.psa.wsdk.service.DataListener
import com.psa.wsdk.service.DataListenerImpl
import com.psa.wsdk.util.Builder

class WearableService: AbstractWearableService() {
    override fun getListenerValue(): DataListener {
        return DataListenerImpl(DataExchanged::class.java,object : Builder<DataExchanged> {
            override fun build(byte: ByteArray, jClass: Class<DataExchanged>): DataExchanged {
                return Gson().fromJson(
                    String(byte),
                    jClass
                )
            }
        })
    }
}