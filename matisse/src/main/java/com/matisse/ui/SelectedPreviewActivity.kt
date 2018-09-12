package com.matisse.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.matisse.entity.ConstValue
import com.matisse.entity.ConstValue.EXTRA_DEFAULT_BUNDLE
import com.matisse.entity.ConstValue.REQUEST_CODE_PREVIEW
import com.matisse.entity.Item
import com.matisse.internal.entity.SelectionSpec
import com.matisse.model.SelectedItemCollection
import com.matisse.ui.view.BasePreviewActivity

/**
 * Created by liubo on 2018/9/11.
 */
class SelectedPreviewActivity : BasePreviewActivity() {

    companion object {
        fun instance(context: Context, bundle: Bundle, mOriginalEnable: Boolean) {
            val intent = Intent(context, SelectedPreviewActivity::class.java)
            intent.putExtra(ConstValue.EXTRA_DEFAULT_BUNDLE, bundle)
            intent.putExtra(ConstValue.EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable)
            (context as Activity).startActivityForResult(intent, REQUEST_CODE_PREVIEW)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SelectionSpec.getInstance().hasInited) {
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }
        val bundle = intent.getBundleExtra(EXTRA_DEFAULT_BUNDLE)
        val selected = bundle.getParcelableArrayList<Item>(SelectedItemCollection.STATE_SELECTION)
        mAdapter?.addAll(selected)
        mAdapter?.notifyDataSetChanged()
        mCheckView?.apply {
            if (mSpec!!.countable) {
                setCheckedNum(1)
            } else {
                setChecked(true)
            }
        }
        mPreviousPos = 0
        updateSize(selected[0])
    }
}