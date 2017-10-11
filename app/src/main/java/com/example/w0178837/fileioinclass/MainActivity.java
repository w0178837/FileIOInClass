package com.example.w0178837.fileioinclass;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    private EditText txtBox;
    private Button btnSave;
    private Button btnLoad;
    // comment
    private static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBox = (EditText) findViewById(R.id.txtBox);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = txtBox.getText().toString();

                try
                {
                    //-- internal storage
                    FileOutputStream fOut = openFileOutput("textfile.txt", MODE_APPEND);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);
                    osw.write(str);
                    osw.flush();
                    osw.close();
                    showToastMessage("File Saved");
                    //showTextInToast();
                    txtBox.setText("");

                    //-- external storage i.e. SD Card
//                    File sdCard = Environment.getExternalStorageDirectory();
//                    File directory = new File(sdCard.getAbsolutePath()+"/MyFileDir");
//                    directory.mkdir();
//                    File file = new File(directory, "textfile.txt");
//                    FileOutputStream fOut = new FileOutputStream(file);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    //-- Internal storage
                    FileInputStream fIn = openFileInput("textfile.txt");
                    InputStreamReader isr = new InputStreamReader(fIn);

                    //-- external storage i.e. SD Card
//                    File sdCard = Environment.getExternalStorageDirectory();
//                    File directory = new File(sdCard.getAbsolutePath()+"/MyFileDir");
//                    File file = new File(directory, "textfile.txt");
//                    FileInputStream fIn = new FileInputStream(file);
//                    InputStreamReader isr = new InputStreamReader(fIn);

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
                    int charRead;

                    while((charRead=isr.read(inputBuffer))>0)
                    {
                        // convert the chars to a string
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readString;
                        inputBuffer = new char[READ_BLOCK_SIZE];
                    }// end while
                    // set the text box to the text that has been read
                    txtBox.setText(s);
                    showToastMessage("File Loaded Successfully");
                }
                catch(IOException e){
                    e.printStackTrace();
                }


            }
        });

    }// end on create
    private void showTextInToast()
    {
        // create an input stream
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        // create new buffered reader and send it the input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String str = null;
        try{
            while ((str = br.readLine()) != null) {
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }// end showTextInToast

    private void showToastMessage(String toastMsg)
    {
        Toast.makeText(getBaseContext(), toastMsg, Toast.LENGTH_LONG).show();
    }
}// end class MainActivity
