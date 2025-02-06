package com.example.bt_android_4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView id_main;
    AppCompatButton btn,button_reload;
    Api apiService;
    List<DataModel> messageList = new ArrayList<>();
    MessageAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        apiService = RetrofitClient.getClient().create(Api.class);
        anhXa();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastId();
            }
        });

        button_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAll();
            }
        });
    }

    private void anhXa() {
        editText = findViewById(R.id.edt_Last_Id);
        btn = findViewById(R.id.btn_last_id);
        id_main = findViewById(R.id.id_main);
        button_reload = findViewById(R.id.button_reload);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void getAll(){
        Call<ListAllModel> call = apiService.getAllData("list_all");
        call.enqueue(new Callback<ListAllModel>() {
            @Override
            public void onResponse(Call<ListAllModel> call, Response<ListAllModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListAllModel myResponse = response.body();
                    messageList = myResponse.getData();
                    adapter = new MessageAdapter(getApplicationContext() ,messageList);
                    recyclerView.setAdapter(adapter);
                } else {
                    id_main.setText("Lỗi: Không nhận được dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<ListAllModel> call, Throwable t) {
                id_main.setText("Lỗi: " + t.getMessage());
                Log.d("loi",t.getMessage());
            }
        });
    }

    private void getLastId() {
        Call<LastIDModel> call = apiService.getLastIdData("last_id");
        call.enqueue(new Callback<LastIDModel>() {
            @Override
            public void onResponse(Call<LastIDModel> call, Response<LastIDModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LastIDModel myResponse = response.body();
                    id_main.setText("" + myResponse.getLast_id());
                } else {
                    id_main.setText("Lỗi: Không nhận được dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<LastIDModel> call, Throwable t) {
                id_main.setText("Lỗi: " + t.getMessage());
                Log.d("loi",t.getMessage());
            }
        });
    }
}