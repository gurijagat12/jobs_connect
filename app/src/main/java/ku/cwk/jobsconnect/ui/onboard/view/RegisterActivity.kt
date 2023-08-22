package ku.cwk.jobsconnect.ui.onboard.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.ActivityRegisterBinding
import ku.cwk.jobsconnect.ui.employer.view.EmployerActivity
import ku.cwk.jobsconnect.ui.home.view.HomeActivity
import ku.cwk.jobsconnect.ui.onboard.viewmodel.LoginViewModel
import ku.cwk.jobsconnect.util.NetworkHandler
import ku.cwk.jobsconnect.util.showAlert
import ku.cwk.jobsconnect.util.view.BaseActivity
import java.util.regex.Pattern


class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.loginStatus.observe(this, loginObserver)
        binding.btSubmit.setOnClickListener(this)
        binding.btLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSubmit -> login()
            binding.btLogin -> finish()
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val first = binding.etFirst.text.toString().trim()
        val last = binding.etLast.text.toString().trim()
        val phone = binding.etMobile.text.toString().trim()
        val userPass = binding.etPassword.text.toString().trim()

        if (first.isEmpty() || userPass.isEmpty() || last.isEmpty() ||
            email.isEmpty() || phone.isEmpty()
        ) {
            showAlert(
                message = getString(R.string.fill_all),
                isError = false
            )
        } else if (!isValidEmail(email)) {
            showAlert(
                message = getString(R.string.fill_email),
                isError = false
            )
        } else if (phone.length < 10) {
            showAlert(
                message = getString(R.string.fill_mobile),
                isError = false
            )
        } else if (userPass.length < 8) {
            showAlert(
                message = getString(R.string.fill_login_pass),
                isError = false
            )
        } else {
            viewModel.registerData.apply {
                firstName = first
                lastName = last
                this.email = email
                this.phone = phone
                password = userPass
                userType = if (binding.rbCandidate.isChecked)
                    1
                else 2
            }

            viewModel.register()
        }
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
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_LONG).show()

                when (viewModel.userType) {
                    1 -> startActivity(Intent(this, HomeActivity::class.java))
                    2 -> startActivity(Intent(this, EmployerActivity::class.java))
                }
                finishAffinity()
            }
            else -> {
                hideProgressDialog()
                showAlert(
                    message = getString(R.string.invalid_creds), isError = false
                )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}