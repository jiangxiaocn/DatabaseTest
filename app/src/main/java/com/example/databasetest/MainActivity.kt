package com.example.databasetest

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                // 开始组装第一条数据
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1) // 插入第一条数据
            val values2 = ContentValues().apply {
                // 开始组装第二条数据
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 19.95)
            }
            db.insert("Book", null, values2) // 插入第二条数据
            updateData.setOnClickListener {
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                values.put("price", 10.99)
                db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
            }
            deleteData.setOnClickListener {
                val db = dbHelper.writableDatabase
                db.delete("Book", "pages > ?", arrayOf("500"))
            }
            queryData.setOnClickListener {
                val db = dbHelper.writableDatabase
                // 查询Book表中所有的数据
                val cursor = db.query("Book", null, null, null, null, null, null)
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        val name = cursor.getString(cursor.getColumnIndex("name"))
                        val author = cursor.getString(cursor.getColumnIndex("author"))
                        val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                        val price = cursor.getDouble(cursor.getColumnIndex("price"))
                        Log.d("MainActivity", "book name is $name")
                        Log.d("MainActivity", "book author is $author")
                        Log.d("MainActivity", "book pages is $pages")
                        Log.d("MainActivity", "book price is $price")
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        }
    }
}
