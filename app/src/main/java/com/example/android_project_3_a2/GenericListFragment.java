package com.example.android_project_3_a2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.List;

public class GenericListFragment extends Fragment {

    public static final String ARG_LIST_TYPE = "list_type";
    private String listType; // "attractions" or "restaurants"
    private SelectionViewModel viewModel;

    private MyListAdapter adapter;

    public GenericListFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance with list type parameter
    public static GenericListFragment newInstance(String listType) {
        GenericListFragment fragment = new GenericListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        viewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        if (getArguments() != null) {
            listType = getArguments().getString(ARG_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment_list layout containing the RecyclerView
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Item> items;
        if ("attractions".equalsIgnoreCase(listType)) {
            items = Arrays.asList(
                    new Item("Lincoln Park Zoo", "https://www.lpzoo.org"),
                    new Item("Navy Pier", "https://navypier.org"),
                    new Item("Field Museum", "https://www.fieldmuseum.org/"),
                    new Item("Art Institute", "https://www.artic.edu"),
                    new Item("TILT!", "https://360chicago.com/tilt")
            );
        } else if ("restaurants".equalsIgnoreCase(listType)) {
            items = Arrays.asList(
                    new Item("Miss Ricky's", "https://virginhotels.com/chicago/eat-drink/miss-rickys/"),
                    new Item("The Gage", "https://thegagechicago.com/"),
                    new Item("London House Chicago", "https://londonhousechicago.com/"),
                    new Item("La Grande Boucherie", "https://www.boucherieus.com/location/chicago/"),
                    new Item("Terrace 16", "https://www.trumphotels.com/chicago/dining/fine-dining-chicago")
            );
        } else {
            // Fallback to an empty list if no valid type is provided
            items = List.of();
        }

        adapter = new MyListAdapter(items, new MyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item, int position) {
                viewModel.selectUrl(item.getUrl());
                viewModel.setSelectedPosition(position);
            }
        });
        recyclerView.setAdapter(adapter);

        // Observe the selected position from the ViewModel
        viewModel.getSelectedPosition().observe(getViewLifecycleOwner(), pos -> {
            if (pos != null) {
                adapter.setSelectedPosition(pos);
            }
        });



    }

    //method to clear the selection highlight in the list.
    public void clearSelection() {
        if (adapter != null) {
            adapter.setSelectedPosition(RecyclerView.NO_POSITION);
        }
    }
}
