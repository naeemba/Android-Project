package baghi.naeem.com.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class HotelActivity extends AppCompatActivity {

    private RecyclerView hotelRecyclerView;
    private HotelRecyclerAdapter hotelRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        hotelRecyclerView = (RecyclerView) findViewById(R.id.hotel_recycler_view);
        hotelRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        hotelRecyclerView.setLayoutManager(layoutManager);

        hotelRecyclerAdapter = new HotelRecyclerAdapter();

        hotelRecyclerAdapter.setOnItemClickListener(new HotelRecyclerAdapter.ClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Hotel hotel = DataSource.getHotels().get(position);
                if(hotel != null) {
                    Toast.makeText(HotelActivity.this, hotel.getName() , Toast.LENGTH_SHORT).show();
                }
            }
        });

        hotelRecyclerView.setAdapter(hotelRecyclerAdapter);
    }
}
