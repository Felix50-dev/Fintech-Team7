package com.aad.fintech.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aad.fintech.R
import com.aad.fintech.databinding.FragmentOtpBinding
import com.aad.fintech.ui.views.activities.HomeActivity
import com.aad.fintech.ui.views.activities.RegisterActivity

private const val SIGN_IN: String = "SIGN_IN"
private const val SIGN_UP: String = "SIGN_UP"

class OtpFragment : Fragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!

    private val args: OtpFragmentArgs by navArgs()

    companion object {
        @JvmStatic
        fun newInstance() = OtpFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        initViews()
        setEvents()
    }

    private fun setToolbar() {
        var toolbar = binding.toolbar.toolbar
        toolbar.setNavigationIcon(R.drawable.black_arrow_back)
        toolbar.setNavigationOnClickListener { it.findNavController().popBackStack() }
        toolbar.setTitle(R.string.verify_phone)
    }

    private fun initViews() {
        binding.otpMessage.text = "Code is sent to ${args.phone}"
        initOptFields()
    }

    private fun initOptFields() {
        binding.otpField1.text = null
        binding.otpField2.text = null
        binding.otpField3.text = null
        binding.otpField4.text = null
    }

    private fun setEvents() {
        binding.nextButton.setOnClickListener { completeOtp() }

        binding.fieldsLayout.setOnClickListener() {
            binding.otpField1.requestFocus()
        }

        binding.otpField1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) binding.otpField2.requestFocus()
            }
        } )

        binding.otpField2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) binding.otpField3.requestFocus()
            }
        } )

        binding.otpField3.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) binding.otpField4.requestFocus()
            }
        } )

        binding.otpField4.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                //if (s.length == 1) binding.otpField4.clearFocus()
            }
        } )

        binding.resendOtpButton.setOnClickListener {
            initOptFields()
            binding.otpMessage.text = "New code is sent to ${args.phone}"
        }
    }

    private fun completeOtp() {
        val isValid = validateOtp()
        if (isValid != null) {
            if (isValid) {
                if (args.signMode == SIGN_IN)
                    startActivity(Intent(context, HomeActivity::class.java))
                else if (args.signMode == SIGN_UP) {
                    val action = OtpFragmentDirections.actionOtpFragmentToRegisterFragment()
                    findNavController().navigate(action)
                }
            } else {
                Toast.makeText(context, "Wrong OTP code!", Toast.LENGTH_LONG).show()
                initOptFields()
            }
        } else {
            Toast.makeText(context, "Incomplete OTP code!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateOtp(): Boolean? {
        val otp1 = binding.otpField1.text?.toString() ?: ""
        val otp2 = binding.otpField2.text?.toString() ?: ""
        val otp3 = binding.otpField3.text?.toString() ?: ""
        val otp4 = binding.otpField4.text?.toString() ?: ""

        if (otp1.isNotEmpty() &&
            otp2.isNotEmpty() &&
            otp3.isNotEmpty() &&
            otp4.isNotEmpty()) {
            val otp = otp1 + otp2 + otp3 + otp4
            return otp == "0000"
        }

        return null
    }

}