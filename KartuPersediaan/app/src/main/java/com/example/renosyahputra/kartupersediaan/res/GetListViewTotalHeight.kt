package com.example.renosyahputra.quicktrans.ui

import android.view.View
import android.widget.ListView



class GetListViewTotalHeight{
    companion object {
        fun getListViewTotalHeight(listView: ListView): Int {
            val mAdapter = listView.getAdapter()

            var totalHeight = 0

            for (i in 0 until mAdapter.getCount()) {
                val mView = mAdapter.getView(i, null, listView)

                mView.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

                totalHeight += mView.getMeasuredHeight()


            }

            val params = listView.getLayoutParams()
            params.height = totalHeight + listView.getDividerHeight() * (mAdapter.getCount() - 1)
            listView.setLayoutParams(params)
            listView.requestLayout()

            return totalHeight
        }
    }
}