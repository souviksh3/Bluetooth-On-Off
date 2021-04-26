package com.example.demo_bluethooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SwitchButton mSwitchbtn;

    ListView mListView;

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchbtn = findViewById(R.id.btn_on_off);

        mListView=findViewById(R.id.Paired_list);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mSwitchbtn.setChecked(false);

        mSwitchbtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if(isChecked)
                {
                    onBluetooth();
                }
                else
                {
                    offBluetooth();
                }

            }
        });

    }

    private void onBluetooth() {

        if(!mBluetoothAdapter.isEnabled()){

            Intent turnon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(turnon,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            getPairedDevicesList();

        }
        else
        {
            Toast.makeText(MainActivity.this,"Bluetooth is off",Toast.LENGTH_LONG).show();
        }

    }



    private void getPairedDevicesList() {

        Set<BluetoothDevice> BluethoothDeviceSet = mBluetoothAdapter.getBondedDevices();

        ArrayList<String> mArraylist= new ArrayList<>();

        for(BluetoothDevice i : BluethoothDeviceSet){

            mArraylist.add(i.getName() + "/n"+ i.getAddress());

        }

        Toast.makeText(MainActivity.this,"Paired Devices List",Toast.LENGTH_LONG).show();

        ArrayAdapter adp = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,mArraylist);

        mListView.setAdapter(adp);

    }

    private void offBluetooth() {

        mBluetoothAdapter.disable();

        Toast.makeText(MainActivity.this,"Bluetooth off",Toast.LENGTH_LONG).show();
    }
}
