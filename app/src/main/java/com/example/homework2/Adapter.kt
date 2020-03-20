package com.example.homework2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.habit_item.view.*

class Adapter(private val habits: List<Habit>) : RecyclerView.Adapter<Adapter.HabitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val habitRowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_item, parent, false)

        return HabitHolder(habitRowView)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitHolder, position: Int) = holder.bind(habits[position])


    class HabitHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var habit: Habit? = null

        fun bind(habit: Habit) {
            this.habit = habit
            view.itemName.text = habit.name
            view.itemDescription.text = habit.description
            view.itemPriority.text = "Приоритет: ${habit.priority}"
            view.itemType.text = if (habit.type == HabitType.GOOD) "Полезная" else "Вредная"
            view.itemCount.text = "Количество: ${habit.count}"
            view.itemPeriod.text = "Раз в ${habit.period} ч."
        }

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context as MainActivity
            val intent = Intent(context, HabitActivity::class.java)
                .apply { putExtra("habit", habit) }
                .apply { putExtra("habitPosition", adapterPosition) }

            context.startActivityForResult(intent, 1)
        }
    }
}