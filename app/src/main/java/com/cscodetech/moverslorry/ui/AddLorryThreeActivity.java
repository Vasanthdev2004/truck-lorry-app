package com.ruru.routelorry.ui;

import static com.ruru.routelorry.utils.FileUtils.isLocal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.RestResponse;
import com.ruru.routelorry.model.TempAddLorry;
import com.ruru.routelorry.model.UserLogin;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLorryThreeActivity extends BaseActivity {


    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.txt_actionbar)
    public TextView txtActionbar;
    @BindView(R.id.ed_materialname)
    public EditText edMaterialname;
    @BindView(R.id.txt_subtitle)
    public TextView txtSubtitle;
    @BindView(R.id.img_isuploadp)
    public ImageView imgIsuploadp;
    @BindView(R.id.lvl_document)
    public LinearLayout lvlDocument;
    @BindView(R.id.btn_next)
    public TextView btnNext;
    @BindView(R.id.recyclerselectimg)
    public RecyclerView recyclerselectimg;
    CustPrograssbar custPrograssbar;

    UserLogin userLogin;

    ArrayList<Imagemode> arrayListImage = new ArrayList<>();
    TempAddLorry tempAddLorry;
    String image="image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lorry_three);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();

        userLogin = sessionmanager.getUserDetails();

        tempAddLorry = (TempAddLorry) getIntent().getSerializableExtra("mydata");
        recyclerselectimg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerselectimg.setItemAnimator(new DefaultItemAnimator());

        if (!tempAddLorry.getRecordId().equalsIgnoreCase("-1")) {
            edMaterialname.setText(tempAddLorry.getDescription());
        }

    }


    @OnClick({R.id.img_back, R.id.btn_next, R.id.lvl_document})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                if (!TextUtils.isEmpty(edMaterialname.getText().toString())) {
                    tempAddLorry.setDescription(edMaterialname.getText().toString());
                    if (arrayListImage.size() != 0 && tempAddLorry.getRecordId().equalsIgnoreCase("-1")) {

                        uploadMultiFile(arrayListImage);

                    } else {
                        uploadEditMultiFile(arrayListImage);
                    }
                } else {
                    edMaterialname.setError("");
                }

                break;

            case R.id.lvl_document:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);


                break;
            default:
                break;

        }
    }

    private void uploadMultiFile(ArrayList<Imagemode> filePaths) {
        custPrograssbar.prograssCreate(AddLorryThreeActivity.this);
        List<MultipartBody.Part> parts = new ArrayList<>();

        if (filePaths != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < filePaths.size(); i++) {
                parts.add(prepareFilePart(image + i, filePaths.get(i).img));
            }
        }
// create a map of data to pass along

        RequestBody ownerid = createPartFromString(tempAddLorry.getOwnerId());
        RequestBody lorryNo = createPartFromString(tempAddLorry.getLorryNo());
        RequestBody weight = createPartFromString(tempAddLorry.getWeight());
        RequestBody description = createPartFromString(tempAddLorry.getDescription());
        RequestBody vehicleId = createPartFromString(tempAddLorry.getVehicleId());
        RequestBody status = createPartFromString("1");
        RequestBody currLocation = createPartFromString(tempAddLorry.getCurrLocation());
        RequestBody currStateId = createPartFromString("1");
        RequestBody routes = createPartFromString(tempAddLorry.getRoutes());

        RequestBody size = createPartFromString("" + parts.size());

// finally, execute the request
        Call<JsonObject> call = APIClient.getInterface().uploadMultiFile(ownerid, lorryNo, weight, description, vehicleId, status, currLocation, currStateId, routes, size, parts);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                custPrograssbar.closePrograssBar();
                Gson gson = new Gson();
                RestResponse restResponse = gson.fromJson(response.body(), RestResponse.class);
                Toast.makeText(AddLorryThreeActivity.this, restResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                if (restResponse.getResult().equalsIgnoreCase("true")) {

                    arrayListImage.clear();
                    startActivity(new Intent(AddLorryThreeActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                custPrograssbar.closePrograssBar();

            }
        });
    }

    private void uploadEditMultiFile(ArrayList<Imagemode> filePaths) {
        custPrograssbar.prograssCreate(AddLorryThreeActivity.this);
        List<MultipartBody.Part> parts = new ArrayList<>();

        if (filePaths != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < filePaths.size(); i++) {
                parts.add(prepareFilePart(image + i, filePaths.get(i).img));
            }
        }
