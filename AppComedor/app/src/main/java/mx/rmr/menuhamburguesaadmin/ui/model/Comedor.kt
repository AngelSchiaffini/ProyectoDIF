package mx.rmr.menuhamburguesaadmin.ui.model

import android.os.Parcel
import android.os.Parcelable

data class Comedor(
    var FolioComedor: Int,
    var Nombre: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(FolioComedor)
        parcel.writeString(Nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comedor> {
        override fun createFromParcel(parcel: Parcel): Comedor {
            return Comedor(parcel)
        }

        override fun newArray(size: Int): Array<Comedor?> {
            return arrayOfNulls(size)
        }
    }
}
