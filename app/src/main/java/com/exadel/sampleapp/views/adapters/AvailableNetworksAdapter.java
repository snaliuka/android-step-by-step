package com.exadel.sampleapp.views.adapters;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exadel.sampleapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvailableNetworksAdapter extends RecyclerView.Adapter<AvailableNetworksAdapter.NetworkViewHolder> {

    private final LayoutInflater inflater;
    private List<ScanResult> networks;
    private ItemSelectedListener listener;
    private String connectedNetworkSSID = "";

    public AvailableNetworksAdapter(Context context, List<ScanResult> networks) {
        this.networks = new ArrayList<>(networks);
        sortNetworks(this.networks);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public NetworkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.layout_available_wireless_network, parent, false);
        return new NetworkViewHolder(item);
    }

    @Override
    public void onBindViewHolder(NetworkViewHolder holder, int position) {
        final ScanResult item = networks.get(position);
        holder.updateSSID(item);
        holder.updateLevelView(item);
        holder.updateAccessibilityView(item);
        holder.updateStateView(item, connectedNetworkSSID);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemSelected(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public void updateDataList(Collection<ScanResult> newData) {
        Set<String> ssids = new HashSet<>();
        List<ScanResult> newNetworks = new ArrayList<>();
        for (ScanResult item : newData) {
            if (TextUtils.isEmpty(item.SSID) || ssids.contains(item.SSID)) {
                continue;
            }
            ssids.add(item.SSID);
            newNetworks.add(item);
        }
        this.networks = newNetworks;
        sortNetworks(this.networks);
        notifyDataSetChanged();
    }

    private void sortNetworks(List<ScanResult> newNetworks) {
        Collections.sort(newNetworks, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult o1, ScanResult o2) {
                return o1.SSID.compareTo(o2.SSID);
            }
        });
    }

    public void setConnectedNetworkSSID(String connectedNetworkSSID) {
        this.connectedNetworkSSID = connectedNetworkSSID == null ? "" : connectedNetworkSSID;
    }

    static class NetworkViewHolder extends RecyclerView.ViewHolder {

        private TextView ssidView;
        private TextView connectionStateView;
        private ImageView networkAccessibilityView;
        private ImageView levelView;

        NetworkViewHolder(View itemView) {
            super(itemView);
            ssidView = (TextView) itemView.findViewById(R.id.tv_network_ssid);
            networkAccessibilityView = (ImageView) itemView.findViewById(R.id.iv_network_accessibility);
            levelView = (ImageView) itemView.findViewById(R.id.iv_wireless_signal_level);
            connectionStateView = (TextView) itemView.findViewById(R.id.tv_state);
        }

        void updateLevelView(ScanResult result) {
            int drawableId;
            final int numLevels = 5;
            final int level = WifiManager.calculateSignalLevel(result.level, numLevels);
            switch (level) {
                case 4: drawableId = R.drawable.wireless_level_4; break;
                case 3: drawableId = R.drawable.wireless_level_3; break;
                case 2: drawableId = R.drawable.wireless_level_2; break;
                case 1: drawableId = R.drawable.wireless_level_1; break;
                default:
                case 0: drawableId = R.drawable.wireless_level_0; break;
            }
            levelView.setImageDrawable(ContextCompat.getDrawable(levelView.getContext(), drawableId));
        }

        void updateAccessibilityView(ScanResult result) {
//            boolean opened = WifiUtils.isOpenNetwork(result);
//            int visibility = opened ? View.INVISIBLE : View.VISIBLE;
            networkAccessibilityView.setVisibility(View.INVISIBLE);
        }

        void updateSSID(ScanResult result) {
            ssidView.setText(result.SSID);
        }

        void updateStateView(ScanResult result, String connectedState) {
            if (result.SSID.equals(connectedState) ||
                    connectedState.equals(String.format("\"%s\"", result.SSID))) {
                this.connectionStateView.setVisibility(View.VISIBLE);
            } else {
                this.connectionStateView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface ItemSelectedListener {

        void onItemSelected(ScanResult selectedNetwork);
    }
}
