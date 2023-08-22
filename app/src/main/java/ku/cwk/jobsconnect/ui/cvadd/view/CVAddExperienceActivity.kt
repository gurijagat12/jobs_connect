package ku.cwk.jobsconnect.ui.cvadd.view

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivityCvAddExperienceBinding
import ku.cwk.jobsconnect.ui.cvadd.viewmodel.CVViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class CVAddExperienceActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCvAddExperienceBinding
    private lateinit var viewModel: CVViewModel
    private var isDateTo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCvAddExperienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CVViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUI()
    }

    private fun initUI() {
        if (intent.hasExtra(Constants.INTENT_DATA)) {
            supportActionBar?.title = getString(R.string.education)
            binding.apply {
                tvTitleH.text = "Institute Name"
                tvDetailsH.text = "Grades"
                tvCompanyH.text = "Course Name"
            }
        }

        viewModel.userId = intent.getIntExtra(Constants.USER_ID, 0)
        viewModel.cvStatus.observe(this, cvObserver)
        binding.apply {
            btSubmit.setOnClickListener(this@CVAddExperienceActivity)
            tvFrom.setOnClickListener(this@CVAddExperienceActivity)
            tvTo.setOnClickListener(this@CVAddExperienceActivity)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> {
                if (binding.tvTitle.text.toString().trim().isEmpty() ||
                    binding.tvCompany.text.toString().trim().isEmpty() ||
                    binding.tvDetails.text.toString().trim().isEmpty() ||
                    binding.tvFrom.text.toString().trim().isEmpty() ||
                    binding.tvTo.text.toString().trim().isEmpty()
                ) {
                    showAlert(
                        message = getString(R.string.fill_all),
                        isError = false
                    )
                    return
                }

                if (intent.hasExtra(Constants.INTENT_DATA)) {
                    viewModel.cvAddQualificationData.user?.userId = viewModel.userId
                    viewModel.cvAddQualificationData.data?.apply {
                        instituteName = binding.tvTitle.text.toString().trim().ifEmpty { null }
                        courseName = binding.tvCompany.text.toString().trim().ifEmpty { null }
                        grades = binding.tvDetails.text.toString().trim().ifEmpty { null }
                        fromDate = viewModel.cvAddExperienceData.data?.fromDate
                        toDate = viewModel.cvAddExperienceData.data?.toDate
                    }

                    viewModel.addCVDetails(viewModel.cvAddQualificationData)

                } else {
                    viewModel.cvAddExperienceData.user?.userId = viewModel.userId
                    viewModel.cvAddExperienceData.data?.apply {
                        jobTitle = binding.tvTitle.text.toString().trim().ifEmpty { null }
                        companyName = binding.tvCompany.text.toString().trim().ifEmpty { null }
                        jobRole = binding.tvDetails.text.toString().trim().ifEmpty { null }
                    }
                    viewModel.addCVDetails(viewModel.cvAddExperienceData)
                }
            }

            binding.tvFrom -> {
                isDateTo = false
                showDatePicker()
            }

            binding.tvTo -> {
                isDateTo = true
                showDatePicker()
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
                Toast.makeText(this, R.string.cv_submitted, Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
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

    private fun showDatePicker() {
        var calendar = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            this,
            { _, yearPicker, monthOfYear, dayOfMonth ->
                calendar = Calendar.getInstance().apply {
                    set(yearPicker, monthOfYear, dayOfMonth)
                }
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(calendar.time)

                val dayStr = String.format(Locale.getDefault(), "%02d", dayOfMonth)

                if (!isDateTo) {
                    viewModel.cvAddExperienceData.data?.fromDate = date

                    binding.tvFrom.setText(
                        getString(
                            R.string.date_format,
                            dayStr,
                            calendar.getDisplayName(
                                Calendar.MONTH,
                                Calendar.LONG,
                                Locale.getDefault()
                            ),
                            yearPicker
                        )
                    )
                } else {
                    viewModel.cvAddExperienceData.data?.toDate = date

                    binding.tvTo.setText(
                        getString(
                            R.string.date_format,
                            dayStr,
                            calendar.getDisplayName(
                                Calendar.MONTH,
                                Calendar.LONG,
                                Locale.getDefault()
                            ),
                            yearPicker
                        )
                    )
                }
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()
    }

}