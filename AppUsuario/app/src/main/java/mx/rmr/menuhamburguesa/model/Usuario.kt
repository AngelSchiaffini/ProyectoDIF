package mx.rmr.menuhamburguesa.model

import android.os.Parcel
import android.os.Parcelable


// Preguntar de date

data class Usuario(
    var IDUsuario: Int,
    var Nombre: String?,
    var Apellido1: String?,
    var Apellido2: String?,
    var CURP: String?,
    var Nacionalidad: String?,
    var Sexo: String?,
    var FechaNac: String?,
    var Condicion: String?,
    var Cel: String?,
    var Correo: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(IDUsuario)
        parcel.writeString(Nombre)
        parcel.writeString(Apellido1)
        parcel.writeString(Apellido2)
        parcel.writeString(CURP)
        parcel.writeString(Nacionalidad)
        parcel.writeString(Sexo)
        parcel.writeString(FechaNac)
        parcel.writeString(Condicion)
        parcel.writeString(Cel)
        parcel.writeString(Correo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}
