package com.example.homework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.habits_list.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val habits = ArrayList<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val savedHabits = savedInstanceState?.getParcelableArrayList<Habit>("habits")
        if (savedHabits != null)
            habits.addAll(savedHabits)


        adapter = Adapter(habits)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            startActivityForResult(Intent(this, HabitActivity::class.java), 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("habits", habits)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val habit = data?.extras?.getParcelable<Habit>("habit")
        val position = data?.extras?.getInt("habitPosition")
        if (requestCode == 0) {
            if (habit != null) {
                habits.add(habit)
                adapter.notifyDataSetChanged()
            }
        } else if (requestCode == 1) {
            if (habit != null && position != null) {
                habits[position] = habit
                adapter.notifyItemChanged(position)
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }
}
