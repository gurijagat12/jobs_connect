package ku.cwk.jobsconnect.ui.cvadd.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivityCvAddBinding
import ku.cwk.jobsconnect.ui.cvadd.viewmodel.CVViewModel
import ku.cwk.jobsconnect.ui.home.model.CvProfile
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity

class CVAddActivity : BaseActivity(), OnClickListener {
    private lateinit var binding: ActivityCvAddBinding
    private lateinit var viewModel: CVViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCvAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CVViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUI()
    }

    private fun initUI() {
        viewModel.userId = intent.getIntExtra(Constants.USER_ID, 0)
        val cvData = Gson().fromJson(
            intent.getStringExtra(Constants.INTENT_DATA),
            CvProfile::class.java
        )
        binding.apply {
            btSubmit.setOnClickListener(this@CVAddActivity)
            tvAge.setText(cvData?.age ?: "")
            tvAddress.setText(cvData?.address ?: "")
            tvLanguage.setText(cvData?.languages ?: "")
            tvHobbies.setText(cvData?.hobbies ?: "")

            when (cvData.gender) {
                Constants.GENDER_MALE -> rbMale.isChecked = true
                Constants.GENDER_FEMALE -> rbFemale.isChecked = true
                Constants.GENDER_OTHER -> rbOther.isChecked = true
            }
        }
        viewModel.cvStatus.observe(this, cvObserver)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> {
                viewModel.cvAddRequestData.apply {
                    userId = viewModel.userId
                    age = binding.tvAge.text.toString().trim().ifEmpty { null }
                    languages = binding.tvLanguage.text.toString().trim().ifEmpty { null }
                    hobbies = binding.tvHobbies.text.toString().trim().ifEmpty { null }
                    address = binding.tvAddress.text.toString().trim().ifEmpty { null }
                    if (binding.rbMale.isChecked) gender = Constants.GENDER_MALE
                    else if (binding.rbFemale.isChecked) gender = Constants.GENDER_FEMALE
                    else if (binding.rbOther.isChecked) gender = Constants.GENDER_OTHER
                }
                viewModel.addCVDetails()
            }
        }
    }

    private val cvObserver: Observer<String> = Observer { status ->
        when (status) {
            NetworkHandler.STATUS_NONE -> {
            }
            NetworkHandler.STATUS_LOADING -> {
                showProgressDialog()
            }
            NetworkHandler.STATUS_SUCCESS -> {
                hideProgressDialog()
                showAlert(
                    message = getString(R.string.cv_submitted),
                    isError = false
                )
                setResult(Activity.RESULT_OK)
            }
            else -> {
                hideProgressDialog()
                showAlert(
                    message = getString(R.string.sorry_something_went_wrong_try),
                    isError = false
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}