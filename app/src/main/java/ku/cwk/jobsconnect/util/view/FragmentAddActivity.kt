package ku.cwk.jobsconnect.util.view

import android.os.Bundle
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.ui.home.view.HomeFragment
import ku.cwk.jobsconnect.util.Constants

class FragmentAddActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    HomeFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}