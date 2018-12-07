package com.mayank.drawerlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class Drawer extends RelativeLayout implements View.OnTouchListener{
    public static final float CLICK_DRAG_TOLERANCE = 10;
    private float downX, downY, dX, drawerWidth;
    private View bg = null;
    private float alphaMax = 1;
    private int scrollDirection = 0, direction = 1, animDuration = 300;
    final public static int Horizontal = 1, Vertical = 2;
    private float dp10;
    private Context context;

    public Drawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Point size = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
        float size_x = size.x;
        dp10 = getResources().getDimensionPixelSize(R.dimen.dp10);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Drawer, 0, 0);
        drawerWidth = 30*dp10;
        Drawable background;
        try {
            direction = a.getBoolean(R.styleable.Drawer_onRightEdge, false)?-1:1;
            drawerWidth = a.getDimension(R.styleable.Drawer_drawerWidth, 30*dp10);
            background = a.getDrawable(R.styleable.Drawer_drawerBackground);
        } finally {
            a.recycle();
        }
        if(Math.abs(getPaddingRight()-getPaddingLeft())<2*dp10){
            if(direction == 1)
                setPadding(getPaddingLeft(), getPaddingTop(), (int)(getPaddingRight() + size_x -drawerWidth), getPaddingBottom());
            else
                setPadding((int)(getPaddingLeft() + size_x -drawerWidth), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        } else {
            if(direction == 1)
                drawerWidth = size_x - getPaddingRight();
            else
                drawerWidth = size_x - getPaddingLeft();
        }
        View view = new View(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)(300*dp10)));
        view.setBackgroundColor(Color.rgb(240,240,240));
        view.setBackground(background);
        setBackgroundColor(Color.argb(0,0,0,0));
        addView(view, 0);
        setOnTouchListener(this);
        if(bg == null) bg = (View) getParent();
        collapse();
    }


    public void setDrawerWidth(float drawerWidth) {
        this.drawerWidth = drawerWidth;
    }

    public float getDrawerWidth() {
        return drawerWidth;
    }

    public void setShadowingBackground(View view){
        bg = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            scrollDirection = 0;
            downX = ev.getRawX();
            downY = ev.getRawY();
            dX = this.getX() - downX;
            peep();
        } else if(action == MotionEvent.ACTION_MOVE){
            if(scrollDirection == 0) {
                scrollDirection = Math.abs(ev.getRawX() - downX) > CLICK_DRAG_TOLERANCE ? Horizontal
                        : Math.abs(ev.getRawY() - downY) > CLICK_DRAG_TOLERANCE ? Vertical : 0;
            } else if(scrollDirection == Horizontal){
                return onTouch(this, ev);
            }
        } else if(action == MotionEvent.ACTION_UP && scrollDirection != 0 && scrollDirection != Vertical)
            return onTouch(this, ev);
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent ev) {
        int action = ev.getAction();
        if(bg == null) bg = (View) getParent();
        if(action == MotionEvent.ACTION_DOWN){
            downX = ev.getRawX();
            dX = this.getX() - downX;
            peep();
        } else if(action == MotionEvent.ACTION_MOVE){
            float newX = ev.getRawX() + dX;
            newX = direction*Math.min(0, direction*newX);
            view.animate().x(newX).setDuration(0).start();
            if(bg!=null) bg.animate().alpha(alphaMax + direction*newX/drawerWidth).setDuration(0).start();
        } else if(action == MotionEvent.ACTION_UP) {
            float upRawX = ev.getRawX();
            if(direction==1? downX >drawerWidth : downX < getWidth() - drawerWidth) collapse();
            else if (direction*(upRawX- downX) > 10*CLICK_DRAG_TOLERANCE) expand();
            else if(direction*(downX -upRawX) > 10*CLICK_DRAG_TOLERANCE) collapse();
            else if(Math.abs(this.getX()) > drawerWidth) collapse();
            else expand();
        }
        return true;
    }

    public void collapse(){
        if(bg!=null) bg.animate().alpha(0).setDuration(animDuration).start();
        int x = getWidth();
        if(x == 0){
            Point size = new Point();
            ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
            x = size.x;
        }
        if(direction == -1)
            this.animate().x(x - dp10).setDuration(animDuration).start();
        else {
            this.animate().x(-x + dp10).setDuration(animDuration).start();
        }
    }

    public void disableDrawer(){
        this.animate().x(-this.getWidth()).setDuration(0).start();
        if(bg!=null) bg.animate().alpha(0).setDuration(0).start();
    }

    public void peep(){
        if(this.getX()!=0) {
            float newX = direction*(-drawerWidth + 2 * dp10);
            this.animate().x(newX).setDuration(animDuration).start();
            if(bg!=null) bg.animate().alpha(alphaMax + direction*newX/drawerWidth).setDuration(animDuration).start();
            dX += direction*(getWidth() - drawerWidth + 2 * dp10);
        }
    }

    public void expand(){
        this.animate().x(0).setDuration(animDuration).start();
        if(bg!=null) bg.animate().alpha(alphaMax).setDuration(animDuration).start();
    }

    public boolean isCollapsed() {
        return this.getX()!=0;
    }
}