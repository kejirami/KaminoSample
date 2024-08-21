package jp.or.manmaru_system.kaminosample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.or.manmaru_system.kaminosample.localdb.Work;

public class WorkRecycleViewAdapter extends RecyclerView.Adapter<WorkViewHolder>{

    private List<Work> mList;

    public WorkRecycleViewAdapter(List<Work> list) {
        mList = list;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_listitem, parent,false);
        return new WorkViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        holder.getSelectView().setChecked(true);
        holder.getNumberView().setText(mList.get(position).getNumber());
        holder.getStartTimeView().setText(mList.get(position).getStartTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
