package com.ptit.tranhoangminh.newsharefood.presenters.addEditProductPresenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEditProductInteractor {
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private LoadAddEditProductListener listener;

    public AddEditProductInteractor(LoadAddEditProductListener listener) {
        this.listener = listener;
    }

    public void createCategoryList() {
        myRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                List<Category> cateList = new ArrayList<>();
                for (DataSnapshot item : iterable) {
                    cateList.add(item.getValue(Category.class));
                }
                listener.onLoadCategorySuccess(cateList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLoadCategoryFailure("Failed to read categories.");
            }
        });
    }

    public void createEditProduct(final String id, final String image_id) {
        myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot item : iterable) {
                    final ProductDetail pdetailKey = item.getValue(ProductDetail.class);
                    if (pdetailKey.getId().equals(id)) {
                        try {
                            final File localFile = File.createTempFile(image_id, ".png");
                            mStorageRef.child("Products").child(image_id).getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            final Bitmap[] bitmap = new Bitmap[1];
                                            bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            listener.onLoadEditProductSuccess(pdetailKey, bitmap[0]);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    listener.onLoadEditProductFailure("Failed to read image. " + exception.getMessage());
                                    listener.onLoadEditProductSuccess(pdetailKey, null);
                                }
                            });
                            break;
                        } catch (IOException e) {
                            listener.onLoadEditProductFailure("Failed to create temp file. " + e.getMessage());
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLoadEditProductFailure("ProductDetailActivity: Failed to read product. " + databaseError.toException());
            }
        });
    }

    public void pushNewProduct(final Product product, final ProductDetail productDetail, final Bitmap bitmap) {
        DatabaseReference productRef = myRef.child("Products").push();
        final String id = productRef.getKey();
        product.setId(id);
        if (user != null) {
            product.setMember_id(user.getUid());
        }
        productRef.setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                productDetail.setId(id);
                myRef.child("ProductDetail").push().setValue(productDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (bitmap == null) {
                            listener.onPushNewProductSuccess();
                            return;
                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();
                        UploadTask uploadTask = mStorageRef.child("Products").child(product.getImage()).putBytes(data);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Uri downloadURL = taskSnapshot.getDownloadUrl();
                                listener.onPushNewProductSuccess();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onPushNewProductFailure("Failed to write image. " + e.getMessage());
                                listener.onPushNewProductSuccess();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onPushNewProductFailure("Failed to save product detail. " + e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onPushNewProductFailure("Failed to save product. " + e.getMessage());
            }
        });
    }

    public void setOldProduct(final Product product, final ProductDetail productDetail) {
        myRef.child("Products").child(product.getId()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                        for (DataSnapshot item : iterable) {
                            final ProductDetail pdetailKey = item.getValue(ProductDetail.class);
                            if (pdetailKey.getId().equals(productDetail.getId())) {
                                item.getRef().setValue(productDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        listener.onSetOldProductSuccess();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        listener.onSetOldProductFailure("Failed to save product detail. " + e.getMessage());
                                    }
                                });
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onSetOldProductFailure("Failed to find product detail. " + databaseError.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onSetOldProductFailure("Failed to save product. " + e.getMessage());
            }
        });
    }

    public void setOldProduct(final Product product, final ProductDetail productDetail, final Bitmap bitmap) {
        myRef.child("Products").child(product.getId()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                        for (DataSnapshot item : iterable) {
                            final ProductDetail pdetailKey = item.getValue(ProductDetail.class);
                            if (pdetailKey.getId().equals(productDetail.getId())) {
                                item.getRef().setValue(productDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                        byte[] data = baos.toByteArray();
                                        UploadTask uploadTask = mStorageRef.child("Products").child(product.getImage()).putBytes(data);
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                //Uri downloadURL = taskSnapshot.getDownloadUrl();
                                                listener.onSetOldProductSuccess();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                listener.onSetOldProductFailure("Failed to write image. " + e.getMessage());
                                                listener.onSetOldProductSuccess();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        listener.onSetOldProductFailure("Failed to save product detail. " + e.getMessage());
                                    }
                                });
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onSetOldProductFailure("Failed to find product detail. " + databaseError.getMessage());
                    }
                });
            }
        });
    }
}
