/*
 * Copyright (C) 2014 Peng fei Pan <sky@xiaopan.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.voc.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.xiaopan.voc.ViewOpeningController;

public class UserAdapter extends BaseAdapter{
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.text_userItem_name);
            viewHolder.phoneTextView = (TextView) convertView.findViewById(R.id.text_userItem_phone);
            viewHolder.openStatusImageView = (ImageView) convertView.findViewById(R.id.image_userItem_openStatus);
            viewHolder.openAreaView = convertView.findViewById(R.id.layout_userItem_openArea);
            viewHolder.callButton = convertView.findViewById(R.id.button_userItem_call);
            viewHolder.messageButton = convertView.findViewById(R.id.button_userItem_message);
            viewHolder.editButton = convertView.findViewById(R.id.button_userItem_edit);
            convertView.setTag(viewHolder);

            viewHolder.callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "你以为真能打电话呢，哈哈哈！", Toast.LENGTH_LONG).show();
                }
            });
            viewHolder.messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "逗你玩儿呢，哈哈哈！", Toast.LENGTH_LONG).show();
                }
            });
            viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "编辑个毛，都没数据，哈哈哈！", Toast.LENGTH_LONG).show();
                }
            });

            viewHolder.openingController = new ViewOpeningController(viewHolder.openAreaView) {
                @Override
                public void setViewStatus(boolean opened) {
                    viewHolder.openAreaView.setVisibility(opened?View.VISIBLE:View.GONE);
                    viewHolder.openStatusImageView.setImageResource(opened?R.drawable.ic_arrow_up:R.drawable.ic_arrow_down);
                }
            };
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder viewHolder1 = (ViewHolder) v.getTag();
                    User user = viewHolder1.user;
                    user.setOpened(!user.isOpened());
                    viewHolder1.openingController.opening(user.isOpened());
                }
            });
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        User user = userList.get(position);
        viewHolder.nameTextView.setText(user.getName());
        viewHolder.phoneTextView.setText(user.getPhone());
        viewHolder.openingController.measureViewHeightAndInit(user.isOpened());
        viewHolder.user = user;

        return convertView;
    }

    private static class ViewHolder{
        private TextView nameTextView;
        private TextView phoneTextView;
        private ImageView openStatusImageView;
        private View openAreaView;
        private View callButton;
        private View messageButton;
        private View editButton;
        private ViewOpeningController openingController;
        private User user;
    }
}
