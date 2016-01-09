package fr.insa.h4401.gfaim;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.rey.material.widget.Button;
import com.rey.material.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<ItemModel> data;
    private List<ViewHolder> alarmsView;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public RecyclerViewRecyclerAdapter(final List<ItemModel> data) {
        alarmsView = new ArrayList<>();
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        ViewHolder v = new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_list_row, parent, false));
        alarmsView.add(v);
        return v;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemModel item = data.get(position);
        final Resources resource = context.getResources();
        holder.textView.setText(item.description);

        if((item.isOn && !holder.aSwitch.isChecked()) ||
                (!item.isOn && holder.aSwitch.isChecked()) ){
            holder.aSwitch.toggle();
        }

        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmsFragment.remove(item);
            }
        });

        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.triangle, 0f, 180f).start();
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.itemView.setElevation(5);
                holder.lineAbove.setVisibility(View.GONE);
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.triangle, 180f, 0f).start();
                holder.lineAbove.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(0x00000000);

            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });

        holder.chooseRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(R.string.titleChooseRestaurant)
                        .widgetColor(resource.getColor(R.color.colorPrimary))
                        .items(R.array.restaurants_favoris)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });

    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        for(ViewHolder v : alarmsView){
            if(v.expandableLayout.isExpanded() && !v.expandableLayout.equals(expandableLayout)){
                v.expandableLayout.toggle();
            }
        }
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout buttonLayout;
        public ExpandableRelativeLayout expandableLayout;
        public View triangle;
        public View lineAbove;
        public View trash;
        public Switch aSwitch;
        public Button chooseRestaurantButton;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.alarm_element);
            expandableLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout);
            triangle = v.findViewById(R.id.triangle);
            lineAbove = v.findViewById(R.id.line);
            trash = v.findViewById(R.id.trash);
            aSwitch = (Switch) v.findViewById(R.id.switch_alarm);
            chooseRestaurantButton = (Button) v.findViewById(R.id.alarm_choose_restaurant);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}