package com.example.a24friend.ui.chatRoom


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.a24friend.R
import com.example.a24friend.databinding.FragmentChatRoomBinding
import com.example.a24friend.domain.Message
import com.example.a24friend.util.ApiStatus

/**
 * A simple [Fragment] subclass.
 */
class ChatRoomFragment : Fragment() {

    private val viewModel: ChatRoomViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, ChatRoomViewModel.Factory(activity.application))
            .get(ChatRoomViewModel::class.java)
    }

    private var viewModelAdapter: ChatRoomAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.message.observe(viewLifecycleOwner, Observer<List<Message>> { message ->
            message.apply {
                viewModelAdapter?.message = message
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = ChatRoomAdapter()

        binding.messageList.adapter = viewModelAdapter

        setHasOptionsMenu(true)

        return binding.root
    }
}
