package com.bignerdranch.android.learning.reneroost.biitpoks;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.learning.reneroost.biitpoks.databinding.FragmentBiitPoksBinding;
import com.bignerdranch.android.learning.reneroost.biitpoks.databinding.NimekirjaUksusHeliBinding;

import java.util.List;

public class BiitPoksFragment extends Fragment {

    BiitPoks biitPoks;

    public static BiitPoksFragment uusInstants() {
        return new BiitPoksFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biitPoks = new BiitPoks(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater taispuhuja, ViewGroup konteiner, Bundle savedInstanceState) {
        FragmentBiitPoksBinding sidumine = DataBindingUtil.inflate(taispuhuja, R.layout.fragment_biit_poks,
                konteiner, false);

        sidumine.taaskasutajaVaade.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        sidumine.taaskasutajaVaade.setAdapter(new HeliAdapter(biitPoks.saaHelid()));

        return sidumine.getRoot();
    }

    private class HeliHoidja extends RecyclerView.ViewHolder {
        private NimekirjaUksusHeliBinding sidumine;

        private HeliHoidja(NimekirjaUksusHeliBinding sidumine) {
            super(sidumine.getRoot());
            this.sidumine = sidumine;
            sidumine.setViewModel(new HeliVaateMudel(biitPoks));
        }

        public void bind(Heli heli) {
            sidumine.getViewModel().maaraHeli(heli);
            sidumine.executePendingBindings();
        }
    }

    private class HeliAdapter extends RecyclerView.Adapter<HeliHoidja> {
        private List<Heli> helid;

        public HeliAdapter(List<Heli> helid) {
            this.helid = helid;
        }

        @NonNull
        @Override
        public HeliHoidja onCreateViewHolder(@NonNull ViewGroup vanem, int i) {
            LayoutInflater taispuhuja = LayoutInflater.from(getActivity());
            NimekirjaUksusHeliBinding sidumine = DataBindingUtil
                    .inflate(taispuhuja, R.layout.nimekirja_uksus_heli, vanem, false);
            return new HeliHoidja(sidumine);
        }

        @Override
        public void onBindViewHolder(@NonNull HeliHoidja heliHoidja, int positsioon) {
            Heli heli = helid.get(positsioon);
            heliHoidja.bind(heli);
        }

        @Override
        public int getItemCount() {
            return helid.size();
        }
    }
}
