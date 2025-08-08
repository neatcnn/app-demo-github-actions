package com.example.opsc6312demo1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.json.JSONObject

class HandleJSONActivity : AppCompatActivity() {
    data class Person(val name: String, val age: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_json)

        val btnJsonObject = findViewById<Button>(R.id.btnJsonObject)
        val btnGson = findViewById<Button>(R.id.btnGson)
        val txtOutput = findViewById<TextView>(R.id.txtOutput)

        // JSON Object Button: Manual Read/Write
        btnJsonObject.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("name", "Alice")
            jsonObject.put("age", 25)

            val name = jsonObject.getString("name")
            val age = jsonObject.getInt("age")

            txtOutput.text = "Using JSONObject:\nWritten JSON: $jsonObject\nRead values:\nName: $name, Age: $age"
        }

        // Gson Button: Use Gson Library
        btnGson.setOnClickListener {
            val person = Person("Bob", 30)
            val gson = Gson()

            // Convert object to JSON
            val jsonString = gson.toJson(person)

            // Convert JSON back to object
            val personFromJson = gson.fromJson(jsonString, Person::class.java)

            txtOutput.text = "Using Gson:\nJSON: $jsonString\nParsed: Name = ${personFromJson.name}, Age = ${personFromJson.age}"
        }


    }


}