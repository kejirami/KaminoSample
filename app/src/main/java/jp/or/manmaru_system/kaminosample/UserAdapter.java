package jp.or.manmaru_system.kaminosample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import jp.or.manmaru_system.kaminosample.localdb.User;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, List<User> list) {
//        super(context, R.layout.user_listitem, list);
        super(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ret = super.getView(position, convertView, parent);
        TextView tvUserName = (TextView)ret;
        //TextView tvUserName = (TextView)ret.findViewById(R.id.tv_username);
        tvUserName.setText(getItem(position).getName());
        return ret;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ret = super.getDropDownView(position, convertView, parent);
        TextView tvUserName = (TextView)ret;
        tvUserName.setText(getItem(position).getName());
        return ret;
    }

}
