package com.project.xetnghiem.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.activities.BookApptActivity;
import com.project.xetnghiem.adapter.SampleHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.services.SampleService;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookStep1Fragment extends BaseFragment {

    View mainView;
    private ListView listViewSamle;
    private SampleHeaderAdapter adapter;
    private List<LabTest> listLabTest;
    private List<LabTest> tmpLabTest;
    private List<SampleDto> listTmpSampleDto;
    private DataListener dataListener = null;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_book_step1, container, false);
        bindView();
        if (listLabTest == null) {
            listLabTest = new ArrayList<>();
        }
        dataListener = (DataListener) getActivity();
        adapter = new SampleHeaderAdapter(getContext(), listLabTest, new SampleHeaderAdapter.OnChangeChkListener() {
            @Override
            public void onChange(int id, boolean status) {
                if (status) {
                    tmpLabTest.add(getLabtest(id));
                } else {
                    tmpLabTest.remove(findLabtestPos(id));

                }
                filterSampleDto();
                dataListener.onDateReceiver(tmpLabTest, listTmpSampleDto);
            }
        });
        if (tmpLabTest == null) {
            tmpLabTest = new ArrayList<>();
        }
        if (listTmpSampleDto == null) {
            listTmpSampleDto = new ArrayList<>();
        }
        listViewSamle.setAdapter(adapter);

        return mainView;
    }

    private LabTest getLabtest(int id) {
        for (LabTest t : listLabTest) {
            if (t.getLabTestId() == id) {
                return t;
            }
        }
        return null;
    }

    private int findLabtestPos(int id) {
        for (int i = 0; i < tmpLabTest.size(); i++) {
            if (tmpLabTest.get(i).getLabTestId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void filterSampleDto() {
        SampleDto dto = null;
        listTmpSampleDto.clear();
        for (LabTest labTest : tmpLabTest) {
            if ((dto = isInSampleList(labTest)) != null) {
                if (!isDtoExist(dto)) {
                    listTmpSampleDto.add(dto);
                }
            }
        }
    }

    public boolean isDtoExist(SampleDto dto) {
        for (SampleDto tmp : listTmpSampleDto) {
            if (dto.getSampleId() == tmp.getSampleId()) {
                return true;
            }
        }
        return false;
    }

    public SampleDto isInSampleList(LabTest labTest) {
        for (SampleDto dto : listSampleDto) {
            for (LabTest labTest1 : dto.getLabTests()) {
                if (labTest.getLabTestId() == labTest1.getLabTestId()) {
                    return dto;
                }
            }
        }
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callDataResource();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void bindView() {
        listViewSamle = mainView.findViewById(R.id.listview_sample);

    }

    private Disposable apiDisposable;
    private List<SampleDto> listSampleDto;

    @Override
    protected void callDataResource() {
        SampleService sampleService = APIServiceManager.getService(SampleService.class);
        sampleService.getAllSample().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<SampleDto>>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiDisposable = d;
                    }

                    @Override
                    protected void onResponseSuccess(Response<List<SampleDto>> sampleDtoResponse) {
                        List<SampleDto> lst = sampleDtoResponse.body();
                        if (listSampleDto == null) {
                            listSampleDto = new ArrayList<>();
                        }
                        listSampleDto.clear();
                        listSampleDto.addAll(sampleDtoResponse.body());
                        listLabTest.clear();
                        for (SampleDto dto : lst) {
                            String sampleName = dto.getSampleName();
                            listLabTest.add(new LabTest(true, sampleName));
                            for (LabTest labTest : dto.getLabTests()) {
                                labTest.setSampleName(sampleName);
                                labTest.setHeader(false);
                                listLabTest.add(labTest);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        int a = 1;
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (apiDisposable != null) {
            apiDisposable.dispose();
        }
    }

    @Override
    public void updateUIData(Object obj) {

    }

    public interface DataListener {
        void onDateReceiver(List<LabTest> data, List<SampleDto> listSampleDto);
    }
}