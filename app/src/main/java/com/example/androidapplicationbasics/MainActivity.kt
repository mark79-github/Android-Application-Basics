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
            R.id.btnSubmit -> submit()
            R.id.btnReset -> reset()
            R.id.btnUndo -> undo()
        }
    }

    private fun submit() {
        val enteredText = binding.editText.text.trim().toString()
        if (enteredText.isEmpty()) {
            toastMessage(getString(R.string.msg_enter_text))
            return
        }

        val text = getString(
            R.string.text_view_text, stack.size + 1, enteredText
        )

        binding.textView.text = text
        binding.editText.setText("")
        stack.addLast(enteredText)
        binding.btnUndo.isEnabled = true
        binding.btnReset.isEnabled = true

        toastMessage(getString(R.string.msg_submit_text, enteredText))
    }

    private fun reset() {
        stack.clear()
        binding.textView.text = ""
        binding.editText.setText("")

        binding.btnReset.isEnabled = false
        binding.btnUndo.isEnabled = false
        toastMessage(getString(R.string.msg_reset_text))
    }

    private fun undo() {
        val lastElement = stack.removeLast()
        binding.editText.setText(lastElement)
        binding.editText.setSelection(binding.editText.text.length)

        when (val peek = stack.lastOrNull()) {
            null -> {
                binding.textView.text = ""
                binding.btnReset.isEnabled = false
                binding.btnUndo.isEnabled = false
            }
            else -> binding.textView.text = getString(
                R.string.text_view_text, stack.size, peek
            )
        }

        toastMessage(getString(R.string.msg_undo_undo_text, lastElement))
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}