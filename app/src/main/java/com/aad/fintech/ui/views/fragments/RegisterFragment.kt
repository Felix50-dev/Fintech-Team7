package com.aad.fintech.ui.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.aad.fintech.R
import com.aad.fintech.databinding.FragmentRegisterBinding
import com.aad.fintech.ui.views.adapters.Sex
import com.aad.fintech.ui.views.adapters.SexAdapter

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        toolbar.setTitle(R.string.register)
    }

    private fun initViews() {
        initSexList()
    }

    private fun initSexList() {
        val sex = Sex.values().toList()
        val sexAdapter = context?.let { SexAdapter(it, R.layout.item_sex, sex) }
        binding.sexField.setAdapter(sexAdapter)
    }

    private fun setEvents() {
        binding.sexFieldLayout.setOnClickListener { binding.sexField.showDropDown() }
        binding.sexField.setOnClickListener { binding.sexField.showDropDown() }
        binding.sexField.setOnItemClickListener { parent, _, position, _ ->
            val sex = parent.getItemAtPosition(position) as Sex?
            if (sex != null) {
                //binding.sexField.text = SpannableStringBuilder(sex.wording)
            }
        }
        binding.nextButton.setOnClickListener {
            if(validate()) {
                val action = RegisterFragmentDirections.actionRegisterFragmentToPinFragment(1)
                it.findNavController().navigate(action)
            }
        }
    }

    private fun validate(): Boolean {
        clearErrors()
        var valid = true

        val email = binding.emailField.text?.toString() ?: ""
        if (email.isNotEmpty() && !email.contains("@")) {
            binding.emailFieldLayout.error = resources.getString(R.string.invalid_email_error)
            valid = false
        }
        if (binding.lastnameField.text?.isEmpty() == true) {
            binding.lastnameFieldLayout.error = resources.getString(R.string.empty_lastname_error)
            valid = false
        }
        if (binding.firstnameField.text?.isEmpty() == true) {
            binding.firstnameFieldLayout.error = resources.getString(R.string.empty_firstname_error)
            valid = false
        }
        if (binding.sexField.text?.isEmpty() == true) {
            binding.sexFieldLayout.error = resources.getString(R.string.empty_sex_error)
            valid = false
        }

        return valid
    }

    private fun clearErrors() {
        binding.emailFieldLayout.error = null
        binding.lastnameFieldLayout.error = null
        binding.firstnameFieldLayout.error = null
        binding.sexFieldLayout.error = null
    }

}