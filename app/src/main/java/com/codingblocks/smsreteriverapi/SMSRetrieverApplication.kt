package com.codingblocks.phoneverify

import android.app.Application
import com.codingblocks.smsreteriverapi.AppSignatureHelper

class SMSRetrieverApplication : Application() {

    /*override fun onCreate() {
        super.onCreate()
        *//*Following will generate the hash code*//*
        var appSignature = AppSignatureHelper(this)
        appSignature.appSignatures
    }*/
}