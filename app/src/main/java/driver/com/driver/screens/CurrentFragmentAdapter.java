package driver.com.driver.screens;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import driver.com.driver.R;
import driver.com.driver.model.ResponseParams.AllShipmentResponse;

/**
 * Created by kalaivani on 4/28/2016.
 */
public class CurrentFragmentAdapter  extends RecyclerView.Adapter<CurrentFragmentAdapter.CurrentHolderView> {

    private static OnItemClickListener listener;
    Context ctx;
    private List<AllShipmentResponse> allShipment;
    private Typeface HnThin,HnBold,HnLIght,gibson_light,gibson_regular;

    //private List<AllShipmentResponse1> allShipment2;
    //Activity activity = (Activity) ctx;


    public CurrentFragmentAdapter(Context context, List<AllShipmentResponse> allShipment1) {
        this.ctx = context;
        this.allShipment = allShipment1;
       // gibson_light = Typeface.createFromAsset(this.getAssets(), "Gibson_Light.otf");
        //HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        //HnLIght = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");


        /*this.allShipment2 = allShipment2;*/
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CurrentHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list_row, parent, false);
        return new CurrentHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(CurrentHolderView holder, int position) {
               try {/*holder.days.setText(dataValue.get(position));
        holder.faverote_venu_name_txt.setText(cafeList.get(position).getFavorateVenuName());*/

            AllShipmentResponse response = allShipment.get(position);

            if (allShipment.get(position).getOrderStatus().equals("Pending")) {
                Log.d("Test","Test");
                holder.joblist_image_status.setImageResource(R.drawable.icon_more);
                holder.txt_status.setText(response.getOrderStatus());
                holder.et_pickupaddress.setText(response.getFromAddress());
                holder.txt_date.setText(response.getOrderDate());
                holder.txt_time.setText(response.getOrderTime());
                holder.$txt_dollar.setText(response.getTotalAmount());

            }
            if (allShipment.get(position).getOrderStatus().equals("In Transit")) {
                Log.d("Test","Test");
                holder.joblist_image_status.setImageResource(R.drawable.icon_transit);
                holder.txt_status.setText(response.getOrderStatus());
                holder.et_pickupaddress.setText(response.getFromAddress());
                holder.txt_date.setText(response.getOrderDate());
                holder.txt_time.setText(response.getOrderTime());
                holder.$txt_dollar.setText(response.getTotalAmount());




        }
        } catch (Exception e) {

            System.out.println("Exception : " + e.getMessage());
        }



    }

    @Override
    public int getItemCount() {

        int inc = 0;

//        try {
//            for (int i = 0; i < allShipment.size(); i++) {
//                System.out.println(i + " === i === ");
//                if (allShipment.get(i).getOrderStatus().equals("Pending")) {
//                    inc++;
//                    System.out.println(i + " inc = " + inc);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return allShipment.size();
    }




    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

    }

    public static class CurrentHolderView extends RecyclerView.ViewHolder {

        TextView et_pickupaddress;
        TextView txt_date;
        TextView txt_time;
        TextView $txt_dollar;
        TextView txt_status;
    ImageView joblist_image_status;

        public CurrentHolderView(View view) {
            super(view);
            et_pickupaddress = (TextView) view.findViewById(R.id.joblist_et_pickupaddress);
            txt_date = (TextView) view.findViewById(R.id.joblist_txt_date);
            txt_time = (TextView) view.findViewById(R.id.joblist_txt_time);
            $txt_dollar = (TextView) view.findViewById(R.id.joblist_txt_dollar);
            txt_status = (TextView) view.findViewById(R.id.joblist_txt_status);
            joblist_image_status = (ImageView) view.findViewById(R.id.joblist_image_status);

            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }


}


