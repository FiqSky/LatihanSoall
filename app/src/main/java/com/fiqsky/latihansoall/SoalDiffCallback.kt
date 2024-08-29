package com.fiqsky.latihansoall

import androidx.recyclerview.widget.DiffUtil

/**
author Fiqih
Copyright 2023, FiqSky Project
 **/
class SoalDiffCallback(
    private val oldList: List<Soal>,
    private val newList: List<Soal>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
