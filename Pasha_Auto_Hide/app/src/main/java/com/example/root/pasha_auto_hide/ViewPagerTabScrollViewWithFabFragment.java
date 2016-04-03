package com.example.root.pasha_auto_hide;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

//import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
//import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
//import com.github.ksoichiro.android.observablescrollview.ScrollState;
//import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * This example shows how to handle scroll events on both the parent Activity and Fragments.
 * (Handling FAB is not the main purpose)
 */
public class ViewPagerTabScrollViewWithFabFragment extends BaseFragment implements ObservableScrollViewCallbacks {

    public static final String ARG_SCROLL_Y = "ARG_SCROLL_Y";
    private View mFab;
    private int mFabMargin;
    private boolean mFabIsShown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_view_pager_tab_scroll_view_with_fab_fragment, container, false);
//        mFab = view.findViewById(R.id.fab);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        mFabIsShown = true;

        // Translate FAB
        ScrollUtils.addOnGlobalLayoutListener(mFab, new Runnable() {
            @Override
            public void run() {
                float fabTranslationY = view.getHeight() - mFabMargin - mFab.getHeight();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
                    // which causes FAB's OnClickListener not working.
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
                    lp.leftMargin = view.getWidth() - mFabMargin - mFab.getWidth();
                    lp.topMargin = (int) fabTranslationY;
                    mFab.requestLayout();
                } else {
                    ViewHelper.setTranslationX(mFab, view.getWidth() - mFabMargin - mFab.getWidth());
                    ViewHelper.setTranslationY(mFab, fabTranslationY);
                }
            }
        });

        final ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
        Activity parentActivity = getActivity();
        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            // Scroll to the specified offset after layout
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARG_SCROLL_Y)) {
                final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
                ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, scrollY);
                    }
                });
            }

            // TouchInterceptionViewGroup should be a parent view other than ViewPager.
            // This is a workaround for the issue #117:
            // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
            scrollView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.root));
        }
        scrollView.setScrollViewCallbacks(this);

        return view;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (getActivity() != null && getActivity() instanceof ObservableScrollViewCallbacks) {
            ((ObservableScrollViewCallbacks) getActivity()).onScrollChanged(scrollY, firstScroll, dragging);
        }
    }

    @Override
    public void onDownMotionEvent() {
        if (getActivity() != null && getActivity() instanceof ObservableScrollViewCallbacks) {
            ((ObservableScrollViewCallbacks) getActivity()).onDownMotionEvent();
        }
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (getActivity() != null && getActivity() instanceof ObservableScrollViewCallbacks) {
            ((ObservableScrollViewCallbacks) getActivity()).onUpOrCancelMotionEvent(scrollState);
        }

        if (scrollState == ScrollState.UP) {
            hideFab();
        } else if (scrollState == ScrollState.DOWN) {
            showFab();
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }
}
