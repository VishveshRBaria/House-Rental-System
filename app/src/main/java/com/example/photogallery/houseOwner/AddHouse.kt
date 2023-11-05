package com.example.photogallery.houseOwner

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.photogallery.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.util.HashMap

class AddHouse : AppCompatActivity() {

    private lateinit var iv_houseImage: ImageView
    private lateinit var et_houseId: EditText
    private lateinit var et_houseLocation: EditText
    private lateinit var et_noOfRoom: EditText
    private lateinit var et_rentPerRoom: EditText
    private lateinit var et_houseDescription: EditText
    private lateinit var btn_addHouse: Button

    private val STORAGE_PERMISSION_CODE = 100
    private lateinit var storageReference: StorageReference
    private val IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private var imageString: String? = null
    private var uploadTask: StorageTask<UploadTask.TaskSnapshot>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)

        iv_houseImage = findViewById(R.id.iv_houseImage)
        et_houseId = findViewById(R.id.et_houseId)
        et_houseLocation = findViewById(R.id.et_houseLocation)
        et_noOfRoom = findViewById(R.id.et_noOfRoom)
        et_rentPerRoom = findViewById(R.id.et_rentPerRoom)
        et_houseDescription = findViewById(R.id.et_houseDescription)
        btn_addHouse = findViewById(R.id.btn_addHouse)

        storageReference = FirebaseStorage.getInstance().reference.child("Uploads")

        iv_houseImage.setOnClickListener {
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
        }

        btn_addHouse.setOnClickListener(View.OnClickListener {
            val houseId = et_houseId.text.toString()
            val houseLocation = et_houseLocation.text.toString()
            val noOfRoom = et_noOfRoom.text.toString()
            val rentPerRoom = et_rentPerRoom.text.toString()
            val houseDescription = et_houseDescription.text.toString()
            val image = imageString

            createHouse(houseId, houseLocation, noOfRoom, rentPerRoom, houseDescription, image)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createHouse(
        houseId: String,
        houseLocation: String,
        noOfRoom: String,
        rentPerRoom: String,
        houseDescription: String,
        image: String?
    ) {
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val userId: String? = firebaseUser?.uid

        val reference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference().child("upload")
                .child(userId!!)
        val hashMap: HashMap<String, String?> = HashMap()
        hashMap["houseId"] = houseId
        hashMap["noOfRoom"] = noOfRoom
        hashMap["houseDescription"] = houseDescription
        hashMap["houseLocation"] = houseLocation
        hashMap["rentPerRoom"] = rentPerRoom
        hashMap["houseImage"] = image
        hashMap["userId"] = userId
        reference.push().setValue(hashMap)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AddHouse, "House Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                    imageString = ""
                } else {
                    Toast.makeText(this@AddHouse, "House Adding Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    private fun getFileExtension(uri: Uri?): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    private fun uploadImage() {
        if (imageUri != null) {
            val fileReference: StorageReference = storageReference.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(imageUri)
            )
            uploadTask = fileReference.putFile(imageUri!!)
            (uploadTask as UploadTask).continueWithTask(Continuation { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileReference.downloadUrl
            }).addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadingUri: Uri? = task.result
                    val mUri: String? = downloadingUri.toString()
                    imageString = mUri
                    Glide.with(this@AddHouse).load(imageUri).into(iv_houseImage)
                } else {
                    Toast.makeText(this@AddHouse, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@AddHouse, "No image Selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            if (uploadTask != null && uploadTask!!.isInProgress) {
                Toast.makeText(this@AddHouse, "Upload in progress", Toast.LENGTH_SHORT).show()
            } else {
                uploadImage()
            }
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@AddHouse,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this@AddHouse, arrayOf(permission), requestCode)
        } else {
            openImage()
            Toast.makeText(this@AddHouse, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImage()
                Toast.makeText(this@AddHouse, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@AddHouse, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
