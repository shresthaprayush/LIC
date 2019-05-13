package com.example.lic.Main.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.lic.Main.Datamodel.Inventory_Model;
import com.example.lic.Main.DataAdapters.Inventory_Adapter_Second;
import com.example.lic.Main.Datamodel.User;
import com.example.lic.Main.Utilities.RetrofitClient;
import com.example.lic.Main.Utilities.SharedPreferenceManager;
import com.example.lic.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inventory_Secong extends AppCompatActivity {

    int id;
    String type,brand;
    RecyclerView recyclerView2;
    private List<Inventory_Model> inventoryModelList;
    private Inventory_Adapter_Second inventory_adapter_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory__secong);

       recyclerView2 = findViewById(R.id.recyclerviewsecondinventory);

        User user = SharedPreferenceManager.getmInstance(this).getUser();
       String Pan = String.valueOf(user.getUserid());


        type = getIntent().getExtras().getString("Productname");
        brand = getIntent().getExtras().getString("brand");
        id = getIntent().getExtras().getInt("ID");

        getSupportActionBar().setTitle(brand+" "+"("+type+")" + id);

        Call<List<Inventory_Model>> call = RetrofitClient.getmInstance().getApi().getspecificdata(id,Pan);

        call.enqueue(new Callback<List<Inventory_Model>>() {
            @Override
            public void onResponse(Call<List<Inventory_Model>> call, Response<List<Inventory_Model>> response) {
                
                

                inventoryModelList = response.body();
                if (inventoryModelList!=null){
                    inventory_adapter_second = new Inventory_Adapter_Second(inventoryModelList,getApplicationContext());
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView2.setAdapter(inventory_adapter_second);
                }

                else {
                    Toast.makeText(Inventory_Secong.this, "No data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Inventory_Model>> call, Throwable t) {

            }
        });






    }
}
