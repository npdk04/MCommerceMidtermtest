package connector;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Account;

public class AccountConnector {
    private final String DATABASE_NAME = "midterm.sqlite";

    public Account login(Activity context, String usr, String pwd) {
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM Account WHERE Username=? AND Password=?", new String[]{usr, pwd});
        Account acc = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            int type = cursor.getInt(3);

            acc = new Account(id, username, password, type);
        }

        cursor.close();
        return acc;
    }
}
