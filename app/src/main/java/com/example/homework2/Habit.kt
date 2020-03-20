package com.example.homework2

import android.os.Parcel
import android.os.Parcelable

class Habit(
    val name: String,
    val description: String,
    val priority: Int,
    val type: HabitType,
    val count: Int,
    val period: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        name = parcel.readString().toString(),
        description = parcel.readString().toString(),
        priority = parcel.readInt(),
        type = HabitType.valueOf(parcel.readString().toString()),
        count = parcel.readInt(),
        period = parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priority)
        parcel.writeString(type.name)
        parcel.writeInt(count)
        parcel.writeInt(period)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit {
            return Habit(parcel)
        }

        override fun newArray(size: Int): Array<Habit?> {
            return arrayOfNulls(size)
        }
    }
}