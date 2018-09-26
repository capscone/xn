package com.project.xetnghiem.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.project.xetnghiem.R;
import com.project.xetnghiem.api.responseObj.ErrorResponse;
import com.project.xetnghiem.utilities.AppConst;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.Utils;

import java.io.IOException;
import java.net.InetAddress;

import okhttp3.ResponseBody;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getMainTitle());
        showMessNetword();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public abstract String getMainTitle();


    public void showDialog(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Thử lại", (DialogInterface dialogInterface, int i) -> {
                });
        alertDialog.show();
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG ).show();
    }

    public void showWarningMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG ).show();
    }


    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.waiting_msg));
            progressDialog.setOnCancelListener((DialogInterface dialog) -> {
                showMessage(getString(R.string.dialog_cancel));
                onCancelLoading();
                hideLoading();
            });
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public  void onCancelLoading(){
        showMessage(getString(R.string.dialog_cancel));
    }

    public void logError(String activity, String method, String message) {
        Log.e(AppConst.DEBUG_TAG, activity + "." + method + "(): " + message);
    }

    public void logError(Class t, String method, String message) {
        Log.e(AppConst.DEBUG_TAG, t.getSimpleName() + "." + method + "(): " + message);
    }

    public void logError(String method, String message) {
        Log.e(AppConst.DEBUG_TAG, "Activity" + this.getClass().getSimpleName() + method + "(): " + message);
//        Log.e(AppConst.DEBUG_TAG, this.getClass().getSimpleName() + "." + method + "(): " + message);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public void showMessNetword() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);
        if (!is3g && !isWifi) {
            showWarningMessage("Vui lòng kiểm tra kết nối mạng của bạn đã được bật.");
        }
    }
    public void showFatalError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            showErrorMessage("Lỗi server");
            try {
                String error = errorBody.string();
                logError("showFatalError: " + method, error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logError("showFatalError: " + method, "errorBody is null");
        }
    }

    public void showErrorUnAuth() {
//        CoreManager.clearStaff(this);
//        showErrorMessage("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại");
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    public void showBadRequestError(ResponseBody errorBody, String method) {
        if (errorBody != null) {
            try {
                String error = errorBody.string();
                ErrorResponse errorResponse = Utils.parseJson(error, ErrorResponse.class);
                showErrorMessage(errorResponse.getErrorMessage());
                logError(method, errorResponse.getExceptionMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
