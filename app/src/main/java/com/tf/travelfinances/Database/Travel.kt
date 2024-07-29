package com.tf.travelfinances.Database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "travels")
data class Travel (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String?,
    var departure: Long?,
    var arrival: Long?,
    var splitExpenses: Boolean = false,
    var peopleForSplit: Int = 0
)