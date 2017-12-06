/*
 * Copyright (C) 2017 Peng fei Pan <sky@panpf.me>
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

package me.panpf.viewep.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        listView = (ListView) findViewById(R.id.list_main);

        List<User> userList = new ArrayList<User>(33);
        for(int w = 0; w < 10; w++){
            User user = new User();
            user.setName("张三"+(w+1)+"号");
            user.setPhone("13897653468");
            userList.add(user);
        }
        for(int w = 0; w < 10; w++){
            User user = new User();
            user.setName("李四"+(w+1)+"号");
            user.setPhone("18600974625");
            userList.add(user);
        }
        for(int w = 0; w < 10; w++){
            User user = new User();
            user.setName("王五"+(w+1)+"号");
            user.setPhone("13301236543");
            userList.add(user);
        }
        for(int w = 0; w < 3; w++){
            User user = new User();
            user.setName("铁锤子"+(w+1)+"号");
            user.setPhone("18901895679");
            userList.add(user);
        }
        listView.setAdapter(new UserAdapter(getBaseContext(), userList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(getBaseContext(), AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
