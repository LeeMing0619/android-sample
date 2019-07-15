package com.app.flex.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.flex.R;
import com.app.flex.YourRideActivity;
import com.app.flex.maputils.DrawRouteMaps;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {


	GoogleMap mMap;
	GoogleApiClient mGoogleApiClient;
	LocationRequest mLocationRequest;
	Location mLastLocation;
	Marker mCurrLocationMarker;
	Marker mDestinationMarker;
	ImageView car_card_float, car_one_img, car_two_img, car_three_img;
	FrameLayout card_frame, from_location_frame, to_location_frame;
	ImageView close_card_btn, to_location_icon;
	LinearLayout from_icons_card, from_location_linear2, to_location_linear, car_card_one_li, car_card_two_li, car_card_three_li;

	EditText from_location_ed, to_location_ed2;
	TextView from_location_ed2, to_location_ed, car_one_txt, car_two_txt, car_three_txt;
	 View view_card1, view_card2, view_card3;


	InputMethodManager imm;
	int PLACE_PICKER_REQUEST = 1;
	int PLACE_PICKER_REQUEST_2 = 2;
	List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS);
	Button request_ride_btn;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_home, null);

		PlacesClient placesClient = Places.createClient(getActivity());


		request_ride_btn=rootview.findViewById(R.id.request_ride_btn);


		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
		mapFragment.getMapAsync(this);

		mLocationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(1000)
				.setFastestInterval(1000);

		car_card_float = rootview.findViewById(R.id.car_card_float);
		card_frame = rootview.findViewById(R.id.card_frame);
		close_card_btn = rootview.findViewById(R.id.close_card_btn);
		from_icons_card = rootview.findViewById(R.id.from_icons_card);
		from_location_linear2 = rootview.findViewById(R.id.from_location_linear2);
		to_location_linear = rootview.findViewById(R.id.to_location_linear);
		to_location_ed = rootview.findViewById(R.id.to_location_ed);
		from_location_ed = rootview.findViewById(R.id.from_location_ed);
		to_location_ed2 = rootview.findViewById(R.id.to_location_ed2);
		from_location_ed2 = rootview.findViewById(R.id.from_location_ed2);

		from_location_frame = rootview.findViewById(R.id.from_location_frame);
		to_location_frame = rootview.findViewById(R.id.to_location_frame);

		view_card1 = rootview.findViewById(R.id.view_card1);
		view_card2 = rootview.findViewById(R.id.view_card2);
		view_card3 = rootview.findViewById(R.id.view_card3);

		car_card_one_li = rootview.findViewById(R.id.car_card_one_li);
		car_card_two_li = rootview.findViewById(R.id.car_card_two_li);
		car_card_three_li = rootview.findViewById(R.id.car_card_three_li);

		car_one_img = rootview.findViewById(R.id.car_one_img);
		car_two_img = rootview.findViewById(R.id.car_two_img);
		car_three_img = rootview.findViewById(R.id.car_three_img);

		car_one_txt = rootview.findViewById(R.id.car_one_txt);
		car_two_txt = rootview.findViewById(R.id.car_two_txt);
		car_three_txt = rootview.findViewById(R.id.car_three_txt);

		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		to_location_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				to_location_frame.setVisibility(View.VISIBLE);
				from_location_frame.setVisibility(View.INVISIBLE);
				from_location_ed2.setText(from_location_ed.getText().toString());
				to_location_ed2.requestFocus();
				imm.showSoftInput(to_location_ed2, InputMethodManager.SHOW_IMPLICIT);
			}
		});

		from_location_linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				to_location_frame.setVisibility(View.INVISIBLE);
				from_location_frame.setVisibility(View.VISIBLE);
				to_location_ed.setText(to_location_ed2.getText().toString());
				from_location_ed.requestFocus();
				imm.showSoftInput(from_location_ed, InputMethodManager.SHOW_IMPLICIT);
			}
		});


		close_card_btn.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("RestrictedApi")
			@Override
			public void onClick(View v) {
				car_card_float.setVisibility(View.VISIBLE);
				close_card_btn.setVisibility(View.GONE);
				card_frame.setVisibility(View.GONE);
			}
		});

		car_card_float.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("RestrictedApi")
			@Override
			public void onClick(View v) {
				car_card_float.setVisibility(View.GONE);
				close_card_btn.setVisibility(View.VISIBLE);
				card_frame.setVisibility(View.VISIBLE);

			}
		});

		CardView li_stand = rootview.findViewById(R.id.li_stand);
		li_stand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		from_location_ed.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Autocomplete.IntentBuilder(
						AutocompleteActivityMode.FULLSCREEN, fields)
						.build(getActivity());
				startActivityForResult(intent, PLACE_PICKER_REQUEST);


			}
		});


		to_location_ed2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Autocomplete.IntentBuilder(
						AutocompleteActivityMode.FULLSCREEN, fields)
						.build(getActivity());
				startActivityForResult(intent, PLACE_PICKER_REQUEST_2);


			}
		});

		request_ride_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_yourRide = new Intent(getActivity(), YourRideActivity.class);
				startActivity(intent_yourRide);
			}
		});


		getCardVisibility();
		car_card_one_li.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCardVisibility();
			}
		});

		car_card_two_li.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				car_two_img.setImageResource(R.drawable.ic_confort_selected);
				car_two_txt.setTextColor(getResources().getColor(R.color.black));
				view_card2.setVisibility(View.VISIBLE);

				car_three_img.setImageResource(R.drawable.ic_elite_inselected);
				car_three_txt.setTextColor(getResources().getColor(R.color.light_gray));
				view_card3.setVisibility(View.INVISIBLE);

				car_one_img.setImageResource(R.drawable.ic_standard_inselected);
				car_one_txt.setTextColor(getResources().getColor(R.color.light_gray));
				view_card1.setVisibility(View.INVISIBLE);
			}
		});

		car_card_three_li.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				car_two_img.setImageResource(R.drawable.ic_confort_unselected);
				car_two_txt.setTextColor(getResources().getColor(R.color.light_gray));
				view_card2.setVisibility(View.INVISIBLE);

				car_three_img.setImageResource(R.drawable.ic_elite_selected);
				car_three_txt.setTextColor(getResources().getColor(R.color.black));
				view_card3.setVisibility(View.VISIBLE);

				car_one_img.setImageResource(R.drawable.ic_standard_inselected);
				car_one_txt.setTextColor(getResources().getColor(R.color.light_gray));
				view_card1.setVisibility(View.INVISIBLE);
			}
		});
		return rootview;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setMyLocationButtonEnabled(false);
		mMap.getUiSettings().setCompassEnabled(false);
		mMap.clear();

		if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		mMap.setMyLocationEnabled(false);
		buildGoogleApiClient();

	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		mGoogleApiClient.connect();
	}

	@Override
	public void onLocationChanged(Location location) {

		updateMarker(location);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PLACE_PICKER_REQUEST) {
			if (resultCode == RESULT_OK) {
				Place place = Autocomplete.getPlaceFromIntent(data);
				from_location_ed.setText(place.getName());

				Location targetLocation = new Location("");
				targetLocation.setLatitude(place.getLatLng().latitude);
				targetLocation.setLongitude(place.getLatLng().longitude);
				updateMarker(targetLocation);

			} else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

				Status status = Autocomplete.getStatusFromIntent(data);

			} else if (resultCode == RESULT_CANCELED) {

			}
		}else if (requestCode == PLACE_PICKER_REQUEST_2) {
			if (resultCode == RESULT_OK) {
				Place place = Autocomplete.getPlaceFromIntent(data);
				to_location_ed2.setText(place.getName());

				if (mDestinationMarker != null) {
					mMap.clear();
					updateMarker(mLastLocation);
				}

				Location targetLocation = new Location("");
				targetLocation.setLatitude(place.getLatLng().latitude);
				targetLocation.setLongitude(place.getLatLng().longitude);
				destinationMarker(targetLocation);



				DrawRouteMaps.getInstance(getActivity())
						.draw(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),new LatLng(targetLocation.getLatitude(),targetLocation.getLongitude()),  mMap);

				request_ride_btn.setVisibility(View.VISIBLE);

			} else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

				Status status = Autocomplete.getStatusFromIntent(data);

			} else if (resultCode == RESULT_CANCELED) {

			}
		}
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			return;
		}
		Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		if (location == null) {
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		} else {
			updateMarker(location);
		}


		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}

	public void updateMarker(Location location) {
		mLastLocation = location;
		if (mCurrLocationMarker != null) {
			mCurrLocationMarker.remove();
		}
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(latLng);
		markerOptions.title("Current Position");
		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location));
		mCurrLocationMarker = mMap.addMarker(markerOptions);

		//move map camera
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

		if (mGoogleApiClient != null) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
		}
	}


	public void destinationMarker(Location location) {

		if (mDestinationMarker != null) {
			mDestinationMarker.remove();
		}
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(latLng);
		markerOptions.title("Current Position");
		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
		mDestinationMarker = mMap.addMarker(markerOptions);

		//move map camera
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

		if (mGoogleApiClient != null) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
		}
	}

	public void getCardVisibility()
	{
		car_two_img.setImageResource(R.drawable.ic_elite_inselected);
		car_two_txt.setTextColor(getResources().getColor(R.color.light_gray));
		view_card2.setVisibility(View.INVISIBLE);

		car_three_img.setImageResource(R.drawable.ic_confort_unselected);
		car_three_txt.setTextColor(getResources().getColor(R.color.light_gray));
		view_card3.setVisibility(View.INVISIBLE);

		car_one_img.setImageResource(R.drawable.ic_standard_selected);
		car_one_txt.setTextColor(getResources().getColor(R.color.black));
		view_card1.setVisibility(View.VISIBLE);
	}
}
