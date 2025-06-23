package connector;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.LOT;

public class LOTConnector {
    private final String DATABASE_NAME = "midterm.sqlite";

    // Lấy nhiệm vụ theo AccountID (cho nhân viên)
    public ArrayList<LOT> getTasksByAccountId(Activity context, int accountId) {
        ArrayList<LOT> list = new ArrayList<>();
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery(
                "SELECT * FROM TaskForTeleSales WHERE AccountID = ?",
                new String[]{String.valueOf(accountId)}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int accId = cursor.getInt(1);
                String title = cursor.getString(2);
                String date = cursor.getString(3);
                int isCompleted = cursor.getInt(4);

                LOT task = new LOT(id, accId, title, date, isCompleted);
                list.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // Lấy tất cả nhiệm vụ (cho admin)
    public ArrayList<LOT> getAllTasks(Activity context) {
        ArrayList<LOT> list = new ArrayList<>();
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM TaskForTeleSales", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int accId = cursor.getInt(1);
                String title = cursor.getString(2);
                String date = cursor.getString(3);
                int isCompleted = cursor.getInt(4);

                LOT task = new LOT(id, accId, title, date, isCompleted);
                list.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}
