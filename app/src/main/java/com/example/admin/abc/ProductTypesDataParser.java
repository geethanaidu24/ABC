package com.example.admin.abc;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Geetha on 4/12/2017 for parsing database data and stored into ProductTypeItem array or if not their goes to another related activity.
 */

public class ProductTypesDataParser extends AsyncTask<Void,Void,Integer> {
    Context c;
    ListView lv;
    String jsonData;
    int pid;
    ArrayList<ProductTypeItem> productTypeItems=new ArrayList<>();

    public ProductTypesDataParser(Context c, ListView lv, String jsonData, int pid) {
        this.c = c;
        this.lv = lv;
        this.jsonData = jsonData;
        this.pid = pid;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }
    @Override

    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result==0)
        {
            //Toast.makeText(c,"Unable to parse",Toast.LENGTH_SHORT).show();
            // opening new activity
            openProductSizesActivity(pid);
        }else
        {

            final ProductTypesListAdapter adapter=new ProductTypesListAdapter(c,productTypeItems);
            lv.setAdapter(adapter);

        }
    }

    private int parseData()
    {
        try
        {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;
            productTypeItems.clear();
            ProductTypeItem productTypeItem;
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                Log.d("result response: ", "> " + jo);
                int ProductTypeId=jo.getInt("ProductTypeId");
                String ProductType =jo.getString("ProductType");
                String ImageUrl=jo.getString("ImageUrl");
                int ProductId = jo.getInt("ProductId");
                productTypeItem=new ProductTypeItem();
                productTypeItem.setProductTypeId(ProductTypeId);
                productTypeItem.setProductType(ProductType);
                productTypeItem.setImageUrl(ImageUrl);
                productTypeItem.setProductId(ProductId);
                productTypeItems.add(productTypeItem);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void openProductSizesActivity(int pid) {
        Intent intent = new Intent(c,ProductTypeSizes.class);
        intent.putExtra("PRODUCTID_KEY", pid);
        c.startActivity(intent);
    }
}