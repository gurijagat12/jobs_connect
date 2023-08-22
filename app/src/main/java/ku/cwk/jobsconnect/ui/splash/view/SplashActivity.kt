package ku.cwk.jobsconnect.ui.splash.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionManager
import android.view.View
import ku.cwk.jobsconnect.databinding.ActivitySplashBinding
import ku.cwk.jobsconnect.ui.employer.view.EmployerActivity
import ku.cwk.jobsconnect.ui.home.view.HomeActivity
import ku.cwk.jobsconnect.ui.onboard.view.LoginActivity
import ku.cwk.jobsconnect.ui.onboard.viewmodel.LoginViewModel
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.view.BaseActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                TransitionManager.beginDelayedTransition(binding.root)
                binding.splashImg.visibility = View.VISIBLE
                timerEnd.start()
            }
        }
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerEnd.cancel()
    }

    private val timerEnd = object : CountDownTimer(2500, 1000) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            if (binding.splashImg.visibility != View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.root)
                binding.splashImg.visibility = View.VISIBLE
                start()
            } else {
                val pref = this@SplashActivity.getSharedPreferences(
                    Constants.SHARED_PREF,
                    Context.MODE_PRIVATE
                )

                when (pref.getInt(Constants.USER_TYPE, 0)) {
                    1 -> startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    2 -> startActivity(Intent(this@SplashActivity, EmployerActivity::class.java))
                    else -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            }
        }
    }
}