package com.project.xetnghiem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.ResultHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.services.ResultService;
import com.project.xetnghiem.models.Result;
import com.project.xetnghiem.models.ResultView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ResultActivity extends BaseActivity {
    private ListView listView;
    private ResultHeaderAdapter adapter;
    private List<ResultView> listResultView;
    private List<Result> listResult;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMainTitle() {
        return "Kết quả xét nghiệm";
    }

    @Override
    public void bindView() {
        listView = findViewById(R.id.listview_result);
        adapter = new ResultHeaderAdapter(this, listResultView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void callDataResource() {
        showLoading();

        APIServiceManager.getService(ResultService.class)
                .getPatientResult(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<Result>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<Result> responseResponse) {

                    }
                });

    }

    private void convertToListResultView(List<Result> list) {
        if (listResultView == null) {
            listResultView = new ArrayList<>();
        }
        for (Result result : list) {
//            for()
        }
    }
    @Override
    public void updateUIData(Object obj) {

    }
}
