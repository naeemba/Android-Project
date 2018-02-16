package baghi.naeem.com.assignment6.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.ui.tools.HotelRecyclerAdapter;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;
import baghi.naeem.com.assignment6.entities.Hotel;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        RecyclerView hotelRecyclerView = (RecyclerView) findViewById(R.id.hotel_recycler_view);
        hotelRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        hotelRecyclerView.setLayoutManager(layoutManager);

        HotelRecyclerAdapter hotelRecyclerAdapter = new HotelRecyclerAdapter();

        hotelRecyclerAdapter.setOnItemClickListener(new HotelRecyclerAdapter.ClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Hotel hotel = DataSource.getHotels().get(position + "");
                if(hotel != null) {
                    ToastMaker.make(HotelActivity.this, hotel.getName());

                    Intent intent = new Intent(HotelActivity.this, HotelReservationActivity.class);
                    intent.putExtra("hotelId", hotel.getId() + "");
                    startActivity(intent);
                    HotelActivity.this.finish();
                }
            }
        });

        hotelRecyclerView.setAdapter(hotelRecyclerAdapter);
    }
}
