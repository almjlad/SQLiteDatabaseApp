package com.wordpress.bndralmjlad.sqliteapp;

/*
 * date 01-09-2021
 * bndr.almjlad@gmail.com
 */

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks ,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;

    Button btnViewUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editText_name);
        editSurname = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_Marks);
        editTextId = findViewById(R.id.editText_id);
        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_viewAll);
        btnViewUpdate = findViewById(R.id.button_update);
        btnDelete= findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                v -> {
                    Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                    if(deletedRows > 0)
                        Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                }
        );
    }
    public void UpdateData() {
        btnViewUpdate.setOnClickListener(
                v -> {
                    boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                            editName.getText().toString(),
                            editSurname.getText().toString(),editMarks.getText().toString());
                    if(isUpdate)
                        Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                v -> {
                    boolean isInserted = myDb.insertData(editName.getText().toString(),
                            editSurname.getText().toString(),
                            editMarks.getText().toString() );
                    if(isInserted)
                        Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                v -> {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0) {
                        // show message
                        showMessage("Error","Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id :"+ res.getString(0)+"\n");
                        buffer.append("Name :"+ res.getString(1)+"\n");
                        buffer.append("Surname :"+ res.getString(2)+"\n");
                        buffer.append("Marks :"+ res.getString(3)+"\n\n");
                    }

                    // Show all data
                    showMessage("Data",buffer.toString());
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}