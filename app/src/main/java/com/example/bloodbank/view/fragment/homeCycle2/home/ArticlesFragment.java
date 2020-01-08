package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.example.bloodbank.view.fragment.userCycle.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.utils.HelperMethod.replaceFragment;

public class ArticlesFragment extends BaSeFragment {


    @BindView(R.id.home_articles_categories_id_spinner)
    Spinner homeArticlesCategoriesIdSpinner;
    @BindView(R.id.home_articles_categories_etxt)
    EditText homeArticlesCategoriesEtxt;
    @BindView(R.id.home_articles_recycler_view)
    RecyclerView homeArticlesRecyclerView;
    @BindView(R.id.home_articles_add_floating_action_button)
    FloatingActionButton homeArticlesAddFloatingActionButton;
    private BottomNavigationView navBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_articles, container, false);
        ButterKnife.bind(this, root);
        navBar = getActivity().findViewById(R.id.nav_view);

        return root;
    }

    @OnClick({R.id.home_articles_categories_etxt, R.id.home_articles_add_floating_action_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_articles_categories_etxt:
                break;
            case R.id.home_articles_add_floating_action_button:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new AddDonationRequestFragment());
                navBar.setVisibility(View.GONE);

                break;
        }
    }
}