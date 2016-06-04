package driver.com.driver.screens;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import driver.com.driver.R;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.HandyHolderView> {

    private static OnItemClickListener listener;
    Context ctx;

    public JobListAdapter(Context context) {
        this.ctx = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public HandyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list_row, parent, false);
        return new HandyHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(HandyHolderView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public static class HandyHolderView extends RecyclerView.ViewHolder {

        public HandyHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}


