package com.sumu.googleplay.adapter.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sumu.googleplay.Contacts;
import com.sumu.googleplay.R;
import com.sumu.googleplay.bean.CategoryInfo;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/28   11:55
 * <p/>
 * 描述：
 * <p/>CategoryAdapter的ViewHolder
 * ==============================
 */
public class CategoryViewHolder extends BaseViewHolder implements View.OnClickListener {

    private CategoryInfo info;

    public CategoryViewHolder(Context context) {
        super(context);
    }

    private RelativeLayout[] rls;
    private ImageView[] ivs;
    private TextView[] tvs;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.item_category_content, null);
        rls = new RelativeLayout[3];
        rls[0] = (RelativeLayout) view.findViewById(R.id.rl_1);
        rls[1] = (RelativeLayout) view.findViewById(R.id.rl_2);
        rls[2] = (RelativeLayout) view.findViewById(R.id.rl_3);
        ivs = new ImageView[3];
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
        tvs = new TextView[3];
        tvs[0] = (TextView) view.findViewById(R.id.tv_1);
        tvs[1] = (TextView) view.findViewById(R.id.tv_2);
        tvs[2] = (TextView) view.findViewById(R.id.tv_3);
        return view;
    }

    @Override
    public void setDataToView(Object data) {
        info = (CategoryInfo) data;
        if (!TextUtils.isEmpty(info.getName1()) && !TextUtils.isEmpty(info.getUrl1())) {
            rls[0].setVisibility(View.VISIBLE);
            tvs[0].setText(info.getName1());
            bitmapUtils.display(ivs[0], Contacts.HOME_IMAGE_URL + info.getUrl1());
            rls[0].setOnClickListener(this);
        } else {
            rls[0].setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(info.getName2()) && !TextUtils.isEmpty(info.getUrl2())) {
            rls[1].setVisibility(View.VISIBLE);
            tvs[1].setText(info.getName2());
            bitmapUtils.display(ivs[1], Contacts.HOME_IMAGE_URL + info.getUrl2());
            rls[1].setOnClickListener(this);
        } else {
            rls[1].setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(info.getName3()) && !TextUtils.isEmpty(info.getUrl3())) {
            rls[2].setVisibility(View.VISIBLE);
            tvs[2].setText(info.getName3());
            bitmapUtils.display(ivs[2], Contacts.HOME_IMAGE_URL + info.getUrl3());
            rls[2].setOnClickListener(this);
        } else {
            rls[2].setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_1:
                Toast.makeText(context, info.getName1(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_2:
                Toast.makeText(context, info.getName2(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_3:
                Toast.makeText(context, info.getName3(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
