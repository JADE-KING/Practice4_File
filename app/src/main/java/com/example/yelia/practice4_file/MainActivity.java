package com.example.yelia.practice4_file;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /*****Preferences*****

    // 文件名
    private final static String SharedPreferencesFileName="config";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // 键
    private final static String Key_StudentNo = "No";
    private final static String Key_StudentName = "Name";
    private final static String Key_StudentGender = "Gender";
    private final static String Key_StudentBirthday = "Birthday";
    private final static String Key_StudentAge = "Age";
    private final static String Key_SaveTime = "SaveTime";

    //*****Preferences*****/

    // File
    String FILE_NAME = "STUDENT_INFORMATION.TXT";

    String no;
    String name;
    String gender;
    int year;
    int month;
    int day;
    String birthday;
    int age;
    TextView textShowBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*****Preferences*****

        // 获得SharedPreferences实例
        preferences = getSharedPreferences(SharedPreferencesFileName, MODE_PRIVATE);
        editor = preferences.edit();

        //*****Preferences*****/

        // 当前时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        textShowBirthday = (TextView)findViewById(R.id.textShowBirthday);
        textShowBirthday.setText(year + "年" + month + "月" + day + "日");

        // 实现时间选择器
        Button btnSelectDate = (Button)findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, dateListener, year, month - 1, day);
                DatePicker dp = dpd.getDatePicker();
                dp.setMaxDate((new Date()).getTime());
                dpd.show();
            }
        });

        Button btnWrite = (Button)findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream out = null;
                try {
                    FileOutputStream fileOut = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    out = new BufferedOutputStream(fileOut);

                    // 日期
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String saveTime = simpleDateFormat.format(new Date());

                    EditText editNo = (EditText) findViewById(R.id.editNo);
                    no = editNo.getText().toString();
                    EditText editName = (EditText) findViewById(R.id.editName);
                    name = editName.getText().toString();

                    String str = "信息显示："
                            + "\n学号：" + no
                            + "\n姓名：" + name
                            + "\n性别：" + gender
                            + "\n年龄：" + age
                            + "\n出生日期：" + birthday
                            + "\n记录时间：" + saveTime;

                    try {
                        out.write(str.getBytes(Charset.forName("UTF-8")));
                    } finally {
                        if (out != null)
                            out.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*****Preferences*****

                // 写入键值对
                editor.putString(Key_StudentNo, no);
                editor.putString(Key_StudentName, name);
                editor.putString(Key_StudentGender, gender);
                editor.putString(Key_StudentBirthday, birthday);
                editor.putInt(Key_StudentAge, age);
                editor.putString(Key_SaveTime, saveTime);

                // 提交修改
                editor.apply();
                // 或 editor.commit();

                //*****Preferences*****/
            }
        });

        Button btnRead = (Button)findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*****Preferences*****

                String _no = preferences.getString(Key_StudentNo, null);
                String _name = preferences.getString(Key_StudentName, null);
                String _gender = preferences.getString(Key_StudentGender, null);
                String _birthday = preferences.getString(Key_StudentBirthday, null);
                int _age = preferences.getInt(Key_StudentAge, 0);
                String _saveTime = preferences.getString(Key_SaveTime, null);

                TextView textDisplayInformation = (TextView)findViewById(R.id.textDisplayInformation);
                textDisplayInformation.setText("信息显示："
                        + "\n学号：" + _no
                        + "\n姓名：" + _name
                        + "\n性别：" + _gender
                        + "\n年龄：" + _age
                        + "\n出生日期：" + _birthday
                        + "\n记录时间：" + _saveTime);

                //*****Preferences*****/

                // InputStream in = null;
                try {
                    FileInputStream fileIn = openFileInput(FILE_NAME);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));
                    String str;
                    String str_i = null;
                    while ((str = reader.readLine()) != null) {
                        if (str_i == null) str_i = str;
                        else str_i = str_i + '\n' + str;
                    }
                    TextView textDisplayInformation = (TextView)findViewById(R.id.textDisplayInformation);
                    textDisplayInformation.setText(str_i);
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch(view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    gender = "男";
                break;
            case R.id.radioButtonFemale:
                if (checked)
                    gender = "女";
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth) {
            year = _year;
            month = _month + 1;
            day = _dayOfMonth;
            birthday = year + "年" + month + "月" + day + "日";
            Calendar calendar = Calendar.getInstance();
            age = calendar.get(Calendar.YEAR) - year;
            if (month > calendar.get(Calendar.MONTH) + 1) age -=1;
            else if (month == calendar.get(Calendar.MONTH) && day > calendar.get(Calendar.DAY_OF_MONTH)) age -=1;
            if (age > 500) Toast.makeText(MainActivity.this, "您老人家真的还活着么o_O?", Toast.LENGTH_LONG).show();
            else if (age > 100) Toast.makeText(MainActivity.this, "国宝啊！", Toast.LENGTH_LONG).show();
            else if (age > 80) Toast.makeText(MainActivity.this, "您老人家高寿，注意身体啊~", Toast.LENGTH_LONG).show();
            textShowBirthday = (TextView)findViewById(R.id.textShowBirthday);
            textShowBirthday.setText(year + "年" + month + "月" + day + "日 年龄：" + age);
        }
    };

}
