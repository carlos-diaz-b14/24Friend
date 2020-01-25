package com.example.a24friend.ui.chatRoom


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.a24friend.R

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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }


}
