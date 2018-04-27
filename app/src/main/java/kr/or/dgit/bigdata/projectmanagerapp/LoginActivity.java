package kr.or.dgit.bigdata.projectmanagerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import kr.or.dgit.bigdata.projectmanagerapp.domain.UserVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    boolean isLogin = false;// 이메일 로그인 확인용

    private JsonParserUtil<UserVO> jsonParserUtil = new JsonParserUtil() {
        @Override
        public UserVO itemParse(JSONObject order) throws JSONException{
            UserVO vo = new UserVO();

            vo.setUno(order.getInt("uno"));
            vo.setEmail(order.getString("email"));
            vo.setFirstName(order.getString("firstName"));
            vo.setLastName(order.getString("lastName"));
            vo.setGrade(order.getInt("grade"));
            vo.setPhotoPath(order.getString("photoPath"));

            return vo;
        }
    };

    Boolean isLogout;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    String result = (String)msg.obj;
                    hideProgressDialog();
                    if(!result.equals("")){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        UserVO vo =  jsonParserUtil.parsingJson(result);
                        Log.d(TAG,vo.toString());

                        intent.putExtra("userVo",vo);
                        startActivity(intent);

                        LoginActivity.this.finish();
                    }else{
                        if(!isLogin){
                        Intent intent = new Intent(LoginActivity.this,JoinFormActivity.class);
                        startActivity(intent);
                        }
                    }

                    break;
                }
                case 1: {
                    String result = (String)msg.obj;
                    hideProgressDialog();
                    if(!result.equals("")){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        UserVO vo =  jsonParserUtil.parsingJson(result);
                        Log.d(TAG,vo.toString());

                        intent.putExtra("userVo",vo);
                        startActivity(intent);

                        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.putBoolean("isLogin",true);

                        Log.d(TAG,"add email"+vo.getEmail());
                        editor.putString("email",vo.getEmail());
                        editor.commit();

                        LoginActivity.this.finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
// Views

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.join_button).setOnClickListener(this);

        //이메일 로그인 여부 확인
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);


        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        Log.d(TAG,R.string.default_web_client_id+" -- GoogleActivity");
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mAuth.signOut();
        //로그아웃 시 로그인 연결 끊기
        isLogout = getIntent().getBooleanExtra("logout",false);

        if(isLogout){
            revokeAccess();
            removePreferences();
        }

        isLogin = pref.getBoolean("isLogin",false);
        Log.d(TAG, isLogin+"");
        if(isLogin){
            String email = pref.getString("email","");
            Log.d(TAG,"email"+email);
            HttpRequestTask mHttpRequestTask = new HttpRequestTask(this ,"POST",email,handler,0);
            mHttpRequestTask.execute("http://192.168.0.50:8080/projectManager/register/googleLogin");
        }

    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            String email = user.getEmail();

            HttpRequestTask mHttpRequestTask = new HttpRequestTask(this ,"POST",email,handler,0);
            mHttpRequestTask.execute("http://192.168.0.50:8080/projectManager/register/googleLogin");
        } else {
            if(isLogout){
                Toast.makeText(this, "로그 아웃하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        } else if (i == R.id.email_sign_in_button) {
            Log.d(TAG,"email_sign_in_button click");

            EditText email = findViewById(R.id.email);
            EditText password = findViewById(R.id.password);

            String query = "";
            JSONObject order = new JSONObject();
            try {
                order.put("email",email.getText().toString());
                order.put("password",password.getText().toString());
                query = order.toString();
                Log.d(TAG,query);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showProgressDialog();
            HttpRequestTask mHttpRequestTask = new HttpRequestTask(LoginActivity.this ,"POST",query,handler,1);
            mHttpRequestTask.execute("http://192.168.0.50:8080/projectManager/register/emailLogin");

        }else if(v.getId() == R.id.join_button){
            Intent intent = new Intent(LoginActivity.this, JoinEmailActivity.class);
            startActivity(intent);
        }
    }

    private void removePreferences(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
