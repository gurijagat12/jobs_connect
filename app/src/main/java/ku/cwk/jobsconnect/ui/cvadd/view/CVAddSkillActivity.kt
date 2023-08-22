package ku.cwk.jobsconnect.ui.cvadd.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivityCvAddSkillBinding
import ku.cwk.jobsconnect.ui.cvadd.viewmodel.CVViewModel
import ku.cwk.jobsconnect.ui.home.model.CvProfile
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity

class CVAddSkillActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCvAddSkillBinding
    private lateinit var viewModel: CVViewModel
    private var skillList = mutableListOf<String>()
    private var isRemove = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCvAddSkillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CVViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUI()
    }

    private fun initUI() {
        viewModel.cvStatus.observe(this, cvObserver)
        viewModel.userId = intent.getIntExtra(Constants.USER_ID, 0)
        val cvData = Gson().fromJson(
            intent.getStringExtra(Constants.INTENT_DATA),
            CvProfile::class.java
        )
        binding.apply {
            //add skills
            val skillCommas = cvData.hasSkills ?: ""
            if (skillCommas.isNotEmpty()) {
                tvSkillEmpty.visibility = View.GONE

                skillList = skillCommas.split(",").toMutableList()
                skillList.forEach { addChip(it) }

            } else tvSkillEmpty.visibility = View.VISIBLE

            btSubmit.setOnClickListener(this@CVAddSkillActivity)
        }
    }

    private fun addChip(skill: String) {
        val chip = Chip(this)
        chip.text = skill
        chip.chipStrokeWidth = 4f
        chip.chipBackgroundColor =
            ContextCompat.getColorStateList(this, R.color.purple_500)
        chip.chipStrokeColor = ContextCompat.getColorStateList(this, R.color.teal_700)
        chip.isCloseIconVisible = true
        chip.closeIconTint = ContextCompat.getColorStateList(this, R.color.white)
        chip.setTextColor(resources.getColor(R.color.white, null))

        chip.setOnCloseIconClickListener { chipClick ->
            skillList.removeAll { it == chip.text.toString() }
            isRemove = true
            updateSkillsApi()
            binding.chipSkill.removeView(chipClick)
        }

        binding.chipSkill.addView(chip)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> {

                if (binding.etSkill.text.toString().trim().isEmpty()) {
                    showAlert(
                        message = getString(R.string.cv_skill_add_please),
                        isError = false
                    )
                    return
                }
                if (skillList.contains(binding.etSkill.text.toString().trim())) {
                    showAlert(
                        message = getString(R.string.cv_skill_exist),
                        isError = false
                    )
                    return
                }
                skillList.add(binding.etSkill.text.toString().trim())
                updateSkillsApi()
            }
        }
    }

    private fun updateSkillsApi() {
        viewModel.cvAddRequestData.apply {
            userId = viewModel.userId
            hasSkills = skillList.joinToString(",")

        }
        viewModel.addCVDetails()
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
                    message = getString(R.string.cv_submitted_skills),
                    isError = false
                )
                if (!isRemove) {
                    addChip(binding.etSkill.text.toString().trim())
                    binding.etSkill.setText("")
                } else {
                    isRemove = false
                }

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