package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.MessageBoard;
import com.yetland.myfriend.model.MessageModel;
import com.yetland.myfriend.model.UserModel;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yeliang on 2016/4/27.
 */
public class MessageBoardAdapter extends RecyclerView.Adapter<MessageBoardAdapter.MessageBoardHolder> {



    private List<UserModel> fromUsers;
    private List<MessageBoard> messageBoards;
    private MessageModel messageModel;
    private OnItemClickListener onItemClickListener;
    private OnReplyClickListener onReplyClickListener;

    public MessageBoardAdapter(MessageModel messageModel) {
        this.messageModel = messageModel;
        fromUsers = this.messageModel.getFromUsers();
        messageBoards = this.messageModel.getMessageBoard();
        Collections.reverse(messageBoards);
        Collections.reverse(fromUsers);
    }

    @Override
    public MessageBoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_board, parent, false);

        return new MessageBoardHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(MessageBoardHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return messageBoards.size();
    }

    public void setOnReplyClickListener(OnReplyClickListener onReplyClickListener) {
        this.onReplyClickListener = onReplyClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnReplyClickListener {
        void onReplyClick(int position);
    }

    class MessageBoardHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bt_reply)
        TextView btReply;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.tv_content_message)
        TextView tvContentMessage;
        @Bind(R.id.time)
        TextView time;

        int position;

        public MessageBoardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            this.position = position;
            MessageBoard messageBoard = messageBoards.get(position);
            UserModel fromUser = fromUsers.get(position);

            nick.setText(fromUser.getNick());
            time.setText(messageBoard.getTime());
            tvContentMessage.setText(messageBoard.getContentMessage());
        }

        @OnClick(R.id.bt_reply)
        public void onClick() {
            onReplyClickListener.onReplyClick(position);
        }
    }
}
