package com.gb.vale.uitaylibrary.utils

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.telecom.Call
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.gb.vale.uitaylibrary.model.UITayMetaDataImage
import com.gb.vale.uitaylibrary.model.UiTayContactPhone
import java.io.IOException

fun Context.uiTayDialedNumber(
    number: String = UI_TAY_EMPTY, key: String = "tel:",
    uiTayAction: String = Intent.ACTION_DIAL
) {
    try {
        val intent = Intent(uiTayAction)
        intent.setData(Uri.parse("$key$number"))
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(UI_TAY_TAG_ERROR, e.message.toString())
    }
}

fun Context.uiTayViewCall() {
    try {
        val intent = Intent()
        intent.setClass(this, Call::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    } catch (e: SecurityException) {
        Log.e(UI_TAY_TAG_ERROR, e.message.toString())
    }
}

fun Context.uiTayViewCallButton() {
    val intent = Intent(Intent.ACTION_CALL_BUTTON)
    this.startActivity(intent)
}

fun Application.uiTayLoadContact(): List<UiTayContactPhone> {
    val contacts: ArrayList<UiTayContactPhone> = ArrayList()
    val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
    )

    val cursor = this.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        projection,
        null,
        null,
        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
    )

    if (cursor?.moveToFirst() == true)
        do {
            val name =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    .trim()
            val phone =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    .trim()

            if (phone.isNotEmpty()) {
                contacts.add(
                    UiTayContactPhone(
                        name = name, phoneNumber = phone,
                        initialLetter = name.substring(0, 1)
                    )
                )
            }
        } while (cursor.moveToNext())

    cursor?.close()
    return contacts
}

fun uiTayValidNumberBlocking(list: List<UiTayContactPhone>, numberCall: String): Boolean {
    var numberBlocking = true
    val incomingCall = numberCall.filter { it.isDigit() }
    val incomingLengthCall = incomingCall.length
    list.forEach { contact ->
        val currentNumber = contact.phoneNumber.filter { it.isDigit() }
        val currentLengthNumber = currentNumber.length
        numberBlocking = if (currentLengthNumber >= incomingLengthCall) {
            currentNumber.substring(
                currentLengthNumber - incomingLengthCall,
                currentLengthNumber
            ) == incomingCall.substring(0, incomingLengthCall)
        } else {
            false
        }
    }
    return !numberBlocking
}

fun String.uiTayMetaDataImage(): UITayMetaDataImage {
    var uiTayData = UITayMetaDataImage()
    try {
        val exifInterface = ExifInterface(this)
        uiTayData =  UITayMetaDataImage(
             length  = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?: UI_TAY_EMPTY,
         width = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?: UI_TAY_EMPTY,
         dateTime  = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)?: UI_TAY_EMPTY,
         take  = exifInterface.getAttribute(ExifInterface.TAG_MAKE)?: UI_TAY_EMPTY,
         model  = exifInterface.getAttribute(ExifInterface.TAG_MODEL)?: UI_TAY_EMPTY,
         orientation  = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)?: UI_TAY_EMPTY,
         whiteBalance  = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE)?: UI_TAY_EMPTY,
         focalLength  = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)?: UI_TAY_EMPTY,
         flash  = exifInterface.getAttribute(ExifInterface.TAG_FLASH)?: UI_TAY_EMPTY,
         gpsDatesTamp  = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)?: UI_TAY_EMPTY,
         gpsTimesTamp  = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)?: UI_TAY_EMPTY,
         gpsLatitude  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)?: UI_TAY_EMPTY,
         gpsLatitudeReferential  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)?: UI_TAY_EMPTY,
         gpsLongitude  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)?: UI_TAY_EMPTY,
         gpsLongitudeReferential  = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)?: UI_TAY_EMPTY,
         gpsProcessingMethod  = exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD)?: UI_TAY_EMPTY
        )

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return uiTayData
}