package baghi.naeem.com.assignment4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HotelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        HotelRecyclerAdapter.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HotelRecyclerAdapter.ViewHolder hotelViewHolder = (ViewHolder) holder;
        Hotel hotel = DataSource.getHotels().get(position);
        hotelViewHolder.nameTextView.setText(hotel.getName());
        hotelViewHolder.addressTextView.setText(hotel.getAddress());
        hotelViewHolder.imageView.setImageResource(hotel.getImageId());
    }

    @Override
    public int getItemCount() {
        return DataSource.getHotels().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView addressTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.nameTextView = (TextView) itemView.findViewById(R.id.hotel_name);
            this.addressTextView = (TextView) itemView.findViewById(R.id.hotel_address);
            this.imageView = (ImageView) itemView.findViewById(R.id.hotel_image);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
