package com.example.mcommercemidtermtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import connector.AccountConnector;
import model.Account;

public class LoginActivity extends AppCompatActivity {
    String DATABASE_NAME = "midterm.sqlite";
    EditText edtUserName, edtPassword;
    boolean isAdminChecked = false;
    boolean isEmployeeChecked = false;
    RadioButton chkAdmin, chkEmployee;
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ánh xạ EditText
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        chkAdmin = findViewById(R.id.chk_admin);
        chkEmployee = findViewById(R.id.chk_employee);

        chkAdmin = findViewById(R.id.chk_admin);
        chkEmployee = findViewById(R.id.chk_employee);

        chkAdmin.setOnClickListener(v -> {
            if (isAdminChecked) {
                chkAdmin.setChecked(false);
                isAdminChecked = false;
            } else {
                chkAdmin.setChecked(true);
                chkEmployee.setChecked(false);
                isAdminChecked = true;
                isEmployeeChecked = false;
            }
        });

        chkEmployee.setOnClickListener(v -> {
            if (isEmployeeChecked) {
                chkEmployee.setChecked(false);
                isEmployeeChecked = false;
            } else {
                chkEmployee.setChecked(true);
                chkAdmin.setChecked(false);
                isEmployeeChecked = true;
                isAdminChecked = false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        processCopy();
    }
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;

            myInput = getAssets().open(DATABASE_NAME);


            // Path to the just created empty db
            String outFileName = getDatabasePath();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void do_login(View view) {
        String usr = edtUserName.getText().toString().trim();
        String pwd = edtPassword.getText().toString().trim();

        if (usr.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xác định role người dùng chọn
        int selectedRole = 0;
        if (chkAdmin.isChecked()) {
            selectedRole = 1;
        } else if (chkEmployee.isChecked()) {
            selectedRole = 2;
        } else {
            Toast.makeText(this, "Vui lòng chọn vai trò (Admin hoặc Nhân viên)", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountConnector ac = new AccountConnector();
        Account acc = ac.login(this, usr, pwd);

        if (acc != null) {
            if (acc.getTypeOfAccount() == selectedRole) {
                if (selectedRole == 1) {
                    Toast.makeText(this, "Đăng nhập thành công: ADMIN", Toast.LENGTH_SHORT).show();

                    // Chuyển sang LOTActivity
                    Intent intent = new Intent(LoginActivity.this, LOTActivity.class);
                    intent.putExtra("account", acc); // Truyền đối tượng Account
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Đăng nhập thành công: NHÂN VIÊN", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Bạn không có quyền đăng nhập với vai trò đã chọn!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
        }
    }
    public void do_exit(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        //thiết lập tiêu đề:
        Resources res=getResources();
        builder.setTitle(res.getText(R.string.title_confirm_exit_title));
        builder.setMessage(res.getText(R.string.title_confirm_exit_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        //Thiết lập tương tác người dùng:
        builder.setPositiveButton(res.getText(R.string.title_confirm_exit_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton(res.getText(R.string.title_confirm_exit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
