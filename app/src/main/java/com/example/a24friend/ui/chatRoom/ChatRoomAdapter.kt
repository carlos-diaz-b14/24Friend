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
import com.example.a24friend.database.getDatabase
import com.example.a24friend.databinding.ItemIncomingMessageBinding
import com.example.a24friend.domain.Message
import com.example.a24friend.domain.ChatRoom
import com.example.a24friend.repository.ChatRoomRepository
import kotlinx.android.synthetic.main.item_outcoming_message.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ITEM_VIEW_TYPE_IN = 0
const val ITEM_VIEW_TYPE_OUT = 1

class ChatRoomAdapter :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    var messages: List<Message> = emptyList()

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val withDataBinding: ItemIncomingMessageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            InMessageViewHolder.LAYOUT,
            parent,

            false
        )
        return when (viewType) {
            ITEM_VIEW_TYPE_IN -> InMessageViewHolder(withDataBinding)
            ITEM_VIEW_TYPE_OUT -> OutMessageViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InMessageViewHolder -> {
                (holder as InMessageViewHolder).holderDataBinding.apply {
                    message = (getItem(position) as DataItem.InMessageItem).message
                    executePendingBindings()
                }
            }
            is OutMessageViewHolder -> {
                holder.bind("テキスト", "時間")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.InMessageItem -> ITEM_VIEW_TYPE_IN
            is DataItem.OutMessageItem -> ITEM_VIEW_TYPE_OUT
        }
    }

    class InMessageViewHolder(val holderDataBinding: ItemIncomingMessageBinding) :
        RecyclerView.ViewHolder(holderDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_incoming_message
        }
    }

    class OutMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: String, time: String) {
            itemView.outcomming_text_message.text = message
            itemView.outcomming_message_time.text = time
        }

        companion object {
            fun from(parent: ViewGroup): OutMessageViewHolder {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_outcoming_message, parent, false)
                return OutMessageViewHolder(view)
            }
        }
    }

    fun addMessageList() {
        adapterScope.launch {
            val items = ArrayList<DataItem>()
            if (messages.isNotEmpty()) {
                for (item in messages) {
                    val userDocId = item.userDocId
                    if (userDocId == userDocId) {
                        items.add(DataItem.InMessageItem(item))
                    } else {
                        items.add(DataItem.OutMessageItem(item))
                    }
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}
class MessageDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
        oldItem == newItem
}


sealed class DataItem {
    abstract val id: String

    data class InMessageItem(val message: Message) : DataItem() {
        override val id: String
            get() = message.id
    }

    data class OutMessageItem(val message: Message) : DataItem() {
        override val id: String
            get() = message.id
    }
}
