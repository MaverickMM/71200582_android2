package com.example.a71200582_andro2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class MainActivity : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

        val edtJudul = findViewById<EditText>(R.id.edtJudul)
        val edtTanggal = findViewById<DatePicker>(R.id.edtTanggal)
        val edtIsi = findViewById<EditText>(R.id.edtIsi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        //Output
        val txvOutput = findViewById<TextView>(R.id.txvOutput)
        val day =  (edtTanggal.dayOfMonth).toString()
        val month = (edtTanggal.month + 1).toString()
        val year = edtTanggal.year.toString()

        val date = (day + "-" + month + "-" + year)


        //Sorting
        val btnCari = findViewById<Button>(R.id.btnCari)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        //MENYIMPAN DATA
        btnSimpan.setOnClickListener {
            val catatan = Catatan(
                edtJudul.text.toString(),
                date,
                edtIsi.text.toString()
            )
            edtJudul.setText("")
//            date.("")
            edtIsi.setText("")
            firestore?.collection("catatan")?.add(catatan)

        }
        //MENCARI DATA
        btnCari.setOnClickListener {
            firestore?.collection("catatan")?.orderBy("judul", Query.Direction.ASCENDING)?.get()?.addOnSuccessListener { data ->


                var output = ""
                for (hasil in data) {
                    if (edtJudul.text.toString() == hasil["judul"]){
                        output += "\n${hasil["judul"]} - ${hasil["tanggal"]}: ${hasil["isi"]}"
                    }
                }
                txvOutput.text = output

            }

        }

        btnDelete.setOnClickListener {
            firestore?.collection("catatan")?.orderBy("judul", Query.Direction.ASCENDING)?.get()?.addOnSuccessListener { data ->


                var output = ""
                for (hasil in data) {
                    if (edtJudul.text.toString() == hasil["judul"]){
                        output += "\nDelete ${hasil["judul"]} - ${hasil["tanggal"]}: ${hasil["isi"]}"
                        firestore?.collection("catatan")!!.document(hasil.id).delete()
                    }
                }
                txvOutput.text = output

            }
        }

        btnUpdate.setOnClickListener {
            val updatechan = Intent(this,Update::class.java)
            updatechan.putExtra("judulawal",(edtJudul.text.toString()))
            updatechan.putExtra("tanggalawal",date)
            updatechan.putExtra("isiawal",edtIsi.text.toString())


            startActivity(updatechan)
//            firestore?.collection("catatan")?.orderBy("judul", Query.Direction.ASCENDING)?.get()?.addOnSuccessListener { data ->
//
//
//                var output = ""
//                for (hasil in data) {
//                    if (edtJudul.text.toString() == hasil["judul"]){
//                        output += "\nUpdate ${hasil["judul"]} - ${hasil["tanggal"]}: ${hasil["isi"]}"
//                        firestore?.collection("catatan")!!.document(hasil.id).update(mapOf(
//                            "judul" to "inco",
//                            "tanggal" to "ming",
//                            "isi" to "tins"
//                        ))
//
//                    }
//                }
//                txvOutput.text = output
//
//            }

        }
    }
}



