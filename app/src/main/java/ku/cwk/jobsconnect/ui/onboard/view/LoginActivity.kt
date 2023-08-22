package ku.cwk.jobsconnect.ui.onboard.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivityLoginBinding
import ku.cwk.jobsconnect.ui.employer.view.EmployerActivity
import ku.cwk.jobsconnect.ui.home.view.HomeActivity
import ku.cwk.jobsconnect.ui.onboard.viewmodel.LoginViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.loginStatus.observe(this, loginObserver)
        binding.btSubmit.setOnClickListener(this)
        binding.btRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> login()
            binding.btRegister ->
                startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val userName = binding.etUser.text.toString().trim()
        val userPass = binding.etPassword.text.toString().trim()

        if (userName.isEmpty() || userPass.isEmpty()) {
            showAlert(
                message = getString(R.string.fill_login),
                isError = false
            )
        } else if (userPass.length < 8) {
            showAlert(
                message = getString(R.string.fill_login_pass),
                isError = false
            )
        } else viewModel.login(userName, userPass)
    }

    private val loginObserver: Observer<String> = Observer { status ->
        when (status) {
            NetworkHandler.STATUS_NONE -> {
            }
            NetworkHandler.STATUS_LOADING -> {
                showProgressDialog()
            }
            NetworkHandler.STATUS_SUCCESS -> {
                hideProgressDialog()
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()

                when (viewModel.userType) {
                    1 -> startActivity(Intent(this, HomeActivity::class.java))
                    2 -> startActivity(Intent(this, EmployerActivity::class.java))
                }
                finish()
            }
            else -> {
                hideProgressDialog()
                showAlert(
                    message = getString(R.string.invalid_creds), isError = false
                )
            }
        }
    }
}