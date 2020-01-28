package com.example.a24friend.ui.chatRoom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a24friend.R
import com.example.a24friend.databinding.ItemIncomingMessageBinding
import com.example.a24friend.domain.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ChatRoomAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(ContactDiffCallback()) {

    var message: List<Message> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val withDataBinding: ItemIncomingMessageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MessageViewHolder.LAYOUT,
            parent,
            false
        )
        return MessageViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MessageViewHolder).holderDataBinding.apply {
            message = getItem(position)
            executePendingBindings()
        }
    }

    class MessageViewHolder(val holderDataBinding: ItemIncomingMessageBinding) : RecyclerView.ViewHolder(holderDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_incoming_message
        }
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean = oldItem == newItem
}
