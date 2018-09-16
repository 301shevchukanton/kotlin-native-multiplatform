package example.shevchuk.com.androidapplication.view.view_model

/**
 * Created by Anton Shevchuk on 16.09.2018.
 */

class ViewModelError<Error>(private var error: Error) {
	private var isHandled: Boolean = false

	fun handle(action : (Error) -> Unit) {
		if (!isHandled) {
			isHandled = true
			action.invoke(error)
		}
	}
}
