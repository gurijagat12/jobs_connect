package ku.cwk.jobsconnect.ui.employer.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivitySearchBinding
import ku.cwk.jobsconnect.interfaces.TagDataListener
import ku.cwk.jobsconnect.ui.employer.adapter.SearchAdapter
import ku.cwk.jobsconnect.ui.home.viewmodel.HomeViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity
import ku.cwk.jobsconnect.util.view.FragmentAddActivity

class SearchActivity : BaseActivity(), TagDataListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: HomeViewModel
    private var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initUI()
    }

    private fun initUI() {
        search = intent.getStringExtra(Constants.INTENT_DATA) ?: ""
        binding.tvSkill.text = search
        viewModel.searchStatus.observe(this, cvObserver)
        viewModel.searchSkills(search)
    }

    override fun sendData(tag: String, data: Any?) {
        startActivity(
            Intent(
                this, FragmentAddActivity::class.java
            ).apply {
                putExtra(Constants.INTENT_DATA, data as String)
            })
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
                showAlert(
                    message = getString(R.string.sorry_something_went_wrong_try), isError = false
                )
            }
        }
    }

    private fun updateData() {
        if (viewModel.searchList.isEmpty())
            binding.tvSkillEmpty.visibility = View.VISIBLE
        else {
            binding.rvSearch.adapter = SearchAdapter(viewModel.searchList, this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}