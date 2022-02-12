package com.example.simpledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    EditText etStockIsin, etStockName, etLoadedData;
    String stockFilename = "t.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStockIsin = (EditText) findViewById(R.id.etStockIsin);
        etStockName = (EditText) findViewById(R.id.etStockName);
        etLoadedData = (EditText) findViewById(R.id.etLoadedData);
        Button btnSave = (Button)  findViewById(R.id.btnSpeichern);
        Button btnLoad = (Button)  findViewById(R.id.btnLaden);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseModel dbModel = new DatabaseModel(etStockIsin.getText().toString(), etStockName.getText().toString());
                // https://developer.android.com/reference/java/io/ObjectOutputStream
                FileOutputStream fos = null;
                try {
                    // first check if file stockFilename file exists
                    File dir = getFilesDir();
                    File file = new File(dir, stockFilename);
                    ObjectOutputStream oos;
                    if(file.exists()){
                        fos = openFileOutput(stockFilename, Context.MODE_APPEND);
                        System.out.println("append file mode");
                        oos = new AppendingObjectOutputStream(fos);
                    }else{
                        fos = openFileOutput(stockFilename, Context.MODE_PRIVATE);
                        System.out.println("create new file mode");
                        oos = new ObjectOutputStream(fos);
                    }
                    oos.writeObject(dbModel);
                    System.out.println("Dataset written");
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLoadedData.setText("");
                // https://developer.android.com/reference/java/io/ObjectInputStream
                FileInputStream fis = null;
                DatabaseModel dbModel;
                String datasets = "";
                try {
                    fis = openFileInput(stockFilename);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    boolean cont = true;
                    int round = 0;
                    while (cont) {
                        round++;
                        System.out.println("round " + round);
                        dbModel = (DatabaseModel) ois.readObject();
                        if (dbModel != null) {
                            String dataset = dbModel.getStockIsin() +
                                    " " + dbModel.getStockName();
                            System.out.println("dataset: " + dataset);
                            datasets = datasets + dataset + "\n";
                        } else {
                            cont = false;
                        }
                    }
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String datasets2 = datasets.replaceAll("[\n]$", ""); // remove newline at the end
                System.out.println("datasets2:" + datasets2 + "#");
                etLoadedData.setText(datasets2);

            }
        });
    }
}

