package com.example.almacenamientoendb

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", txt_CodigoProducto.getText().toString())
            registro.put("descripcion", txt_Descripcion.getText().toString())
            registro.put("precio", txt_Precio.getText().toString())
            bd.insert("articulos", null, registro)
            bd.close()
            txt_CodigoProducto.setText("")
            txt_Descripcion.setText("")
            txt_Precio.setText("")
            Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
        }

        btn_2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion,precio from articulos where codigo=${txt_CodigoProducto.text.toString()}", null)
            if (fila.moveToFirst()) {
                txt_Descripcion.setText(fila.getString(0))
                txt_Precio.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btn_3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,precio from articulos where descripcion='${txt_Descripcion.text.toString()}'", null)
            if (fila.moveToFirst()) {
                txt_CodigoProducto.setText(fila.getString(0))
                txt_Precio.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btn_4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("articulos", "codigo=${txt_CodigoProducto.text.toString()}", null)
            bd.close()
            txt_CodigoProducto.setText("")
            txt_Descripcion.setText("")
            txt_Precio.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show()
        }

        btn_5.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("descripcion", txt_Descripcion.text.toString())
            registro.put("precio", txt_Precio.text.toString())
            val cant = bd.update("articulos", registro, "codigo=${txt_CodigoProducto.text.toString()}", null)
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "no existe un artículo con el código ingresado", Toast.LENGTH_SHORT).show()
        }

    }
}