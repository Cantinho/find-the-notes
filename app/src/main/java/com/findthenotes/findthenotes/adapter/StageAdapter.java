package com.findthenotes.findthenotes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.view.StageActivity;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder>{

    private List<Stage> mStages;
    private Context mContext;

//    public StageAdapter(List<String> stages) {
//        mStages = stages;
//    }

    public StageAdapter(List<Stage> stages, Context context) {
        mContext = context;
        mStages = stages;
    }

    @Override
    public StageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stage, parent, false);
        StageViewHolder stageViewHolder = new StageViewHolder(v);
        return stageViewHolder;
    }

    @Override
    public void onBindViewHolder(StageViewHolder holder, final int position) {
        holder.stageNumber.setText("");
        holder.starsView.setVisibility(View.GONE);

        if (mStages.get(position).getStars() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_inactive));
                holder.starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_inactive));
                holder.starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_inactive));
            } else {
                holder.starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_inactive));
                holder.starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
                holder.starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_inactive));
            }
        } else if (mStages.get(position).getStars() == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_inactive));
                holder.starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_inactive));
            } else {
                holder.starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
                holder.starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_inactive));
            }
        } else if (mStages.get(position).getStars() == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                holder.starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_inactive));
            } else {
                holder.starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                holder.starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_inactive));
            }
        } else if (mStages.get(position).getStars() == 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                holder.starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_side_active));
            } else {
                holder.starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_active));
                holder.starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                holder.starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_side_active));
            }
        }

        if (mStages.get(position).isUnlocked()) {
            holder.starsView.setVisibility(View.VISIBLE);

            holder.stageNumber.setText("" + mStages.get(position).getNumber());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.stageBackGround.setBackground(mContext.getDrawable(R.mipmap.bg_level_basic));
            } else {
                holder.stageBackGround.setBackground(mContext.getResources().getDrawable(R.mipmap.bg_level_basic));
            }
        } else {
            holder.starsView.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.stageBackGround.setBackground(mContext.getDrawable(R.mipmap.bg_level_block));
            } else {
                holder.stageBackGround.setBackground(mContext.getResources().getDrawable(R.mipmap.bg_level_block));
            }
        }

        holder.stageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStages.get(position).isUnlocked()) {
                    Intent intent = new Intent(mContext, StageActivity.class);
                    intent.putExtra(Stage.ID, mStages.get(position).getId());
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStages.size();
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {
        CardView stageCardView;
        TextView stageNumber;
        LinearLayout stageBackGround;
        FrameLayout starsView;
        ImageView starLeft;
        ImageView starRight;
        ImageView starMiddle;

        StageViewHolder(View itemView) {
            super(itemView);
            stageCardView = (CardView) itemView.findViewById(R.id.cv_stage);
            stageNumber = (TextView) itemView.findViewById(R.id.stage_number);
            stageBackGround = (LinearLayout) itemView.findViewById(R.id.stage_background);
            starsView = (FrameLayout) itemView.findViewById(R.id.stars_view);
            starLeft = (ImageView) itemView.findViewById(R.id.star_left);
            starRight = (ImageView) itemView.findViewById(R.id.star_right);
            starMiddle = (ImageView) itemView.findViewById(R.id.star_middle);
        }
    }
}
