package com.example.josh.advcompproj

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.android.volley.toolbox.Volley.newRequestQueue
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.json.JSONArray
import  android.view.View
import java.math.BigInteger
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progressBar.visibility = View.GONE // Hides the loading circle in the beginning. The loading circle is used to cue the user that the app is working

        var requestQueue= newRequestQueue(this@MainActivity) //Object that handles the requests for REST API
        var user_id: String
        var password : String
        var loginInfo: Array<String> = arrayOf("User","Password")


        buttonSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                progressBar.visibility = View.VISIBLE //Loading circle appears
                loginInfo = getLogin(v)
                user_id = loginInfo[0]                //Values to be used in URL and POST
                password = loginInfo[1]

                queries(requestQueue,user_id,password)
            }
        })
   }// End of main class


            /*************************FUNCTIONS*******************************/


    fun getLogin(v : View?): Array<String>{// Gets the login information from the editTexts and returns the User's ID and password that was entered
        val user_Id : String = editUserId.text.toString()
        var password : String = editPassword.text.toString()

            password = password.md5() // First hashes password and then stores in array

        val login : Array<String> = arrayOf(user_Id,password)

        return login
    }

    fun String.md5(): String { //Hashes inputed password
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')

        //Credits: https://gist.github.com/dlimpid/68761108b49b7c1ddb84250dfac78495
    }


    fun queries(requestQueue: RequestQueue, employee_id: String , pass: String ){

        val url_post: String = "http://192.168.1.218:3003/post"
        val url_get:String = "http://192.168.1.218:3003/mobile"
        var sendingThis: JSONObject = JSONObject("{passwords:$pass,employee_idFK:$employee_id}") // The JSONobject being send in the POST request
        var firstConnTry : Boolean = true
        var salaryArray : JSONObject = JSONObject()
        var counter = 1

        var postRequest = JsonObjectRequest( // Sending password and user_ID. Will be checked by server for verification
            Request.Method.POST,
            url_post,
            sendingThis,
            Response.Listener() { response -> //Probably isnt called because POST does not return anything
            }, Response.ErrorListener() { err ->
                Log.v("v_tag", "Error2 + ${err}")
            })


        var objectRequest = JsonArrayRequest( //GET request. Will only go through if verified by server
            Request.Method.GET,
            url_get,
            null,
            Response.Listener() { response ->
                var respString: String = response.toString()
                val jsonArray: JSONArray = JSONArray(respString)            //Response is sent as a JSON array
                var jsonObj: JSONObject = jsonArray.getJSONObject(0) //Converting JSON array by getting the first and only element and making that a JSON object

                    salaryArray = jsonObj
                    storing(salaryArray) // Storing value in a function

                Log.v("v_tag" , "original response string : ${respString}")
            },

            Response.ErrorListener() { err ->
                Log.v("v_tag", "Timeouterror ${err.toString()}")
                Thread.sleep(5000)
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()

            })

         //Executing the requests
        requestQueue.add(postRequest)
        Thread.sleep(200)
        requestQueue.add(objectRequest)


    }



    fun storing(salaryObj : JSONObject){ // Storing fields

        //Log.v("v_tag", "Checking length of stored object: ${salaryObj.length()}")

        var emp_number : Int = salaryObj.get("employee_id").toString().toInt()
        var emp_name : String = salaryObj.get("employee_name").toString()
        var dept_name :Int = salaryObj.get("dept_number").toString().toInt()
        var annual_salary: Float = salaryObj.get("annual_salary").toString().toFloat()
        var per_increase: Float = salaryObj.get("raise").toString().toFloat()


        var updated_salaryArray: Array<Float> = calculation(annual_salary, per_increase) // Storing new salary in array that is calculated in a function
        var raise:Float = updated_salaryArray[0]
        var new_salary:Float = updated_salaryArray[1]

        switchActivity(emp_number, emp_name,dept_name,annual_salary,per_increase,raise,new_salary) // Passing values to new activity
    }


    fun calculation(annual_salary : Float, percent : Float) : Array<Float>{ //Calculating the new salary

        var raise: Float = annual_salary * percent
        var new_salary : Float = raise + annual_salary

        val update_salary:Array<Float> = arrayOf(raise,new_salary) // Stored as an array since you can't pass back two variables at once in Kotlin

        return update_salary
    }



    fun switchActivity(emp_num : Int, emp_name : String, dept_name : Int, annual_salary: Float , per_increase: Float,raise:Float,new_salary:Float ){

        //Passing values to activity.

        val intent = Intent(this@MainActivity, Activity2::class.java)

        intent.putExtra("employee_num", emp_num.toString())
        intent.putExtra("employee_name", emp_name.toString())
        intent.putExtra("dept_name", dept_name.toString())
        intent.putExtra("annual_salary", annual_salary.toString())
        intent.putExtra("per_increase", per_increase.toString())
        intent.putExtra("raise", raise.toString())
        intent.putExtra("new_salary", new_salary.toString())

        progressBar.visibility = View.GONE //Hiding the loading circle
        startActivity(intent) // Switching to new activity
    }

}// End of MainActivity
