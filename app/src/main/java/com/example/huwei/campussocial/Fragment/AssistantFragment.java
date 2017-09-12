package com.example.huwei.campussocial.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.huwei.campussocial.R;
import com.example.huwei.campussocial.Util.AlarmReceive;

import java.util.Calendar;

/**
 * Created by HUWEI on 2017/5/2.
 */

public class AssistantFragment extends Fragment {
    private EditText et;
    private Button btn;
    private TextView tv;
    private int count;
    private StringBuilder sb;
    private String msg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assistant,container,false);
        sb = new StringBuilder();
        et = (EditText) view.findViewById(R.id.et);
        btn = (Button)view.findViewById(R.id.btn);
        tv = (TextView) view.findViewById(R.id.tv);
        //得到本地缓存的备忘录内容
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String memo = prefs.getString("memo",null);
        tv.setText(memo);
        Bundle bundle =getActivity().getIntent().getExtras();
        if(bundle != null){
            new AlertDialog.Builder(getContext())
                    .setIcon(null)
                    .setTitle("温馨提示")
                    .setMessage("备忘录时间到了,请注意")
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
        }
        InitViews();
        return view;
    }
    //设定备忘录提醒时间
    private void InitViews() {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());//设置当前时间为c的时间
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                msg = et.getText().toString().trim();
                sb.append(count++);
                sb.append("备忘录的内容为:");
                sb.append(msg);
                sb.append("\n\n");
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putString("memo",sb.toString());
                editor.apply();
                tv.setText(sb.toString().trim());
                new TimePickerDialog(
                        getContext(),
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(getContext(),AlarmReceive.class);
                                PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                                am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                String tempHour = (hour+"").length()>1?hour+"":"0"+hour;
                                String tempMinute = (minute+"").length()>1?minute+"":"0"+minute;
                                Toast.makeText(getContext(), "设置时间为:"+tempHour+":"+tempMinute, Toast.LENGTH_SHORT).show();
                            }
                        },
                        hour,
                        minute,
                        true).show();
            }
        });
    }
}
