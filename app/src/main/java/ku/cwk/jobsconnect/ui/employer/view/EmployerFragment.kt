package ku.cwk.jobsconnect.ui.employer.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.FragmentEmployerBinding
import ku.cwk.jobsconnect.ui.home.viewmodel.HomeViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseFragment


class EmployerFragment : BaseFragment(), OnClickListener {

    private var _binding: FragmentEmployerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(parentActivity)[HomeViewModel::class.java]
        _binding = FragmentEmployerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cvStatus.observe(viewLifecycleOwner, cvObserver)
        viewModel.fetchEmployerDetails()

        binding.btSubmit.setOnClickListener(this)
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
                    message = getString(R.string.sorry_something_went_wrong_try), isError = false
                )
            }
        }
    }

    private fun updateData() {
        binding.apply {
            //click listeners
            //tvAbout.setOnClickListener(this@EmployerFragment)

            //add data
            val cvData = viewModel.cvData
            tvName.text = getString(
                R.string.ph_space, cvData.personal?.firstName ?: "", cvData.personal?.lastName ?: ""
            )

            cvData.personal?.apply {
                tvEmail.text = email ?: "-"
                tvMobile.text = phone ?: "-"
            }
        }
    }


    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> {
                startActivity(
                    Intent(
                        parentActivity, SearchActivity::class.java
                    ).apply {
                        putExtra(Constants.INTENT_DATA, binding.etSkill.text.toString())
                    })
            }
        }
    }
}