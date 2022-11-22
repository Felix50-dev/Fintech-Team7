package com.aad.fintech.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aad.fintech.R
import com.aad.fintech.databinding.FragmentPhoneBinding
import com.aad.fintech.ui.views.activities.LoginActivity
import com.aad.fintech.ui.views.activities.RegisterActivity
import com.aad.fintech.ui.views.adapters.Country
import com.aad.fintech.ui.views.adapters.CountryAdapter

private const val SIGN_IN: String = "SIGN_IN"
private const val SIGN_UP: String = "SIGN_UP"

class PhoneFragment : Fragment() {

    private var _binding: FragmentPhoneBinding? = null
    private val binding get() = _binding!!

    private val args: PhoneFragmentArgs by navArgs()

    private lateinit var countries: List<Country>
    private lateinit var country: Country

    companion object {
        @JvmStatic
        fun newInstance() = PhoneFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setToolbar() {
        var toolbar = binding.toolbar.toolbar
        toolbar.setNavigationIcon(R.drawable.black_close)
        toolbar.setNavigationOnClickListener {
            // it.findNavController().popBackStack()
        }
        toolbar.setTitle(R.string.continue_with_phone)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        initViews()
        setEvents()
    }

    private fun initViews() {
        binding.phoneFieldLayout.prefixTextView.updateLayoutParams {
            height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        binding.phoneFieldLayout.prefixTextView.gravity = Gravity.CENTER

        initCountryList()
        initCountry()
        //initRegisterOrLoginViews()

        binding.askForRegisterText.visibility = View.GONE
        binding.registerButton.visibility = View.GONE
        binding.askForLoginText.visibility = View.GONE
        binding.loginButton.visibility = View.GONE
    }

    private fun initRegisterOrLoginViews() {
        if (args.signMode == SIGN_IN) {
            setRegisterView()
        } else if (args.signMode == SIGN_UP) {
            setLoginView()
        }
    }

    private fun initCountryList() {
        countries = mutableListOf(
            Country("+228", "Togo", R.drawable.tg, 8),
            Country("+234", "Nigeria", R.drawable.ng, 10),
            Country("+250", "Rwanda", R.drawable.rw, 10),
        )
        val countryAdapter = context?.let { CountryAdapter(it, R.layout.item_country, countries) }
        binding.country.setAdapter(countryAdapter)
    }

    private fun initCountry() {
        selectCountry(countries[0])
    }

    private fun setEvents() {
        binding.countryLayout.setOnClickListener { binding.country.showDropDown() }
        binding.country.setOnItemClickListener { parent, _, position, _ ->
            val country = parent.getItemAtPosition(position) as Country?
            if (country != null) {
                selectCountry(country)
            }
        }
        binding.nextButton.setOnClickListener {
            if (validatePhone()) {
                val phone = "${country.code} ${binding.phoneField.text.toString()}"
                val action = PhoneFragmentDirections.actionPhoneFragmentToOtpFragment(phone, args.signMode)
                it.findNavController().navigate(action)
            }
        }
        binding.registerButton.setOnClickListener {
            var signUpIntent = Intent(context, RegisterActivity::class.java)
            signUpIntent.putExtra("signMode", SIGN_UP)
            startActivity(signUpIntent)
        }
        binding.loginButton.setOnClickListener {
            var signInIntent = Intent(context, LoginActivity::class.java)
            signInIntent.putExtra("signMode", SIGN_IN)
            startActivity(signInIntent)
        }
    }

    private fun selectCountry(country: Country) {
        this.country = country
        binding.country.text = null
        binding.country.setBackgroundResource(country.flag)
        binding.phoneFieldLayout.prefixText = country.code
        binding.phoneField.filters = arrayOf(InputFilter.LengthFilter(country.phoneLength))
    }

    private fun validatePhone(): Boolean {
        clearErrors()
        if ((binding.phoneField.text?.length ?: 0) != country.phoneLength) {
            binding.phoneFieldLayout.error = resources.getString(R.string.invalid_phone_error)
            return false
        }
        return true
    }

    private fun clearErrors() {
        binding.phoneFieldLayout.error = null
    }

    private fun setRegisterView() {
        binding.askForLoginText.visibility = View.GONE
        binding.loginButton.visibility = View.GONE
        binding.askForRegisterText.visibility = View.VISIBLE
        binding.registerButton.visibility = View.VISIBLE
    }

    private fun setLoginView() {
        binding.askForRegisterText.visibility = View.GONE
        binding.registerButton.visibility = View.GONE
        binding.askForLoginText.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
    }

}