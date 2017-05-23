package com.strangeman.vipqa.activitys;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.adapter.ProductAdapter;
import com.strangeman.vipqa.entity.CheckLoginState;
import com.strangeman.vipqa.entity.Order;
import com.strangeman.vipqa.entity.Product;
import com.strangeman.vipqa.entity.User;
import com.strangeman.vipqa.network.MyRequest;
import com.strangeman.vipqa.utils.OrderMethod;
import com.strangeman.vipqa.utils.ProductMethod;
import com.strangeman.vipqa.utils.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllProductActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<Product> productList;
    private ProductAdapter adapter;
    private MyRequest myRequest;
    private SwipeRefreshLayout swipeRefresh;
    private Toolbar toolbar;
    private NavigationView navView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private View headview;
    private Button button;
    private TextView textView;
    private CircleImageView circleImageView;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_product);
        myRequest=new MyRequest(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refresh();
            }

            private void refresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myRequest.getAllProduct(new VolleyCallback<ProductMethod>() {
                                    @Override
                                    public void onSuccess(ProductMethod result) {
                                        if(result.getProducts()!=null) {
                                            productList.clear();
                                            if (result.getProducts() != null) {
                                                for (int i = 0; i < result.getProducts().size(); i++) {
                                                    productList.add(result.getProducts().get(i));
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }


        });
        adapter = new ProductAdapter(productList);
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = productList.get(position);
                Intent intent = new Intent(AllProductActivity.this, com.strangeman.vipqa.activitys.MainActivity.class);
                intent.putExtra("productId",product.getProductId());
                intent.putExtra("productName",product.getProductName());
                intent.putExtra("productPrice",product.getPrice());
                intent.putExtra("imagePath",product.getImagePath());
                startActivity(intent);
            }
        });
        initProducts();
        recyclerView.setAdapter(adapter);

        headview = navView.inflateHeaderView(R.layout.nav_header);
        button = (Button) headview.findViewById(R.id.login);
        textView = (TextView) headview.findViewById(R.id.identity);
        circleImageView=(CircleImageView)headview.findViewById(R.id.icon_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckLoginState.getUser()==null) {
                    Intent intent = new Intent(AllProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    CheckLoginState.setUser(null);
                   // headview = navView.inflateHeaderView(R.layout.nav_header);
                    //button = (Button) headview.findViewById(R.id.login);
                    //textView = (TextView) headview.findViewById(R.id.identity);

                    button.setText(getString(R.string.login));
                    textView.setText(getString(R.string.visitorIdentity));
                    circleImageView.setImageResource(R.drawable.vipuser);
                }
            }
        });
        user = CheckLoginState.getUser();
        if (user != null) {
            button.setText(getString(R.string.logout));
            textView.setText(user.getUserName());
            Glide.with(this).load(user.getUserPhoto()).into(circleImageView);
        }

        navView.setCheckedItem(R.id.nav_order);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_order:
//                        if(CheckLoginState.getUser()!=null){
//                            myRequest.getOrders(new VolleyCallback<OrderMethod>() {
//                                @Override
//                                public void onSuccess(OrderMethod result) {
//
//                                }
//                            },CheckLoginState.getUser().getUserId());
//                        }
                        break;
                    case R.id.nav_question:
                        break;
                    case R.id.nav_answer:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.user:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public void initProducts(){
        myRequest.getAllProduct(new VolleyCallback<ProductMethod>() {
            @Override
            public void onSuccess(ProductMethod result) {
                if(result!=null){
                    if(result.getProducts()!=null) {
                        productList.clear();
                        for (int i = 0; i < result.getProducts().size(); i++) {
                            productList.add(result.getProducts().get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = CheckLoginState.getUser();
        if(user!=null){


            button.setText(getString(R.string.logout));
            textView.setText(user.getUserName());
            Glide.with(this).load(user.getUserPhoto()).into(circleImageView);


        }
    }
}
