package com.example.databasepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //xml 객체 선언
    private EditText name, age;
    private Button pushBtn, deleteBtn, deleteAllBtn, findBtn;
    private TextView resultData;

    //데이터베이스 선언
    private DB_helper db_helper;

    //입력 값 받기 위한 객체
    private String nameData;
    private int ageData;

    //나이 정렬을 위한 boolean
    boolean sortDirection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //객체 연결
        name = findViewById(R.id.edit_name);
        age = findViewById(R.id.edit_age);
        pushBtn = findViewById(R.id.pushdata);
        deleteBtn = findViewById(R.id.deletedata);
        deleteAllBtn = findViewById(R.id.deletealldata);
        findBtn = findViewById(R.id.finddata);
        resultData = findViewById(R.id.textdata);

        //데이터베이스 생성
        db_helper = new DB_helper(this);
        db_helper.open();
        db_helper.create();

        //데이터 입력 버튼을 누르면
        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditTEst로부터 값을 입력받는다.
                nameData = name.getText().toString();
                ageData = Integer.parseInt(age.getText().toString());

                //데이터 베이스에 값 넣는다.
                db_helper.insertColum(nameData, ageData);

                //데이터베이스 보기
                showDataBase(null);
            }
        });

        //데이터 삭제(이름)를 누르면
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameData = name.getText().toString();
                db_helper.deleteColumn(nameData);

                //데이터베이스 보기
                showDataBase(null);
            }
        });

        //데이터 전체 삭제를 누르면
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper.deleteAllColumns();

                //데이터베이스 보기
                showDataBase(null);
            }
        });

        //특정 데이터 찾기(이름 또는 나이 입력)
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //값을 입력받는다.
                nameData = name.getText().toString();

                //공백이 아니면
                if(nameData != null) {
                    db_helper.selectNameColumns(nameData);
                    showDataBase(nameData);
                }
            }
        });
}

    //데이터들을 읽어온다.
    public void showDataBase(String name){
        Cursor cursor = db_helper.selectColumns();

        //받은 데이터를 배열에 쌓음
        List<String> readDataList = new ArrayList();
        String readData, allData = null;

        while (cursor.moveToNext()){
            String readName = cursor.getString(cursor.getColumnIndex("name"));
            String readAge = cursor.getString(cursor.getColumnIndex("age"));
            readData = "\n이름 : "+readName + " / 나이 : " + readAge ;

            if(name!= null && readName.equals(name)){
                readDataList.add(readData);
            }

            if(name == null){
                readDataList.add(readData);
            }
        }

        //받은 데이터를 하나의 string으로 쌓아 줌
        for(int i=0; i<readDataList.size(); i++){
            allData+=readDataList.get(i);
        }

        if(readDataList.size() != 0) {
            resultData.setText(allData.replace("null", ""));
        }
        else {
            resultData.setText("데이터 없음");
        }
    }
}