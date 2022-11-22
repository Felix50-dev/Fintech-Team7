package com.aad.fintech.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aad.fintech.R
import com.aad.fintech.databinding.FragmentPinBinding
import com.aad.fintech.ui.views.activities.HomeActivity

class PinFragment : Fragment() {

    private var _binding: FragmentPinBinding? = null
    private val binding get() = _binding!!

    private val args: PinFragmentArgs by navArgs()

    private var pin: String = ""

    companion object {
        @JvmStatic
        fun newInstance() = PinFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPinBinding.inflate(inflater, container, false)
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
        toolbar.setTitle(R.string.secure_with_pin)
    }

    private fun initViews() {
        initPinViews()
        when(args.pinMode) {
            0 -> setPinModeView(getString(R.string.enter_pin), false)
            1 -> setPinModeView(getString(R.string.define_pin), true)
            2 -> setPinModeView(getString(R.string.confirm_pin), true)
        }
    }

    private fun setPinModeView(pinMessage: String, withToolbar: Boolean) {
        binding.pinMessage.text = pinMessage
        if (withToolbar)
            binding.toolbar.toolbar.visibility = View.VISIBLE
        else
            binding.toolbar.toolbar.visibility = View.GONE
    }

    private fun initPinViews() {
        setPinView(binding.pin1, false)
        setPinView(binding.pin2, false)
        setPinView(binding.pin3, false)
        setPinView(binding.pin4, false)
        pin = ""
    }

    private fun setPinView(pin: ImageView, value: Boolean) {
        if (value) pin.setImageResource(R.drawable.filled_pin_box)
        else pin.setImageResource(R.drawable.outline_pin_box)
    }

    private fun setEvents() {
        binding.button1.setOnClickListener { setPin( binding.button1.text.toString()) }
        binding.button2.setOnClickListener { setPin( binding.button2.text.toString()) }
        binding.button3.setOnClickListener { setPin( binding.button3.text.toString()) }
        binding.button4.setOnClickListener { setPin( binding.button4.text.toString()) }
        binding.button5.setOnClickListener { setPin( binding.button5.text.toString()) }
        binding.button6.setOnClickListener { setPin( binding.button6.text.toString()) }
        binding.button7.setOnClickListener { setPin( binding.button7.text.toString()) }
        binding.button8.setOnClickListener { setPin( binding.button8.text.toString()) }
        binding.button9.setOnClickListener { setPin( binding.button9.text.toString()) }
        binding.button0.setOnClickListener { setPin( binding.button0.text.toString()) }
        binding.clearButton.setOnClickListener { initPinViews() }
    }

    private fun setPin(toAppend: String) {
        if (pin.length < 4) {
            pin += toAppend
            updatePinView()
        }
    }

    private fun updatePinView() {
        when (pin.length) {
            1 -> setPinView(binding.pin1, true)
            2 -> setPinView(binding.pin2, true)
            3 -> setPinView(binding.pin3, true)
            4 -> {
                setPinView(binding.pin4, true)
                completePin()
            }
        }
    }

    private fun completePin() {
        if (validatePin()) completeValidPin()
        else completeInvalidPin()
    }

    private fun validatePin(): Boolean {
        return if (args.pinMode != 1)
            pin == "0000"
        else
            true
    }

    private fun completeValidPin() {
        when(args.pinMode) {
            0 -> return
            1 -> {
                val action = PinFragmentDirections.actionPinFragmentSelf(2)
                findNavController().navigate(action)
            }
            2 -> {
                startHomeActivity()
            }
        }
    }

    private fun startHomeActivity() {
        var next = Intent(context, HomeActivity::class.java)
        startActivity(next)
    }

    private fun completeInvalidPin() {
        initPinViews()
        Toast.makeText(context, "Wrong PIN code!", Toast.LENGTH_LONG).show()
    }
}