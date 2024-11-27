package com.example.fruit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView; // 正确的导入路径

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fruit.R;
import com.example.fruit.adapter.CommentAdapter;
import com.example.fruit.bean.Browse;
import com.example.fruit.bean.Car;
import com.example.fruit.bean.Comment;
import com.example.fruit.bean.Fruit;
import com.example.fruit.util.SPUtils;
import com.example.fruit.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 水果明细信息
 */
public class FruitDetailActivity extends AppCompatActivity {
    private Activity mActivity;
    private ImageView ivImg;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvContent;
    private TextView tvIssuer;
    private Button btnCollect;
    private Button btnCancel;
    private ActionBar mActionBar; // 标题栏
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 评论相关组件
    private RecyclerView recyclerViewComments;
    private EditText editTextComment;
    private Button buttonSubmitComment;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private String fruitTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_fruit_detail);

        // 初始化视图组件
        ivImg = findViewById(R.id.img);
        tvTitle = findViewById(R.id.title);
        tvDate = findViewById(R.id.date);
        tvContent = findViewById(R.id.content);
        tvIssuer = findViewById(R.id.issuer);
        btnCollect = findViewById(R.id.btn_collect);
        btnCancel = findViewById(R.id.btn_cancel);
        mActionBar = findViewById(R.id.myActionBar);

        // 初始化评论相关组件
        recyclerViewComments = findViewById(R.id.recyclerView_comments);
        editTextComment = findViewById(R.id.editText_comment);
        buttonSubmitComment = findViewById(R.id.button_submit_comment);

        // 设置 ActionBar
        mActionBar.setData(mActivity, "商品详情", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });

        Fruit fruit = (Fruit) getIntent().getSerializableExtra("fruit");
        fruitTitle = fruit.getTitle(); // 设置水果标题用于加载评论

        // 显示水果的详细信息
        tvTitle.setText(fruit.getTitle());
        tvDate.setText(String.format("上架时间:%s", fruit.getDate()));
        tvContent.setText(fruit.getContent());
        tvIssuer.setText(String.format("￥ %s", fruit.getIssuer()));
        Glide.with(mActivity)
                .asBitmap()
                .skipMemoryCache(true)
                .load(fruit.getImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivImg);

        String account = (String) SPUtils.get(mActivity, SPUtils.ACCOUNT, "");

        // 浏览记录
        Browse browse = DataSupport.where("account = ? and title = ?", account, fruit.getTitle()).findFirst(Browse.class);
        if (browse == null) {
            Browse browse1 = new Browse(account, fruit.getTitle());
            browse1.save();
        }

        Boolean isAdmin = (Boolean) SPUtils.get(mActivity, SPUtils.IS_ADMIN, false);
        if (!isAdmin) {
            Car order = DataSupport.where("account = ? and title = ?", account, fruit.getTitle()).findFirst(Car.class);
            btnCollect.setVisibility(order != null ? View.GONE : View.VISIBLE);
            btnCancel.setVisibility(order != null ? View.VISIBLE : View.GONE);
        }

        // 收藏按钮
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = new Car(fruit.getIssuer(), account, fruit.getTitle(), "S" + System.currentTimeMillis(), account, sf.format(new Date()));
                car.save();
                Toast.makeText(mActivity, "加入购物车成功", Toast.LENGTH_SHORT).show();
                btnCollect.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });

        // 取消收藏按钮
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car order = DataSupport.where("account = ? and title = ?", account, fruit.getTitle()).findFirst(Car.class);
                order.delete();
                Toast.makeText(mActivity, "已从购物车移除", Toast.LENGTH_SHORT).show();
                btnCollect.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
            }
        });

        // 设置 RecyclerView 和适配器
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        loadComments(); // 加载现有评论
        commentAdapter = new CommentAdapter(commentList);
        recyclerViewComments.setAdapter(commentAdapter);

        // 提交评论按钮点击事件
        buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextComment.getText().toString().trim();
                if (!content.isEmpty()) {
                    // 创建并保存评论
                    Comment comment = new Comment();
                    comment.setUserAccount(account);
                    comment.setContent(content);
                    comment.setTimestamp(System.currentTimeMillis());
                    comment.setFruitTitle(fruitTitle);
                    comment.save();

                    // 更新评论列表
                    commentList.add(comment);
                    commentAdapter.notifyItemInserted(commentList.size() - 1);
                    recyclerViewComments.scrollToPosition(commentList.size() - 1);
                    editTextComment.setText("");
                } else {
                    Toast.makeText(FruitDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 加载现有评论的方法
    private void loadComments() {
        commentList.clear();
        commentList.addAll(DataSupport.where("fruitTitle = ?", fruitTitle).find(Comment.class));
    }
}
