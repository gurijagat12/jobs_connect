package ku.cwk.jobsconnect.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.FragmentDashboardBinding
import ku.cwk.jobsconnect.ui.home.viewmodel.HomeViewModel
import ku.cwk.jobsconnect.ui.onboard.view.LoginActivity
import ku.cwk.jobsconnect.util.view.BaseFragment

class DashboardFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(parentActivity)[HomeViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val cvData = viewModel.cvData
            tvName.text = getString(
                R.string.ph_space, cvData.personal?.firstName ?: "",
                cvData.personal?.lastName ?: ""
            )

            btSubmit.setOnClickListener(this@DashboardFragment)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> {
                viewModel.logout()
                startActivity(Intent(parentActivity, LoginActivity::class.java))
                parentActivity.finish()
            }
        }
    }
}