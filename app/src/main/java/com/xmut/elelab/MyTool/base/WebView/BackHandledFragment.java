package com.xmut.elelab.MyTool.base.WebView;

import android.os.Bundle;

import com.xmut.elelab.MyTool.base.BaseFragment;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/4/16 3:34
 */
public abstract class BackHandledFragment extends BaseFragment {

    protected BackHandledInterface mBackHandledInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }
}
