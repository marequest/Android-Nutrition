package com.mareq.ispitmealapp.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.DialogFragment


class OpenCameraDialog : DialogFragment() {

    private val CAMERA_PIC_REQUEST = 1337


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Take a picture")
            builder.setMessage("Take a new picture of your meal")
                .setPositiveButton("Open Camera",
                    DialogInterface.OnClickListener { dialog, id ->

                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        activity?.startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST)

                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}