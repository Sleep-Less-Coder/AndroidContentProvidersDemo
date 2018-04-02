package com.example.hemant.contentproviderk2it;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.addressEditText) EditText addressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.submitBtn)
    public void addUser() {
        ContentValues values = new ContentValues();

        values.put(UsersProvider.NAME, nameEditText.getText().toString());
        values.put(UsersProvider.ADDRESS, addressEditText.getText().toString());

        Uri uri = getContentResolver().insert(UsersProvider.CONTENT_URI, values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.viewUsersBtn)
    public void viewusers() {
        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, null, null, null, null);

        StringBuilder stringBuilder = new StringBuilder();

        if(c.moveToFirst()) {
            do {
                int nameIndex = c.getColumnIndex(UsersProvider.NAME);
                int addressIndex = c.getColumnIndex(UsersProvider.ADDRESS);

                stringBuilder.append(c.getString(nameIndex) + " : " + c.getString(addressIndex) + "\n");

            } while (c.moveToNext());
        }

        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.getAddressBtn)
    public void getAddress() {
        String[] proj = {UsersProvider.ADDRESS};
        String[] args = {nameEditText.getText().toString()};

        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, proj, UsersProvider.NAME + "= ?", args, null);

        StringBuilder stringBuilder = new StringBuilder();

        if(c.moveToFirst()) {
            do {
                int addressIndex = c.getColumnIndex(UsersProvider.ADDRESS);

                stringBuilder.append(c.getString(addressIndex) + "\n");

            } while (c.moveToNext());
        }

        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.getUserIdBtn)
    public void getUserId() {
        String[] proj = {UsersProvider.UID};
        String[] args = {nameEditText.getText().toString(), addressEditText.getText().toString()};

        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, proj, UsersProvider.NAME + "= ? AND " + UsersProvider.ADDRESS + " = ?", args, null);

        StringBuilder stringBuilder = new StringBuilder();

        if(c.moveToFirst()) {
            do {
                int userIndex = c.getColumnIndex(UsersProvider.UID);

                stringBuilder.append(c.getInt(userIndex) + "\n");

            } while (c.moveToNext());
        }

        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.updateNameBtn)
    public void updateName() {
        String currentName = nameEditText.getText().toString();
        String newName = addressEditText.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersProvider.NAME, newName);

        String[] whereArgs = {currentName};

        int count = getContentResolver().update(UsersProvider.CONTENT_URI, contentValues, UsersProvider.NAME + " = ?", whereArgs);

        Toast.makeText(this, count + " Updated!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.updateAddressBtn)
    public void updateAddress() {
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersProvider.ADDRESS, address);

        String[] whereArgs = {name};

        int count = getContentResolver().update(UsersProvider.CONTENT_URI, contentValues, UsersProvider.NAME + " = ?", whereArgs);

        Toast.makeText(this, count + " Updated!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.deleteBtn)
    public void deleteUser() {
        String name = nameEditText.getText().toString();

        String[] whereArgs = {name};

        int count = getContentResolver().delete(UsersProvider.CONTENT_URI, UsersProvider.NAME + " = ?", whereArgs);

        Toast.makeText(this, count + " Deleted!", Toast.LENGTH_SHORT).show();
    }
}
