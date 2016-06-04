package driver.com.driver.screens;

import android.content.Context;
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
import driver.com.driver.model.ResponseParams.AdditionalResponse;

/**
 * Created by androidusr1 on 1/6/16.
 */
public class AdditionalAdapter extends RecyclerView.Adapter<AdditionalAdapter.AdditionalHolderView> {

    private static OnItemClickListener listener;
    Context ctx;
    private List<AdditionalResponse> response;
    private AdditionalAdapter thisAdapter = this;
    ImageView minus;
//private List<AllShipmentResponse1> allShipment2;
//Activity activity = (Activity) ctx;

    public AdditionalAdapter(Context context, final List<AdditionalResponse> response) {
        Log.d("Testing", "Additional Adapter Constructer");
        this.ctx = context;
        this.response = response;
        /*this.allShipment2 = allShipment2;*/
       /* minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.remove(getItemCount());
            }
        });*/
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public AdditionalHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_additional_item, parent, false);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.added_additional_item, parent, false);

        Log.d("Testing", "Additional Holder View");
        return new AdditionalHolderView(view);
    }

    @Override
    public void onBindViewHolder(AdditionalHolderView holder, int position) {


        AdditionalResponse model = response.get(position);
        Log.d("Testing", "onBind" + model.getTitle() + " " + model.getValue());
        holder.value.setText(model.getValue());
        holder.title.setText(model.getTitle());
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testingtest","");
            }
        });
        Log.d("Testing", "on Bind view Holder");


    }

    @Override
    public int getItemCount() {
        Log.d("Testing", "Size in Adapter " + response.size());

        return response.size();
    }





    // Define the listener interface
    public interface OnItemClickListener {

        void onItemClick(View itemView, int position);


    }

    public static class AdditionalHolderView extends RecyclerView.ViewHolder {


        TextView title, value;
        ImageView minus;

        public AdditionalHolderView(View view) {

            super(view);

            Log.d("Testing", "Additional Holder");
            title = (TextView) view.findViewById(R.id.add_services_label);
            value = (TextView) view.findViewById(R.id.add_services_value);
            minus = (ImageView) view.findViewById(R.id.image_minus);
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


