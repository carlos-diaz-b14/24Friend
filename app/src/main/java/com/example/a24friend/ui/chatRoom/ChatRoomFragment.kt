package com.example.a24friend.ui.chatRoom


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.a24friend.databinding.FragmentChatRoomBinding
import com.example.a24friend.domain.Message
import com.example.a24friend.ui.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class ChatRoomFragment : Fragment() {

    private val viewModel: ChatRoomViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, ChatRoomViewModel.Factory(
            activity.application,(requireNotNull(this.activity) as MainActivity).mUserId))
            .get(ChatRoomViewModel::class.java)
    }

    private var viewModelAdapter: ChatRoomAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.messages.observe(viewLifecycleOwner, Observer<List<Message>> { message ->
            message.apply {
                viewModelAdapter?.messages = message
                viewModelAdapter?.addMessageList()
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