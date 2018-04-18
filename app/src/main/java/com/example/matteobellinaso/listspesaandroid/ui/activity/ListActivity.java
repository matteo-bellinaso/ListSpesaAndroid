package com.example.matteobellinaso.listspesaandroid.ui.activity;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matteobellinaso.listspesaandroid.R;
import com.example.matteobellinaso.listspesaandroid.data.db.DatabaseListManager;
import com.example.matteobellinaso.listspesaandroid.logic.Utils;
import com.example.matteobellinaso.listspesaandroid.ui.adapter.MyRecyclerAdapter;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class ListActivity extends AppCompatActivity {

    private ImageButton addImg;

    private static int RESULT_LOAD_IMG = 1;
    public final static String EXTRA_LIST_ID = "listId";


    private String pathImgList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseListManager databaseListManager;
    private Context mContext;
    private static TextView nlist;

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNlist();
        databaseListManager.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.userIcon: {
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("userId", Utils.readId(this));
                startActivity(intent);
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Utils.setNList(this);

        nlist = (TextView) findViewById(R.id.n_list);

        databaseListManager = new DatabaseListManager(this);
        databaseListManager.open();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton buttons = (FloatingActionButton) findViewById(R.id.floating_button);
        setNlist();

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListDialog();
            }
        });
        databaseListManager.close();
    }


    public void addListDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        dialogBuilder.setView(dialogView);
        final EditText edit = (EditText) dialogView.findViewById(R.id.edit_add_list);
        addImg = (ImageButton) dialogView.findViewById(R.id.add_list_img);
        dialogBuilder.setTitle(R.string.add_name_list);


        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        dialogBuilder.setPositiveButton(R.string.alert_confim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                databaseListManager.createList(edit.getText().toString(), pathImgList, Utils.readId(ListActivity.this));
                mAdapter = new MyRecyclerAdapter(ListActivity.this);

                Utils.setNList(getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
                pathImgList = null;

                Cursor idCursor = databaseListManager.fetchLastList(Utils.readId(ListActivity.this));
                idCursor.moveToFirst();
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_LIST_ID, idCursor.getInt(idCursor.getColumnIndex("_id")));
                intent.putExtra("listName", idCursor.getString(idCursor.getColumnIndex(databaseListManager.KEY_LIST_NAME)));
                startActivity(intent);

            }
        });

        dialogBuilder.setNegativeButton(R.string.alert_undo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setNlist();
        databaseListManager.close();
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                addImg.setImageBitmap(selectedImage);

                pathImgList = getStringFromUri(imageUri);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


    public String getStringFromUri(Uri uri) {
        String stringUri;
        stringUri = uri.toString();
        return stringUri;
    }

    public static void setNlist(){
        nlist.setText("Elenco liste (" + Utils.getNLIST() + ")" );
    }

}

