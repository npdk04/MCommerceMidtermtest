package com.example.mcommercemidtermtest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.LOTAdapter;
import connector.LOTConnector;
import model.Account;
import model.LOT;

public class LOTActivity extends AppCompatActivity {

    ListView listView;
    Button btnCreate;
    LOTConnector connector;
    ArrayList<LOT> taskList;
    LOTAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotactivity);

        // Ánh xạ
        listView = findViewById(R.id.list_view_tasks);
        btnCreate = findViewById(R.id.btn_create);

        // Lấy tài khoản đang đăng nhập
        Account currentAccount = (Account) getIntent().getSerializableExtra("account"); // cần truyền từ LoginActivity

        if (currentAccount == null) {
            Toast.makeText(this, "Không có tài khoản đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load dữ liệu
        connector = new LOTConnector();
        if (currentAccount.getTypeOfAccount() == 1) {
            taskList = connector.getAllTasks(this);
        } else {
            // Nếu là nhân viên → lấy theo AccountID
            taskList = connector.getTasksByAccountId(this, currentAccount.getId());
        }

        adapter = new LOTAdapter(this, R.layout.item_list_of_task, taskList);
        listView.setAdapter(adapter);

        // Tạo nhiệm vụ mới (chưa xử lý logic, placeholder)
        btnCreate.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng tạo nhiệm vụ chưa được triển khai.", Toast.LENGTH_SHORT).show();
        });
    }
}
