package com.example.fruit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fruit.R;
import com.example.fruit.bean.Comment;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;

    // 定义 ViewHolder，用于缓存评论的视图组件
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvContent, tvTimestamp;

        public ViewHolder(View view) {
            super(view);
            tvUser = view.findViewById(R.id.textView_user);
            tvContent = view.findViewById(R.id.textView_content);
            tvTimestamp = view.findViewById(R.id.textView_timestamp);
        }
    }

    // 构造函数，传入评论数据
    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载 item_comment 布局
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取当前位置的评论
        Comment comment = commentList.get(position);
        holder.tvUser.setText(comment.getUserAccount());
        holder.tvContent.setText(comment.getContent());
        // 格式化时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.tvTimestamp.setText(sdf.format(comment.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
