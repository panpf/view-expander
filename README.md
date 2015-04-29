#ViewOpeningController

ViewOpeningController是Android上一个快速实现View展开关闭效果的库，使用非常简单

Android ViewOpeningController is the last fast implementation of View started off effect of the library, very easy to use

![sample.gif](https://github.com/xiaopansky/ViewOpeningController/raw/master/releases/sample.gif)

## Sample App
[Download it on Github](https://github.com/xiaopansky/ViewOpeningController/raw/master/releases/view-opening-controller-1.0.0.apk)

![download.jpg](https://github.com/xiaopansky/ViewOpeningController/raw/master/releases/download.jpg)

##Usage Guide
###Step1 导入
####Using Eclipse
点击下载 [view-opening-controller-1.0.0.jar](https://github.com/xiaopansky/ViewOpeningController/raw/master/releases/view-opening-controller-1.0.0.jar) ，下载后放到libs目录下即可

####Using Android Studio
点击下载 [view-opening-controller-1.0.0.aar](https://github.com/xiaopansky/ViewOpeningController/raw/master/releases/view-opening-controller-1.0.0.aar) ，下载后放到libs目录下，然后在build.gradle中添加以下代码
```java
repositories{
    flatDir(){
        dirs 'libs'
    }
}

dependencies{
	compile(name:'view-opening-controller-1.0.0', ext:'aar')
}
```
最后同步一下Gradle即可

其实ViewOpeningController只有一个class文件，所以你也可以直接下载jar文件放到libs目录下，然后同步Gradle即可

###Step2 使用
在Adapter中使用
在数据对象User中保存展开状态
```java
public class User {
    ...
    private boolean opened;
	...
    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
```

ViewHoler
```java
private static class ViewHolder{
    ...
    private ImageView openStatusImageView;
    private View openAreaView;
    private ViewOpeningController openingController;
    private User user;
}
```

在getView方法中完成初始化ViewOpeningController以及相应的事件设置
```java
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null){
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
        final ViewHolder viewHolder = new ViewHolder();
        ...
        viewHolder.openStatusImageView = (ImageView) convertView.findViewById(R.id.image_userItem_openStatus);
        viewHolder.openAreaView = convertView.findViewById(R.id.layout_userItem_openArea);
        convertView.setTag(viewHolder);

// 创建ViewOpeningController，并制定要展开关闭的View，你还需要实现setViewStatus(boolean)方法，根据opened参数修改你要展开关闭的View
        viewHolder.openingController = new ViewOpeningController(viewHolder.openAreaView) {
            @Override
            public void setViewStatus(boolean opened) {
                viewHolder.openAreaView.setVisibility(opened?View.VISIBLE:View.GONE);
                viewHolder.openStatusImageView.setImageResource(opened?R.drawable.ic_arrow_up:R.drawable.ic_arrow_down);
            }
        };
// 监听convertView的点击事件，调用openingController.opening(boolean)方法展开或关闭
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
    ...
    
// 重置openingController的状态
viewHolder.openingController.measureViewHeightAndInit(user.isOpened());
    viewHolder.user = user;

    return convertView;
}
```

注意：
>* 展开区域的布局跟其它区域之间的间隔不能用margin实现，因为这样在切换的过程中会有明显的卡顿
>* 展开区域内的子View最好是贴着底部的，例如使用android:layout_gravity="bottom"属性，并且子View的高度必须是写死的，这样在变化的过程中会比较自然

详情请clone源码，查看示例

##License
```java
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
```
