package com.example.a24friend.ui.rules



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.a24friend.databinding.FragmentRulesBinding
import com.example.a24friend.R



/**
 * A simple [Fragment] subclass.
 */
class RulesFragment : Fragment() {
    private val viewModel = RulesViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRulesBinding>(inflater,
            R.layout.fragment_rules,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rulesPrivacyPolicyMore.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_rulesFragment_to_privacyAgreementFragment)
        }
        viewModel.navigateToSurvey.observe(this, Observer {
            if (it) {
                this.findNavController().navigate(R.id.action_rulesFragment_to_surveyFragment)
                viewModel.onSurveyNavigated()
            }
        })

        binding.rulesAcceptCheckBox.isChecked = false
        binding.rulesNextButton.isEnabled = false
        binding.rulesAcceptCheckBox.setOnClickListener {
            val isChecked = binding.rulesAcceptCheckBox.isChecked
            binding.rulesNextButton.isEnabled = isChecked
        }


        return binding.root
    }

}

