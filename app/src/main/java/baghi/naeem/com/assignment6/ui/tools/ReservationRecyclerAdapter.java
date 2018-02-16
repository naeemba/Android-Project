package baghi.naeem.com.assignment6.ui.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.entities.Reservation;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Reservation> reservations = new ArrayList<>();
    private static ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        ReservationRecyclerAdapter.clickListener = clickListener;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ReservationRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Reservation.GENERAL_CALENDAR_FORMAT, Locale.ENGLISH);
        ReservationRecyclerAdapter.ViewHolder hotelViewHolder = (ReservationRecyclerAdapter.ViewHolder) holder;
        final Reservation reservation = reservations.get(position);
        hotelViewHolder.hotelNameTextView.setText(reservation.getHotel().getName());
        hotelViewHolder.checkInTextView.setText(simpleDateFormat.format(reservation.getCheckInDate().getTime()));
        hotelViewHolder.checkOutTextView.setText(simpleDateFormat.format(reservation.getCheckOutDate().getTime()));
        hotelViewHolder.roomTypeTextView.setText(reservation.getCount() + " " + reservation.getRoomType().getValue());
        hotelViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(reservations.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelNameTextView;
        TextView checkInTextView;
        TextView checkOutTextView;
        TextView roomTypeTextView;
        ImageView deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            this.hotelNameTextView = (TextView) itemView.findViewById(R.id.hotel_name_text_view);
            this.checkInTextView = (TextView) itemView.findViewById(R.id.check_in_text_view);
            this.checkOutTextView = (TextView) itemView.findViewById(R.id.check_out_text_view);
            this.roomTypeTextView = (TextView) itemView.findViewById(R.id.room_type_text_view);
            this.deleteButton = (ImageView) itemView.findViewById(R.id.delete_reservation_button);
        }
    }

    public interface ClickListener {
        void onItemClick(String reservationId);
    }
}
