package com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.CategoryCollapseAdapter;
import com.ptit.tranhoangminh.newsharefood.adapters.MaterialAdapter;
import com.ptit.tranhoangminh.newsharefood.adapters.RecipeAdapter;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.presenters.addEditProductPresenters.AddEditProductPresenter;
import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;

import java.security.acl.Group;
import java.util.Calendar;
import java.util.List;

public class NewModifyProductActivity extends AppCompatActivity implements AddEditProductView {
    private FrameLayout frameMaterial, frameRecipe, frameCategory;
    private MaterialAdapter materialAdapter;
    private RecipeAdapter recipeAdapter;
    private CategoryCollapseAdapter categoryCollapseAdapter;
    private ImageView img;
    private Toolbar toolbar;
    private TextView txtMaterial, txtRecipe, txtCategory;
    private ProgressBar pgbModify;
    private final int REQUEST_CODE_TAKEPHOTO = 1;
    private final int REQUEST_CODE_PICKPHOTO = 2;
    private AddEditProductPresenter addEditProductPresenter;
    private boolean changedPic;
    private EditText edtName, edtVideo;
    private String cate_id, cate_name;
    private int mode;
    private Product pd;
    private ProductDetail pdetail;

    private static String begin_pattern = "v=";
    private static String end_pattern = "&";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_modify_product_layout);
        setControls();

        frameMaterial.setVisibility(View.GONE);
        frameRecipe.setVisibility(View.GONE);
        frameCategory.setVisibility(View.GONE);
        materialAdapter = new MaterialAdapter((TableLayout) findViewById(R.id.tablelayoutMaterial), this);
        recipeAdapter = new RecipeAdapter((TableLayout) findViewById(R.id.tablelayoutRecipe), this);
        categoryCollapseAdapter = new CategoryCollapseAdapter((TableLayout) findViewById(R.id.tablelayoutCategories), this, this);
        registerForContextMenu(img);
        img.setLongClickable(false);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEditProductPresenter = new AddEditProductPresenter(this);
        addEditProductPresenter.loadCategory();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mode = bundle.getInt("mode");
            if (mode == ProductActivity.EDIT_MODE) {
                pd = (Product) bundle.getSerializable("product");
                edtName.setText(pd.getName());
                cate_id = bundle.getString("cate_id");
                cate_name = bundle.getString("cate_name");
                addEditProductPresenter.loadEditProduct(pd.getId(), pd.getImage());
            }
        }
    }

    private void setControls() {
        frameMaterial = findViewById(R.id.frameMaterials);
        frameRecipe = findViewById(R.id.frameRecipe);
        frameCategory = findViewById(R.id.frameCategories);
        toolbar = findViewById(R.id.toolbar2);
        img = findViewById(R.id.imageViewAdd);
        txtMaterial = findViewById(R.id.txtMaterial);
        txtRecipe = findViewById(R.id.txtRecipe);
        pgbModify = findViewById(R.id.progressbarModifyProduct);
        txtCategory = findViewById(R.id.txtCategory);
        edtName = findViewById(R.id.editTextName);
        edtVideo = findViewById(R.id.editTextVideo);
        cate_id = "";
        changedPic = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                try {
                    String name = edtName.getText().toString();
                    String video = edtVideo.getText().toString();
                    if (name.equals("")) edtName.setError("Empty name");
                    if (cate_id == "") txtCategory.setError("Don't choose yet");
                    if (video.equals("")) edtVideo.setError("Empty video");
                    String material = materialAdapter.getMaterials();
                    String recipe = recipeAdapter.getRecipe();
                    int begin = video.indexOf(begin_pattern);
                    boolean b;
                    if (begin == -1) {
                        edtVideo.setError("Wrong format video");
                        return true;
                    }
                    int end = video.indexOf(end_pattern);
                    String id_video;
                    if (end == -1) {
                        id_video = video.substring(begin + begin_pattern.length());
                    }
                    else {
                        id_video = video.substring(begin + begin_pattern.length(), end);
                    }

                    if (name != "" && material != "" && recipe != "" && cate_id != "") {
                        if (mode == ProductActivity.ADD_MODE) {
                            ProductDetail newProductDetail = new ProductDetail(0, 0, material, recipe, id_video, "");
                            Product newProduct;
                            if (!changedPic) {
                                newProduct = new Product("", cate_id, name, "default_image.png");
                                addEditProductPresenter.saveNewProduct(newProduct, newProductDetail, null);
                            } else {
                                Calendar calendar = Calendar.getInstance();
                                String imageName = "image" + calendar.getTimeInMillis() + ".png";
                                newProduct = new Product("", cate_id, edtName.getText().toString(), imageName);
                                addEditProductPresenter.saveNewProduct(newProduct, newProductDetail, ((BitmapDrawable) img.getDrawable()).getBitmap());
                            }
                        } else if (mode == ProductActivity.EDIT_MODE) {
                            pd.setName(edtName.getText().toString());
                            pd.setParent_id(cate_id);
                            pdetail.setMaterials(material);
                            pdetail.setRecipe(recipe);
                            if (!changedPic) {
                                addEditProductPresenter.saveOldProduct(pd, pdetail);
                            } else {
                                addEditProductPresenter.saveOldProduct(pd, pdetail, ((BitmapDrawable) img.getDrawable()).getBitmap());
                            }
                        }
                    }
                } catch (Exception e) {
                    String st = e.getMessage();
                    if (st.equals("MaterialException"))
                        txtMaterial.setError("");
                    else if (st.equals("RecipeException"))
                        txtRecipe.setError("");
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showpickimage(View view) {
        img.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_image_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuTakepic:
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_CODE_TAKEPHOTO);
                return true;
            case R.id.menuChoosepic:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_PICKPHOTO);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKEPHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    changedPic = true;
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    img.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_PICKPHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    changedPic = true;
                    Uri selectedImage = data.getData();
                    img.setImageURI(selectedImage);
                    Log.d("picture box", selectedImage.getPath());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toggle_categories(View view) {
        if (frameCategory.isShown()) {
            frameCategory.setVisibility(View.GONE);
            slide_up(this, frameCategory);
        } else {
            frameCategory.setVisibility(View.VISIBLE);
            slide_down(this, frameCategory);
        }
    }

    public void toggle_materials(View view) {
        if (frameMaterial.isShown()) {
            frameMaterial.setVisibility(View.GONE);
            slide_up(this, frameMaterial);
        } else {
            frameMaterial.setVisibility(View.VISIBLE);
            slide_down(this, frameMaterial);
        }
    }

    public void toggle_recipe(View view) {
        if (frameRecipe.isShown()) {
            frameRecipe.setVisibility(View.GONE);
            slide_up(this, frameRecipe);
        } else {
            frameRecipe.setVisibility(View.VISIBLE);
            slide_down(this, frameRecipe);
        }
    }

    public void add_new_row_material(View view) {
        if (!frameMaterial.isShown()) {
            frameMaterial.setVisibility(View.VISIBLE);
            slide_down(this, frameMaterial);
        }
        materialAdapter.addNewRow();
    }

    public void add_new_row_recipe(View view) {
        if (!frameRecipe.isShown()) {
            frameRecipe.setVisibility(View.VISIBLE);
            slide_down(this, frameRecipe);
        }
        recipeAdapter.addNewRow();
    }

    void slide_down(Context context, View v) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    void slide_up(Context context, View v) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    @Override
    public void showProgress() {
        pgbModify.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbModify.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayEditProduct(ProductDetail productDetail, Bitmap bitmap) {
        pdetail = productDetail;
        img.setImageBitmap(bitmap);
        materialAdapter.setMaterials(productDetail.getMaterials());
        recipeAdapter.setRecipe(productDetail.getRecipe());
        edtVideo.setText(productDetail.getVideo());
    }

    @Override
    public void displayCategoryList(List<Category> categories) {
        categoryCollapseAdapter.addView(categories);
        if (!cate_id.equals("")) {
            for (Category cate : categories) {
                if (cate.getId().equals(cate_id)) {
                    txtCategory.setText(cate.getName());
                    break;
                }
            }
        }
    }

    @Override
    public void backActivity() {
        setResult(1111);
        finish();
    }

    @Override
    public void setCategory(Category category) {
        if (frameCategory.isShown()) {
            frameCategory.setVisibility(View.GONE);
            slide_up(this, frameCategory);
        }
        txtCategory.setText(category.getName());
        cate_id = category.getId();
    }
}
