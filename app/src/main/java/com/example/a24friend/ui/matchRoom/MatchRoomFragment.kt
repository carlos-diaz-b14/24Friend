package com.example.a24friend.ui.matchRoom


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.a24friend.R
import com.example.a24friend.databinding.FragmentChatRoomBinding
import com.example.a24friend.databinding.FragmentMatchRoomBinding
import com.example.a24friend.ui.chatRoom.ChatRoomViewModel

/**
 * A simple [Fragment] subclass.
 */
class MatchRoomFragment : Fragment() {

    private val viewModel: MatchRoomViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MatchRoomViewModel.Factory(activity.application))
            .get(MatchRoomViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchRoomBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }


}
