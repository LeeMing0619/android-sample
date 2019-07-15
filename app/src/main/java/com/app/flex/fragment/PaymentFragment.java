package com.app.flex.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.flex.EditPaymentActivity;
import com.app.flex.PayLaterActivity;
import com.app.flex.R;
import com.app.flex.YourRideActivity;

public class PaymentFragment extends Fragment
{

	private CardView mVisaCard;
	private CardView mMasterCard;
	private CardView mPaypalCard;
	private Button mAddPaymentBtn, mPayLaterBtn, mAddCreditCardBtn;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View rootview=inflater.inflate(R.layout.fragment_payment,null);

		mVisaCard=rootview.findViewById(R.id.visa_card);
		mMasterCard=rootview.findViewById(R.id.master_card);
		mPaypalCard=rootview.findViewById(R.id.paypal_card);
		mAddPaymentBtn = rootview.findViewById(R.id.add_payment_btn);
		mPayLaterBtn = rootview.findViewById(R.id.pay_later_btn);
		mAddCreditCardBtn = rootview.findViewById(R.id.add_credit_card_btn);

		mAddPaymentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_edit_payment = new Intent(getActivity(), EditPaymentActivity.class);
				startActivity(intent_edit_payment);
			}
		});

		mPayLaterBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_pay_later = new Intent(getActivity(), PayLaterActivity.class);
				startActivity(intent_pay_later);
			}
		});

		mAddCreditCardBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddCreditCardFragment someFragment = new AddCreditCardFragment();
				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		return rootview;
	}
}
