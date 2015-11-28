package com.sumu.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sumu.googleplay.R;
import com.sumu.googleplay.protocol.TopProtocol;
import com.sumu.googleplay.utils.UIUtils;
import com.sumu.googleplay.view.LoadingPage;

import java.util.List;

/**
 * ==============================
 * 作者：苏幕
 * <p/>
 * 时间：2015/11/22   19:39
 * <p/>
 * 描述：
 * <p/>排行界面
 * ==============================
 */
public class TopFragment extends BaseFragment {

    private List<String> datas;

    @Override
    protected LoadingPage.LoadResult load() {
        TopProtocol topProtocol = new TopProtocol();
        datas = topProtocol.load(0);
        return checkData(datas);
    }


    @Override
    protected View createSuccessView() {
        ScrollView scrollView=new ScrollView(context);
        scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Drawable pressed=UIUtils.createShape(Color.GRAY);//被按下的颜色
        for (int i = 0; i < datas.size(); i++) {
            TextView textView = new TextView(context);
            final String content=datas.get(i);
            textView.setText(content);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(18);
            Drawable normal=UIUtils.createShape(UIUtils.getColor());//正常的颜色
            StateListDrawable stateListDrawable = UIUtils.getStateListDrawable(pressed, normal);//状态选择器
            textView.setBackgroundDrawable(stateListDrawable);
            int padding = UIUtils.dip2px(context, 5);
            textView.setPadding(padding, padding, padding, padding);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
                }
            });
            linearLayout.addView(textView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        scrollView.addView(linearLayout);
        return scrollView;
    }


}
