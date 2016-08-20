package com.yetland.myfriend.view.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.yetland.myfriend.R;
import com.yetland.myfriend.core.data.ResultCodeList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogFragment extends DialogFragment {

    private static final String TAG = "CustomDialogFragment";
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.et_input)
    EditText mEtInput;
    @Bind(R.id.tv_negative)
    TextView mTvNegative;
    @Bind(R.id.tv_positive)
    TextView mTvPositive;
    @Bind(R.id.tv_error_msg)
    TextView mTvErrorMsg;
    @Bind(R.id.ll_et_layout)
    LinearLayout mLlEtLayout;

    private String mTitle = "";// 对话框标题
    private String mContent = "";//对话框内容
    private String mInput = "";// 对话框输入的内容
    private String mPositiveText = "";
    private String mNegativeText = "";
    private OnNegativeClickListener onNegativeClickListener;// 取消按钮监听器
    private OnPositiveClickListener onPositiveClickListener;// 确定按钮监听器

    private ResultCodeList.DialogType type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dailog, container, false);
        ButterKnife.bind(this, view);

        mTvTitle.setText(mTitle);
        mTvNegative.setText(mNegativeText);
        mTvPositive.setText(mPositiveText);

        switch (type) {
            case EDITTEXT:
                mLlEtLayout.setVisibility(View.VISIBLE);
                RxTextView.textChanges(mEtInput).subscribe(charSequence -> {
                    String input = charSequence.toString().replace(" ", "");

                    if (input.equals("")) {
                        mTvErrorMsg.setText("请输入数据");
                    } else {
                        mTvErrorMsg.setText("");
                    }
                });
                break;
            case TEXTVIEW:
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(mContent);
                break;
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_negative, R.id.tv_positive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_negative:
                onNegativeClickListener.OnNegativeClick();
                break;
            case R.id.tv_positive:
                onPositiveClickListener.OnPositiveClick(getInput());
                break;
        }
    }

    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    public void setOnNegativeClickListener(OnNegativeClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    public void setTitle(String title) {
        mTitle = title;

    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getInput() {
        return String.valueOf(mEtInput.getText());
    }

    public void setInput(String input) {
        mInput = input;
    }

    public void setPositiveText(String positiveText) {
        mPositiveText = positiveText;
    }

    public void setNegativeText(String negativeText) {
        mNegativeText = negativeText;
    }

    public void setType(ResultCodeList.DialogType type) {
        this.type = type;
    }

    public void showError(String errorMessage) {
        mTvErrorMsg.setText(errorMessage);
    }

    public interface OnNegativeClickListener {
        void OnNegativeClick();
    }

    public interface OnPositiveClickListener {
        void OnPositiveClick(String input);
    }
}
