package presentation.base

abstract class BasePresenter<View : BaseView> {

    protected var view: View? = null
        private set

    fun attach(view: View) {
        this.view = view
        onViewAttached()
    }

    fun detach() {
        this.view = null
        onViewDetached()
    }

    open fun onViewAttached() {}
    open fun onViewDetached() {}
}