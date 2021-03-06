package com.example.sung.dementiacare.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sung.dementiacare.R;
import com.example.sung.dementiacare.notification.alarm.AlarmDo;

import java.util.ArrayList;

/**
 * Created by Minwoo on 2017. 9. 24..
 */


public class NotificationChoiceListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    private ArrayList<AlarmDo> alarmDo = new ArrayList<AlarmDo>();
    public Boolean hideCheckBox = false;

    // ListViewAdapter의 생성자
    public NotificationChoiceListViewAdapter() {


    }

    public NotificationChoiceListViewAdapter(ArrayList<AlarmDo> alarmDo) {
        this.alarmDo = alarmDo;

        for(AlarmDo alarmDo1 : alarmDo) {
            this.addItem(alarmDo1);

        }

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_notification_listview_item_layout, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.tv_time);
        TextView repeatTextView = (TextView) convertView.findViewById(R.id.tv_repeat);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);


        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        if(hideCheckBox)checkBox.setVisibility(View.GONE);
        listViewItem.setCheckBox(checkBox);
//        listViewItem.setChecked(true);
        // 아이템 내 각 위젯에 데이터 반영
        textTextView.setText(listViewItem.getText());
        timeTextView.setText(listViewItem.getAlarmDo().getHour()+":"+listViewItem.getAlarmDo().getMinutes());

        boolean[] bits = new boolean[7];
        String[] day = {"월","화","수","목","금","토","일"};
        String selectDay = "";
        for (int i = 6; i >= 0; i--) {
            bits[i] = (listViewItem.getAlarmDo().getRepeat() & (1 << i)) != 0;
        }

        for(int i = 0; i < bits.length; i++){
            if(bits[i]) selectDay += (day[i]+" ");
        }

        repeatTextView.setText(selectDay);
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }


    public ArrayList<ListViewItem> getListViewItemList() {
        return listViewItemList;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(AlarmDo alarmDo) {
        ListViewItem item = new ListViewItem();
        item.setText(alarmDo.getName());
        item.setAlarmDo(alarmDo);
        listViewItemList.add(item);
    }

    public void hideCheckBox(){

    }
}

