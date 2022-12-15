package com.example.a71200582_andro2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var firestore: FirebaseFirestore? = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update)
            firestore = FirebaseFirestore.getInstance()


        //menerima data dari intent main activity
            var judulawal = intent.getStringExtra("judulawal").toString()
            val tanggalawal = intent.getStringExtra("tanggalawal").toString()
            val isiawal = intent.getStringExtra("isiawal").toString()

            val edtJudul = findViewById<EditText>(R.id.edtJudul)
            val edtTanggal = findViewById<DatePicker>(R.id.edtTanggal)
            val edtIsi = findViewById<EditText>(R.id.edtIsi)

            //Output
            val txvOutput = findViewById<TextView>(R.id.txvOutput)
            val day = (edtTanggal.dayOfMonth).toString()
            val month = (edtTanggal.month + 1).toString()
            val year = edtTanggal.year.toString()

            val date = (day + "-" + month + "-" + year)


            //Sorting
            val btnUpdate = findViewById<Button>(R.id.btnUpdate)



            btnUpdate.setOnClickListener {


            firestore?.collection("catatan")?.orderBy("judul", Query.Direction.ASCENDING)?.get()?.addOnSuccessListener { data ->


                var output = ""

                for (hasil in data) {
                    if (judulawal == hasil["judul"] && tanggalawal == hasil["tanggal"] && isiawal == hasil["isi"]){
                        output += "\nUpdate ${hasil["judul"]} - ${hasil["tanggal"]}: ${hasil["isi"]}"
                        firestore?.collection("catatan")!!.document(hasil.id).update(mapOf(
                            "judul" to edtJudul.text.toString(),
                            "tanggal" to date,
                            "isi" to edtIsi.text.toString()
                        ))

                    }
                }
                txvOutput.text = output

            }


            }
        }
    }
