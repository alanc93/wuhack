package com.nilanc.herenow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MsgAdapter extends BaseAdapter {
	
	private final List<Message> msgs;
	private Context context;

	public MsgAdapter(Context context, List<Message> msgs) {
		this.context = context;
		this.msgs = msgs;
	}

    @Override
    public int getCount() {
        return msgs != null ? msgs.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return msgs != null ? msgs.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgHolder holder;
        Message msg = (Message) getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.msg_layout, null);
            holder = createMsgHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MsgHolder) convertView.getTag();
        }
        holder.msgText.setText(msg.getMsg());
        if (msg.fromSelf()) {
            holder.content.setGravity(Gravity.END);
            holder.content.setBackgroundColor(0xFFFEEE);
        } else {
            holder.content.setGravity(Gravity.START);
        }
//        holder.msgDet.setText(/*getTimeText(msg.getTime())*/"Time");

        return convertView;
    }

    public String getTimeText(Date time) {
        return time.toString();
    }

    public void add(Message m) { msgs.add(m); }

    public void add(List<Message> ms) { msgs.addAll(ms); }

    public MsgHolder createMsgHolder(View v) {
        MsgHolder holder = new MsgHolder();
        holder.msgText = (TextView) v.findViewById(R.id.msgText);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        return holder;
    }

    private static class MsgHolder {
        public TextView msgText;
        public LinearLayout content;
    }
}
