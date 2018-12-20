package com.example.josh.advcompproj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_2.*
import kotlinx.android.synthetic.main.activity_main.*
import  android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_2.view.*
import kotlinx.android.synthetic.main.row_main.view.*


class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)


        //Retrieving information from previous activity
        val emp_num =  intent.getStringExtra( "employee_num").toString()
        val emp_name = intent.getStringExtra("employee_name").toString()
        val dept_num = intent.getStringExtra( "dept_name").toString()
        val annual_salary = intent.getStringExtra( "annual_salary").toString()
        val per = intent.getStringExtra( "per_increase").toString()
        val raise = intent.getStringExtra( "raise").toString()
        val new_salary = intent.getStringExtra("new_salary").toString()

        employee_Name.text = ("${emp_name.toString()}'s Salary")
        val data = arrayListOf<String>(emp_name,emp_num, dept_num,annual_salary,per,raise,new_salary)
        val titles = arrayListOf<String>("Employee", "Employee Number", "Department", "Annual Salary","Percent Increase", "Raise", "New Salary")

        thisView.list.adapter = MyCustomAdapters(data, titles)
    }
    private class MyCustomAdapters(data_list : ArrayList<String>, title_list : ArrayList<String>) : BaseAdapter(){

        private val titles = title_list
        private val data = data_list

        //Rows in list
        override fun getCount(): Int {
            return titles.size
        }

        override fun getItemId(position: Int): Long {
            return  position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val rowMain: View

            //checking if row is empty. If not, create(infalte) new row
            if (convertView == null){
                val layout_inflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layout_inflator.inflate(R.layout.row_main, viewGroup, false)

                val viewHolder = ViewHolder(rowMain.dataView, rowMain.titleView)
                rowMain.tag = viewHolder

            }else{
                //row exists, keep row and don't query for another again
                rowMain = convertView
            }

            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.nameTextView_s.text =  data.get(position)
            viewHolder.positionTextView2.text= titles.get(position)

            // acessing the text. Need to go through rowMain to get to row_main.xml

            return rowMain

        }

        private class ViewHolder (val nameTextView_s: TextView, val positionTextView2: TextView){

        }

    }
}
