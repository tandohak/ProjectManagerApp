package kr.or.dgit.bigdata.projectmanagerapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import kr.or.dgit.bigdata.projectmanagerapp.domain.UserVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.WorkspaceVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpFileUploadTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;

public class JoinFormActivity extends BaseActivity {
    private final int GALLERY_CODE = 1112;

    private final String TAG = "JoinFormActivity";

    String imagePath = "";
    ImageView resultImageView;
    String email;
    EditText emailEdit;
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText workspaceEdit;
    String[] names;
    private LinearLayout infoFormBox;
    private LinearLayout makeWorkBox;
    String uploadImagPath = "";
    int reqHeight = 1024;
    int reqWidth = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_form);
        infoFormBox = findViewById(R.id.infoFormBox);
        makeWorkBox = findViewById(R.id.makeWorkBox);
        resultImageView = findViewById(R.id.addPhoto);
        resultImageView.setOnClickListener(photoListener);
        emailEdit = findViewById(R.id.email);
        firstNameEdit = findViewById(R.id.firstName);
        lastNameEdit = findViewById(R.id.lastName);
        workspaceEdit = findViewById(R.id.workspaceName);

        email = getIntent().getStringExtra("email");
        if (!email.equals("")) {
            emailEdit.setText(email);
            emailEdit.setEnabled(false);
        }
        String displayName = getIntent().getStringExtra("displayName");
        if (!displayName.equals("")) {
            names = displayName.split(" ");
            firstNameEdit.setText(names[1]);
            firstNameEdit.setEnabled(false);
            lastNameEdit.setText(names[0]);
            lastNameEdit.setEnabled(false);
        }

        ActivityCompat.requestPermissions(JoinFormActivity.this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                GALLERY_CODE);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String res = (String) msg.obj;
                    Log.d(TAG, res);
                    if(res != "fail"){
                        UserVO vo = new UserVO();
                        WorkspaceVO wvo = new WorkspaceVO();
                        try {
                            JSONObject order = new JSONObject(res);
                            JSONObject user  = order.getJSONObject("userVo");
                            vo.setUno(user.getInt("uno"));
                            vo.setEmail(user.getString("email"));
                            vo.setFirstName(user.getString("firstName"));
                            vo.setLastName(user.getString("lastName"));
                            vo.setGrade(user.getInt("grade"));
                            vo.setPhotoPath(user.getString("photoPath"));

                            JSONObject workspace  = order.getJSONObject("wvo");

                            wvo.setWcode(workspace.getString("wcode"));
                            wvo.setName(workspace.getString("name"));
                            wvo.setMaker(workspace.getString("maker"));
                            wvo.setUno(workspace.getInt("uno"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(JoinFormActivity.this,MainActivity.class);

                        Log.d(TAG,vo.toString());

                        intent.putExtra("userVo",vo);
                        intent.putExtra("workVo",wvo);
                        startActivity(intent);
                    }

                    hideProgressDialog();
                    break;
                case 1:
                    uploadImagPath = (String) msg.obj;
                    Log.d(TAG, (String) msg.obj);
                    registerUser(uploadImagPath);
                    break;
            }
        }
    };

    public void onClick(View v) {
        if (v.getId() == R.id.backBtn) {
            Intent intent = new Intent(JoinFormActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.makeWorkspace) {
            infoFormBox.setVisibility(View.GONE);
            makeWorkBox.setVisibility(View.VISIBLE);
            //회원가입
        } else if (v.getId() == R.id.moveWorkspace) {
            if (imagePath != "") {
                fileUpload(imagePath);
            }else{
                registerUser("");
            }
            showProgressDialog();
        }
    }

    private void registerUser(String filePath){
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", email);
            obj.put("firstName", names[1]);
            obj.put("lastName", names[0]);
            obj.put("photoPath", filePath);
            UUID uuid = UUID.randomUUID();
            obj.put("password",uuid.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequestTask mHttpRequestTask = new HttpRequestTask(this, "POST", obj.toString(), handler, 0);
        mHttpRequestTask.execute(RequestPref.pref + "/register/create/" + workspaceEdit.getText().toString());
    }

    private View.OnClickListener photoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                default:
                    break;
            }

        }
    }

    private void sendPicture(Uri imgUri) {

        imagePath = getRealPathFromURI(imgUri);// path 경로

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//
        bitmap = rotate(bitmap, exifDegree);
//        bitmap =imgReSize(imagePath);
        resultImageView.setImageBitmap(bitmap);//이미지 뷰에 비트맵 넣기
    }

    private Bitmap sendPictureString(String imagePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap =imgReSize(imagePath);

        return rotate(bitmap, exifDegree);//이미지 뷰에 비트맵 넣기
    }

    private Bitmap imgReSize(String imagePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        try {
            InputStream in = new FileInputStream(imagePath);
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = -1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? height : widthRatio;
        }

        BitmapFactory.Options imgOptions = new BitmapFactory.Options();
        imgOptions.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, imgOptions);
        return  bitmap;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imagePath", imagePath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (imagePath != "") {
            imagePath = savedInstanceState.getString("imagePath");
            resultImageView.setImageBitmap(sendPictureString(imagePath));
        }

    }

    private void fileUpload(String file) {
        HttpFileUploadTask fileUploadTask = new HttpFileUploadTask(JoinFormActivity.this, file, handler, 1);
        fileUploadTask.execute(RequestPref.pref + "/uploadFile");
    }
}
