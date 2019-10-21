package com.gebeya.chatme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<role> mRole;
    private LayoutInflater mInflater;
    public String imageurl;
    TextView show_message;

    public MessageAdapter(Context context, List<role> mRole) {
        mContext = context;
        this.mRole = mRole;
        this.imageurl = imageurl;

        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View itemView = mInflater.inflate(R.layout.chat_item_bot, viewGroup, false);

        return new ViewHolder(itemView);

/*        if (mRole.get(position).isMe()) {
            View itemView = mInflater.inflate(R.layout.chat_item_bot, viewGroup, false);
            return new MessageAdapter.ViewHolder(itemView);
        }
        else{
            View itemView = mInflater.inflate(R.layout.chat_item_user, viewGroup, false);
            return new MessageAdapter.ViewHolder(itemView);
        }*/

    }
        @Override
        public void onBindViewHolder (@NonNull MessageAdapter.ViewHolder holder,int position){

            role role = mRole.get(position);
            if (role.isMe()) {
                holder.profile_image_user.setVisibility(View.VISIBLE);
                holder.show_message_bot.setText(mRole.get(position).getMessage());

                // load the color
                int color = ContextCompat.getColor(mContext, android.R.color.holo_green_dark);
                holder.show_message_bot.setBackgroundColor(color);
                holder.profile_left_container.setVisibility(View.VISIBLE);

                // Hide the bot image and space
                // Show my image and space
            } else {

                holder.profile_image_user.setImageResource(View.VISIBLE);
                holder.show_message_bot.setText(mRole.get(position).getMessage());
                holder.profile_right_container.setVisibility(View.VISIBLE);
                // Hide my image and space
                // Show the bot and image and space
            }

//            holder.show_message_user .setText(mRole.get(position).getMessage());

        }




        @Override
        public int getItemCount () {
            return mRole.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView show_message_user,show_message_bot;
            public ImageView profile_image_bot,profile_image_user;
            public  View profile_right_container;
            public View profile_left_container;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                show_message_user= itemView.findViewById(R.id.show_message_user);
                show_message_bot= itemView.findViewById(R.id.show_message_bot);
                profile_image_bot = itemView.findViewById(R.id.profile_image_bot);
                profile_image_user=itemView.findViewById(R.id.profile_image_user);
                profile_right_container=itemView.findViewById(R.id.profile_right_container);
                profile_left_container=itemView.findViewById(R.id.profile_left_container);
            }
        }
    }
