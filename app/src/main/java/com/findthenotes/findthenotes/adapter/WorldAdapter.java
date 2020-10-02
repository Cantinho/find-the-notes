package com.findthenotes.findthenotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.model.Level;
import com.findthenotes.findthenotes.model.World;
import com.findthenotes.findthenotes.view.TrainingActivity;
import com.findthenotes.findthenotes.view.WorldActivity;

import java.util.List;

public class WorldAdapter extends RecyclerView.Adapter<WorldAdapter.WorldViewHolder> {

    private Context mContext;
    private List<World> mWorlds;

    public WorldAdapter(Context context, List<World> worlds) {
        mContext = context;
        mWorlds = worlds;
    }

    @Override
    public WorldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_world, parent, false);
        WorldViewHolder worldViewHolder = new WorldViewHolder(v);
        return worldViewHolder;
    }

    @Override
    public void onBindViewHolder(WorldViewHolder holder, int position) {
        holder.worldName.setText(mWorlds.get(position).getName());
        holder.worldBg.setBackground(getLevelDrawable(mWorlds.get(position).getLevel()));
        holder.worldIcon.setImageDrawable(getLevelIcon(mWorlds.get(position).getLevel()));
        String label = getLevelString(mWorlds.get(position).getLevel());
        holder.worldDifficulty.setText(label);

        final int worldPosition = position;
        holder.worldCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(World.TRAINING.equals(mWorlds.get(worldPosition).getTag())) {
                    intent = new Intent(mContext, TrainingActivity.class);
                } else {
                    intent = new Intent(mContext, WorldActivity.class);

                    intent.putExtra(World.TAG, mWorlds.get(worldPosition).getTag());
                    intent.putExtra(World.ID, mWorlds.get(worldPosition).getId());

                }
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWorlds.size();
    }

    private String getLevelString(Level level) {
        switch (level) {
            case TRAINING:
                return mContext.getResources().getString(R.string.training);
            case EASILY:
                return mContext.getResources().getString(R.string.easily);
            case EASY:
                return mContext.getResources().getString(R.string.easy);
            case MEDIUM:
                return mContext.getResources().getString(R.string.medium);
            case HARD:
                return mContext.getResources().getString(R.string.hard);
            case EXPERT:
                return mContext.getResources().getString(R.string.expert);
            case EXTREME:
                return mContext.getResources().getString(R.string.extreme);
            case ABSOLUTE:
                return mContext.getResources().getString(R.string.absolute);
            default:
                return null;
        }

    }

    private Drawable getLevelDrawable(Level level) {
        switch (level) {
            case TRAINING:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_basic);
                }

            case EASILY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_basic);
                }

            case EASY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_basic);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_basic);
                }

            case MEDIUM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_middle, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_middle);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_middle);
                }
            case HARD:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_hard, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_hard);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_hard);
                }
            case EXPERT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_expert, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_expert);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_expert);
                }
            case EXTREME:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_expert, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_expert);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_expert);
                }
            case ABSOLUTE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_absolute, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.bg_absolute);
                } else {
                    return mContext.getDrawable(R.mipmap.bg_absolute);
                }
            default:
                return null;
        }
    }

    private Drawable getLevelIcon(Level level) {
        switch (level) {
            case TRAINING:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_basic_level_home);
                }
            case EASILY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_basic_level_home);
                }
            case EASY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_basic_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_basic_level_home);
                }
            case MEDIUM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_middle_hard_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_middle_hard_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_middle_hard_level_home);
                }
            case HARD:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_middle_hard_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_middle_hard_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_middle_hard_level_home);
                }
            case EXPERT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_expert_level_home);
                }
            case EXTREME:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_expert_level_home);
                }
            case ABSOLUTE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home, mContext.getTheme());
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return mContext.getResources().getDrawable(R.mipmap.ic_expert_level_home);
                } else {
                    return mContext.getDrawable(R.mipmap.ic_expert_level_home);
                }
            default:
                return null;
        }
    }

    public static class WorldViewHolder extends RecyclerView.ViewHolder {
        CardView worldCardView;
        TextView worldDifficulty;
        TextView worldName;
        LinearLayout worldBg;
        ImageView worldIcon;

        WorldViewHolder(View itemView) {
            super(itemView);
            worldCardView = (CardView) itemView.findViewById(R.id.cv_world);
            worldName = (TextView) itemView.findViewById(R.id.world_name);
            worldDifficulty = (TextView) itemView.findViewById(R.id.world_difficulty);
            worldDifficulty.setTypeface(null, Typeface.BOLD);
            worldBg = (LinearLayout) itemView.findViewById(R.id.bg_world);
            worldIcon = (ImageView) itemView.findViewById(R.id.iv_world);

        }
    }
}
