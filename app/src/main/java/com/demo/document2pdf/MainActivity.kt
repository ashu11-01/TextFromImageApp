package com.demo.document2pdf

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),NoteListAdapter.ItemClicked {

    private lateinit var currentPhotoPath: String
    lateinit var notesRecycler : RecyclerView
    private val IMAGE_CAPTURE_REQUEST: Int = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            openCamera(it)
        }
    }

    private fun initviews() {
        notesRecycler = findViewById(R.id.notes_recycler)
        notesRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        val n1 = NoteModel("N1","Notes1",Date(2020,2,1))
        val n2 = NoteModel("N2","Notes2",Date(2020,3,2))
        val noteList : List<NoteModel> = listOf(n1,n2)
        notesRecycler.adapter = NoteListAdapter(this,noteList)
    }

    private fun openCamera(it: View?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager).also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }

                photoFile?.also {
                    val photoUri : Uri = FileProvider.getUriForFile(this,"com.demo.document2pdf",it)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(intent,IMAGE_CAPTURE_REQUEST)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == IMAGE_CAPTURE_REQUEST && resultCode == Activity.RESULT_OK) {
                val bitmap : Bitmap = BitmapFactory.decodeFile(currentPhotoPath)
//                findViewById<ImageView>(R.id.).setImageBitmap(bitmap)
                try{
                    runTextRecognition(bitmap)
                }catch (ex: IllegalStateException){
                    Toast.makeText(this,"Could not recognize text from image",Toast.LENGTH_SHORT).show()
                }
            }
        }
    @Throws(IOException::class)
    private fun createImageFile() : File {
        val timestamp : String  = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val location : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("Image_${timestamp}_",".jpg",location).apply { currentPhotoPath = absolutePath }
    }

    private fun runTextRecognition(bitmap : Bitmap){
        val image : FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
        val detector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(image).addOnSuccessListener{firebaseVisionText ->
            processRecognizedText(firebaseVisionText)
        }.addOnFailureListener{
            throw IllegalStateException()
        }
    }

    private fun processRecognizedText(recognizedText : FirebaseVisionText) {
        var resultText : String? = null
        if(recognizedText.textBlocks.size==0){
            resultText =null
            return
        }
        for(block in recognizedText.textBlocks){
            resultText = resultText + block.text +"\n"
        }
//        tvText.text = resultText

        goToEditNoteScreen(resultText)
    }

    private fun goToEditNoteScreen(resultText : String?) {
        val intent = Intent(this,EditNoteActivity::class.java)
        intent.putExtra("noteText",resultText)
        startActivity(intent)
    }
    override fun onItemClicked(position: Int) {
        Toast.makeText(this,"clicked"+position,Toast.LENGTH_SHORT).show()
    }
}

