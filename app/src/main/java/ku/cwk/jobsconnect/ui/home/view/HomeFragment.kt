package ku.cwk.jobsconnect.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.FragmentHomeBinding
import ku.cwk.jobsconnect.interfaces.TagDataListener
import ku.cwk.jobsconnect.ui.cvadd.view.CVAddActivity
import ku.cwk.jobsconnect.ui.cvadd.view.CVAddExperienceActivity
import ku.cwk.jobsconnect.ui.cvadd.view.CVAddSkillActivity
import ku.cwk.jobsconnect.ui.home.adapter.EducationAdapter
import ku.cwk.jobsconnect.ui.home.adapter.ExperienceAdapter
import ku.cwk.jobsconnect.ui.home.viewmodel.HomeViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseFragment

class HomeFragment() : BaseFragment(), OnClickListener,
    TagDataListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = parentActivity.intent.getStringExtra(Constants.INTENT_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(parentActivity)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cvStatus.observe(viewLifecycleOwner, cvObserver)
        viewModel.fetchCVDetails(email)

        if (email != null) {
            parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.apply {
                tvWelcome.visibility = View.GONE
                cvWelcome.visibility = View.GONE
                tvHello.visibility = View.GONE
                tvName.visibility = View.GONE

                tvSkill.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                tvEducation.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                tvExperience.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                tvAbout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
    }

    override fun sendData(tag: String, data: Any?) {

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
                updateData()
            }
            else -> {
                hideProgressDialog()
                parentActivity.showAlert(
                    message = getString(R.string.sorry_something_went_wrong_try),
                    isError = false
                )
            }
        }
    }

    private fun updateData() {
        binding.apply {
            val cvData = viewModel.cvData

            //click listeners
            if (email == null) {
                tvAbout.setOnClickListener(this@HomeFragment)
                tvSkill.setOnClickListener(this@HomeFragment)
                tvExperience.setOnClickListener(this@HomeFragment)
                tvEducation.setOnClickListener(this@HomeFragment)
            } else
                parentActivity.title = getString(
                    R.string.ph_space, cvData.personal?.firstName ?: "",
                    cvData.personal?.lastName ?: ""
                )

            //add data
            tvName.text = getString(
                R.string.ph_space, cvData.personal?.firstName ?: "",
                cvData.personal?.lastName ?: ""
            )
            if ((cvData.cvProfile?.hasJob ?: "0") == "1") {
                tvRole.text = cvData.experience[0].jobTitle ?: "-"
            }
            cvData.personal?.apply {
                tvEmail.text = email ?: "-"
                tvMobile.text = phone ?: "-"
            }
            cvData.cvProfile?.apply {
                tvAge.text = age ?: "-"
                tvGender.text = gender ?: "-"
                tvLanguage.text = languages ?: "-"
                tvHobbies.text = hobbies ?: "-"
                tvAddress.text = address ?: "-"
            }

            //add education
            if ((cvData.cvProfile?.qualification ?: "0") == "0") {
                tvEducationEmpty.visibility = View.VISIBLE
            } else {
                tvEducationEmpty.visibility = View.GONE
                rvEducation.apply {
                    adapter = EducationAdapter(cvData.qualification)
                }
            }

            //add experience
            if ((cvData.cvProfile?.hasJob ?: "0") == "0") {
                tvExperienceEmpty.visibility = View.VISIBLE
            } else {
                tvExperienceEmpty.visibility = View.GONE
                rvExperience.apply {
                    adapter = ExperienceAdapter(cvData.experience)
                }
            }

            //add skills
            binding.chipSkill.removeAllViews()
            val skillCommas = cvData.cvProfile?.hasSkills ?: ""
            if (skillCommas.isNotEmpty()) {
                tvSkillEmpty.visibility = View.GONE

                val list: List<String> = skillCommas.split(",").toList()
                list.forEach { addChip(it) }

            } else tvSkillEmpty.visibility = View.VISIBLE
        }
    }

    private fun addChip(skill: String) {
        val chip = Chip(requireContext())
        chip.text = skill
        chip.chipStrokeWidth = 4f
        chip.chipBackgroundColor =
            ContextCompat.getColorStateList(parentActivity, R.color.purple_500)
        chip.chipStrokeColor = ContextCompat.getColorStateList(parentActivity, R.color.teal_700)
        chip.setTextColor(resources.getColor(R.color.white, null))
        binding.chipSkill.addView(chip)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.tvAbout -> {
                cvAddResult.launch(
                    Intent(
                        parentActivity, CVAddActivity::class.java
                    ).apply {
                        putExtra(Constants.INTENT_DATA, Gson().toJson(viewModel.cvData.cvProfile))
                        putExtra(Constants.USER_ID, viewModel.cvData.cvProfile?.userId ?: 0)
                    }
                )
            }
            binding.tvSkill -> {
                cvAddResult.launch(
                    Intent(
                        parentActivity, CVAddSkillActivity::class.java
                    ).apply {
                        putExtra(Constants.INTENT_DATA, Gson().toJson(viewModel.cvData.cvProfile))
                        putExtra(Constants.USER_ID, viewModel.cvData.cvProfile?.userId ?: 0)
                    }
                )
            }
            binding.tvExperience -> {
                cvAddResult.launch(
                    Intent(
                        parentActivity, CVAddExperienceActivity::class.java
                    ).apply {
                        putExtra(Constants.USER_ID, viewModel.cvData.cvProfile?.userId ?: 0)
                    }
                )
            }
            binding.tvEducation -> {
                cvAddResult.launch(
                    Intent(
                        parentActivity, CVAddExperienceActivity::class.java
                    ).apply {
                        putExtra(Constants.USER_ID, viewModel.cvData.cvProfile?.userId ?: 0)
                        putExtra(Constants.INTENT_DATA, Constants.INTENT_EDUCATION)
                    }
                )
            }
        }
    }

    private val cvAddResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK)
                viewModel.fetchCVDetails(email)
        }
}