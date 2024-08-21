package jp.or.manmaru_system.kaminosample;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WorkViewHolder extends RecyclerView.ViewHolder {
    private CheckBox chkSelect;
    private TextView tvNumber;
    private TextView tvStartTime;

    public WorkViewHolder(View itemView) {
        super(itemView);
        chkSelect = itemView.findViewById(R.id.chk_select);
        tvNumber = itemView.findViewById(R.id.tv_number);
        tvStartTime = itemView.findViewById(R.id.tv_time);
    }

    public CheckBox getSelectView(){return chkSelect;}
    public TextView getNumberView(){return tvNumber;}
    public TextView getStartTimeView(){return tvStartTime;}
}