// create a map of data to pass along

        RequestBody recordid = createPartFromString(tempAddLorry.getRecordId());
        RequestBody ownerid = createPartFromString(tempAddLorry.getOwnerId());
        RequestBody lorryNo = createPartFromString(tempAddLorry.getLorryNo());
        RequestBody weight = createPartFromString(tempAddLorry.getWeight());
        RequestBody description = createPartFromString(tempAddLorry.getDescription());
        RequestBody vehicleId = createPartFromString(tempAddLorry.getVehicleId());
        RequestBody status = createPartFromString("1");
        RequestBody currLocation = createPartFromString(tempAddLorry.getCurrLocation());
        RequestBody currStateId = createPartFromString("1");
        RequestBody routes = createPartFromString(tempAddLorry.getRoutes());

        RequestBody size = createPartFromString("" + parts.size());

// finally, execute the request
        Call<JsonObject> call = APIClient.getInterface().uploadEditMultiFile(recordid, ownerid, lorryNo, weight, description, vehicleId, status, currLocation, currStateId, routes, size, parts);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                custPrograssbar.closePrograssBar();
                Gson gson = new Gson();
                RestResponse restResponse = gson.fromJson(response.body(), RestResponse.class);
                Toast.makeText(AddLorryThreeActivity.this, restResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                if (restResponse.getResult().equalsIgnoreCase("true")) {
                    arrayListImage.clear();
                    startActivity(new Intent(AddLorryThreeActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }

            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                custPrograssbar.closePrograssBar();

            }
        });
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = getFile(fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static File getFile(String path) {
        if (path == null) {
            return null;
        }

        if (isLocal(path)) {
            return new File(path);
        }
        return null;
    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    Uri imageUri = result.getData().getData();
                    try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {
                        File outputDir = getApplicationContext().getFilesDir();
                        File outputFile = File.createTempFile(image, null, outputDir);
                        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }
                        // Get the file path of the saved image file
                        String filePath = outputFile.getAbsolutePath();
                        if (filePath != null) {
                            Imagemode imagemode = new Imagemode();
                            imagemode.setImg(filePath);
                            imagemode.setType("selectimge");
                            arrayListImage.add(imagemode);
                            postImage(arrayListImage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


    public void postImage(List<Imagemode> urilist) {
        ImageAdp imageAdp = new ImageAdp(urilist);
        recyclerselectimg.setAdapter(imageAdp);
        imgeselect(urilist);

    }

    public void imgeselect(List<Imagemode> urilist) {
        if (urilist.size() == 2) {
            imgIsuploadp.setVisibility(View.VISIBLE);
            txtSubtitle.setText(getString(R.string.uploaded_successfully));
            btnNext.setVisibility(View.VISIBLE);
        } else {
            imgIsuploadp.setVisibility(View.GONE);
            txtSubtitle.setText(getString(R.string.haven_t_uploaded_yet));
            btnNext.setVisibility(View.GONE);

        }

    }

    public class ImageAdp extends RecyclerView.Adapter<ImageAdp.MyViewHolder> {
        private List<Imagemode> arrayList;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView remove;
            ImageView thumbnail;
            TextView txtType;

            public MyViewHolder(View view) {
                super(view);

                thumbnail = view.findViewById(R.id.image_pic);
                remove = view.findViewById(R.id.image_remove);
                txtType = view.findViewById(R.id.txt_type);
            }
        }

        public ImageAdp(List<Imagemode> arrayList) {
            this.arrayList = arrayList;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.imageview_layout, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {


            Glide.with(AddLorryThreeActivity.this)
                    .load(arrayList.get(position).img)
                    .thumbnail(Glide.with(AddLorryThreeActivity.this).load(R.drawable.tprofile))
                    .into(holder.thumbnail);
            holder.txtType.setVisibility(View.GONE);
            holder.remove.setOnClickListener(v -> {

                if (!arrayList.isEmpty()) {
                    arrayList.remove(position);
                    notifyDataSetChanged();
                    imgeselect(arrayList);
                }

            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    class Imagemode {
        String type;
        String img;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }


}