package com.example.a24friend.ui.privacyAgreement


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.a24friend.databinding.FragmentPrivacyAgreementBinding


import com.example.a24friend.R

/**
 * A simple [Fragment] subclass.
 */
class PrivacyAgreementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPrivacyAgreementBinding>(inflater,
            R.layout.fragment_privacy_agreement,container,false)

        binding.agreeButtonForPrivacy.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_privacyAgreementFragment_to_rulesFragment)
        }

        return binding.root


        return inflater.inflate(R.layout.fragment_privacy_agreement, container, false)
    }


}
