package com.keecoding.autoreloadapp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.keecoding.autoreloadapp.databinding.InputWebBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class SettingsFragment: DialogFragment(R.layout.input_web) {
    private lateinit var mainActivity: MainActivity
    private lateinit var b: InputWebBinding
    private var ctrReload = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = InputWebBinding.inflate(inflater, container, false)
        return b.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        ctrReload = SharedPref.reload
        b.ctrReload.text = SharedPref.reload.toString()
        b.etUrlWeb.setText(SharedPref.url)
        if (SharedPref.periose == -1) {
            b.cbUnlimited.isChecked = true
        } else {
            b.etPeriode.setText(SharedPref.periose.toString())
        }

        b.cbUnlimited.setOnCheckedChangeListener { compoundButton, bo ->
            b.etPeriode.isActivated = !bo
        }

        b.btnReloadPlus.setOnClickListener {
            if (ctrReload in 1..9) {
                ctrReload++
                b.ctrReload.text = ctrReload.toString()
            }
        }

        b.btnReloadMinus.setOnClickListener {
            if (ctrReload in 2..10) {
                ctrReload--
                b.ctrReload.text = ctrReload.toString()
            }
        }

        b.btnSimpan.setOnClickListener {
            if (!Patterns.WEB_URL.matcher(b.etUrlWeb.text.toString()).matches()) {
                Toast.makeText(mainActivity, "Web URL Tidak Valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            MainActivity.WEB_URL = b.etUrlWeb.text.toString()
            MainActivity.PERIOD = if (!b.cbUnlimited.isChecked) b.etPeriode.text.toString().toInt() else -1
            MainActivity.RELOAD = b.ctrReload.text.toString().toInt()
            mainActivity.callback()

            SharedPref.url = b.etUrlWeb.text.toString()
            SharedPref.reload = ctrReload
            if (b.cbUnlimited.isChecked) {
                SharedPref.periose =  -1
            } else {
                SharedPref.periose = b.etPeriode.text.toString().toInt()
            }
            SharedPref.reload = ctrReload
            dismiss()
        }

        b.btnBatal.setOnClickListener { dismiss() }
    }
}