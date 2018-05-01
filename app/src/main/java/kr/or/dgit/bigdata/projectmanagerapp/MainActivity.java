package kr.or.dgit.bigdata.projectmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.UserVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.WorkspaceVO;
import kr.or.dgit.bigdata.projectmanagerapp.fragments.ProjectInnerFragment;
import kr.or.dgit.bigdata.projectmanagerapp.fragments.WorkspaceFragment;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Observer;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    private final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private WorkspaceFragment mWorkspaceFragment;
    private ProjectInnerFragment mProjectInnerFragment;
    private FragmentManager manager;
    UserVO userVo;
    WorkspaceVO workVo;
    MemberVO memVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userVo = getIntent().getParcelableExtra("userVo");
        workVo = getIntent().getParcelableExtra("workVo");
        memVo = getIntent().getParcelableExtra("memVo");
        Log.d(TAG, userVo.toString());
        Log.d(TAG, workVo.toString());
        Log.d(TAG, memVo.toString());

        manager = getSupportFragmentManager();
        mWorkspaceFragment = new WorkspaceFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("wcode", workVo.getWcode());
        bundle.putParcelable("memVo", memVo);
        mWorkspaceFragment.setArguments(bundle);
        mWorkspaceFragment.position = new Position();
        mWorkspaceFragment.position.attach(this);


        mProjectInnerFragment = new ProjectInnerFragment();
        mProjectInnerFragment.mPosition = new Position();
        mProjectInnerFragment.mPosition.attach(this);

        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.fragment_container, mWorkspaceFragment);
        ft.commit();

        for(int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            manager.popBackStack();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.logout) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("logout", true);
                startActivity(intent);
                this.finish();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }



    @Override
    public void update() {
        int getId = mWorkspaceFragment.position.getOnClickId();
        int position = -1;
        Log.d(TAG,getId+"");
        try {
            switch (getId) {
                case R.id.project_item:
                    position = mWorkspaceFragment.position.getPosition();
                    ProjectVO vo = mWorkspaceFragment.getProjectVO(position);
                    Log.d(TAG, "position = " + position);
                    Bundle bundle = new Bundle(1);
                    bundle.putString("wcode", workVo.getWcode());
                    bundle.putParcelable("memVo", memVo);
                    bundle.putParcelable("projectVo", vo);
                    mProjectInnerFragment.setArguments(bundle);
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.add(R.id.fragment_container, mProjectInnerFragment);
                    ft.commit();
                    mWorkspaceFragment.position.resetPosition();
                    break;
                case R.id.pj_setting:
                    position = mWorkspaceFragment.position.getPosition();
                    Log.d(TAG, "position = " + position);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
