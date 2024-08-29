package com.fiqsky.latihansoall

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

/**
author Fiqih
Copyright 2023, FiqSky Project
 **/

class SoalAdapter(
    private val context: Context,
    private var soalList: List<Soal>, // Use var to allow updates
    private val viewPager: ViewPager2
) : RecyclerView.Adapter<SoalAdapter.SoalViewHolder>() {

    fun updateSoalList(newSoalList: List<Soal>) {
        val diffCallback = SoalDiffCallback(soalList, newSoalList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        soalList = newSoalList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_soal, parent, false)
        return SoalViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SoalViewHolder, position: Int) {
        val soal = soalList[position]
        holder.bind(soal, position)
    }

    override fun getItemCount(): Int = soalList.size

    inner class SoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val soalText: TextView = itemView.findViewById(R.id.soalText)
        private val btnA: MaterialButton = itemView.findViewById(R.id.btnA)
        private val btnB: MaterialButton = itemView.findViewById(R.id.btnB)
        private val btnC: MaterialButton = itemView.findViewById(R.id.btnC)
        private val btnD: MaterialButton = itemView.findViewById(R.id.btnD)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(soal: Soal, position: Int) {
            soalText.text = soal.pertanyaan
            btnA.text = "A. ${soal.jawaban["A"]}"
            btnB.text = "B. ${soal.jawaban["B"]}"
            btnC.text = "C. ${soal.jawaban["C"]}"
            btnD.text = "D. ${soal.jawaban["D"]}"

            val buttons = listOf(btnA, btnB, btnC, btnD)

            buttons.forEach { button ->
                button.setOnClickListener {
                    val isCorrect = button.text.startsWith(soal.jawaban_benar)
                    val targetColor = if (isCorrect) android.graphics.Color.GREEN else android.graphics.Color.RED
                    animateButtonColor(button, targetColor)

                    if (!isCorrect) vibrateOnError()

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (position < soalList.size - 1) {
                            viewPager.currentItem = position + 1
                        } else {
                            Toast.makeText(context, "Selesai! Semua soal telah dijawab.", Toast.LENGTH_SHORT).show()
                        }
                    }, 5000)
                }
            }
        }

        private fun animateButtonColor(button: MaterialButton, color: Int) {
            val anim = ObjectAnimator.ofArgb(button, "backgroundColor", button.solidColor, color)
            anim.duration = 300
            anim.start()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun vibrateOnError() {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
            vibrator.vibrate(android.os.VibrationEffect.createOneShot(200, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }
}
