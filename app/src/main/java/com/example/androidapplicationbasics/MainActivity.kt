package com.example.androidapplicationbasics

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplicationbasics.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val stack = ArrayDeque<String>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSubmit.setOnClickListener(onClick)
        binding.btnReset.setOnClickListener(onClick)
        binding.btnUndo.setOnClickListener(onClick)
    }

    private val onClick = fun(v: View) {
        when (v.id) {
            R.id.btnReset -> reset()
            R.id.btnSubmit -> submit()
            R.id.btnUndo -> undo()
        }
    }

    private fun submit() {
        val enteredText = binding.editText.text.trim().toString()
        if (enteredText.isEmpty()) {
            showToastMessage(getString(R.string.msg_enter_text))
            return
        }

        val text = getString(
            R.string.text_view_text, stack.size + 1, enteredText
        )

        binding.textView.text = text
        binding.editText.setText("")
        stack.addLast(enteredText)

        showToastMessage(getString(R.string.msg_submit_text, enteredText))
    }

    private fun reset() {
        stack.clear()
        binding.textView.text = ""
        binding.editText.setText("")

        showToastMessage(getString(R.string.msg_reset_text))
    }

    private fun undo() {
        if (stack.isEmpty()) {
            binding.editText.setText("")
            showToastMessage(getString(R.string.msg_undo_no_elements_text))
            return
        }

        binding.textView.text = ""
        val lastElement = stack.removeLast()
        binding.editText.setText(lastElement)
        showToastMessage(getString(R.string.msg_undo_undo_text, lastElement))
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}