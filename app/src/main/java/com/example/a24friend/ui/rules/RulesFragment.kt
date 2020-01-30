package com.example.a24friend.ui.rules



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.a24friend.databinding.FragmentRulesBinding
import com.example.a24friend.R



/**
 * A simple [Fragment] subclass.
 */
class RulesFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRulesBinding>(inflater,
            R.layout.fragment_rules,container,false)

        binding.rulesPrivacyPolicyMore.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_rulesFragment_to_privacyAgreementFragment)
        }

        binding.rulesNextButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_rulesFragment_to_surveyFragment)
        }


        return binding.root
    }
}

