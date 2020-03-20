package com.example.homework2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_habit.*

class HabitActivity : AppCompatActivity() {

    private var habit: Habit? = null
    private var habitPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)

        habit = intent.extras?.getParcelable("habit")
            ?: savedInstanceState?.getParcelable("habit")

        habitPosition = intent.extras?.getInt("habitPosition")
            ?: savedInstanceState?.getInt("habitPosition")

        if (habit != null)
            changeHabitView()

        saveHabitButton.setOnClickListener { onClick() }
    }

    private fun onClick() {
        val habit = getHabitFromView()
        val intent = Intent().apply { putExtra("habit", habit) }
        if (habitPosition != null) {
            intent.apply { putExtra("habitPosition", habitPosition as Int) }
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("habit", getHabitFromView())
        outState.putInt("habitPosition", habitPosition as Int)
    }

    private fun changeHabitView() {
        val habit = this.habit as Habit
        habitNameText.setText(habit.name)
        habitDescription.setText(habit.description)
        prioritySpinner.setSelection(habit.priority)
        priorityTypeGroup.check(if (habit.type == HabitType.GOOD) goodRadioButton.id else badRadioButton.id)
        repetitionNumber.setText(habit.period.toString())
        periodNumber.setText(habit.period.toString())
    }

    private fun getHabitFromView(): Habit {
        val name = habitNameText.text.toString()
        val description = habitDescription.text.toString()
        val priority = prioritySpinner.selectedItemPosition
        val type = if (priorityTypeGroup.checkedRadioButtonId == goodRadioButton.id)
            HabitType.GOOD else HabitType.BAD
        val count = repetitionNumber.text.toString().toIntOrNull() ?: 0
        val period = periodNumber.text.toString().toIntOrNull() ?: 0

        return Habit(name, description, priority, type, count, period)
    }
}