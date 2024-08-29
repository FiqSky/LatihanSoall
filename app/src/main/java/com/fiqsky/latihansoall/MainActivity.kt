package com.fiqsky.latihansoall

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var soalAdapter: SoalAdapter
    private lateinit var soalList: MutableList<Soal>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        soalList = mutableListOf()

        // Menghubungkan ke reference yang benar sesuai dengan struktur JSON di Firebase
        database = FirebaseDatabase.getInstance("https://latihansoall-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("soal_twk")

        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val soal = snapshot.getValue(Soal::class.java)
                soal?.let { soalList.add(it) }
                Log.d("MainActivity", "Soal: $soal")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Failed to load data", error.toException())
            }
        })


        // Mengambil data dari Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                soalList.clear()
                for (soalSnapshot in snapshot.children) {
                    val soal = soalSnapshot.getValue(Soal::class.java)
                    soal?.let { soalList.add(it) }
                }
                soalAdapter = SoalAdapter(this@MainActivity, soalList, viewPager)
                viewPager.adapter = soalAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Failed to load data", error.toException())
            }
        })
    }
}
