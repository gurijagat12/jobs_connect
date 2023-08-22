package ku.cwk.jobsconnect.util.view

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    lateinit var parentActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = requireActivity() as BaseActivity
    }

    open fun showProgressDialog() {
        parentActivity.showProgressDialog()
    }

    open fun hideProgressDialog() {
        parentActivity.hideProgressDialog()
    }
}