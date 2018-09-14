package example.shevchuk.com.androidapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import entity.Status
import entity.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.JSON

class MainActivity : AppCompatActivity() {
	val todo: Todo = Todo("123213", "!23213", Status.DONE)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		tvText.text = JSON.stringify(todo)
	}
}
