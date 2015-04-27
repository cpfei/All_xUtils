package com.example.all_xutils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

//    http://a1.greentree.cn:8029/Api/index.php/Other/getActivityList
//    pageindex=1&pagesize=10&cityId=226


    @ViewInject(R.id.refresh)
    private SwipeRefreshLayout refresh;
    @ViewInject(R.id.listView)
    private ListView listView;

    private BeanAdapter adapter;
    private List<Bean> list=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        //初始化BitmapUtils
        BitmapHelper.init(this);
        DbHelper.init(this);//初始化DbUtils

        try {
            list=DbHelper.getUtils().findAll(Bean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        if(list==null) {
            list = new ArrayList<Bean>();
        }
        adapter = new BeanAdapter(this, list);

        listView.setAdapter(adapter);
        //给下拉设置监听
        refresh.setOnRefreshListener(this);

    }
    //重写下拉刷新的方法
    @Override
    public void onRefresh() {
        adapter.clear();//清空原来的数据
        HttpUtils utils = new HttpUtils();
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("pageindex", "2");
        params.addQueryStringParameter("pagesize", "20");
        params.addQueryStringParameter("cityId", "226");

        utils.send(HttpRequest.HttpMethod.POST,"http://a1.greentree.cn:8029/Api/index.php/Other/getActivityList",
                params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                try {
                    JSONObject obj = new JSONObject(objectResponseInfo.result);
                    JSONArray items = obj.getJSONObject("responseData").getJSONArray("items");

                    List<Bean> beans=new ArrayList<Bean>();

                    for(int i=0;i<items.length();i++){
                        JSONObject item = items.getJSONObject(i);
                        Bean bean = new Bean();

                        bean.setId(item.getInt("id"));
                        bean.setTitle(item.getString("title"));
                        bean.setImage(item.getString("imageUrl"));


                        beans.add(bean);


                    }

                    DbHelper.getUtils().saveOrUpdateAll(beans);

                    adapter.addAll(beans);



                    Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();

                    refresh.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (DbException e) {
                    e.printStackTrace();
//                    System.out.println("数据库错误");
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });


    }
}
