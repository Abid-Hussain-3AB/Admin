package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Other.SplashActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDelProductActivity extends AppCompatActivity {
    EditText editproductname, editproducttype, editproductquantity;
    private DatabaseReference mFirebaseDatabase;
    Button btndel,btnedit,btnconfirm,btncancel;
    String PId,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_del_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Delete, Edit Product");
        Intent intent = getIntent();
        editproductname = findViewById(R.id.EditProductName);
        editproductname.setVisibility(View.GONE);
        editproducttype = findViewById(R.id.EditProductType);
        editproducttype.setVisibility(View.GONE);
        editproductquantity = findViewById(R.id.EditQuantity);
        editproductquantity.setVisibility(View.GONE);
        btndel = findViewById(R.id.btnDel);
        btnedit = findViewById(R.id.btnEdit);
        btnconfirm = findViewById(R.id.btnConfirm);
        btncancel = findViewById(R.id.btnCancel);
        btnconfirm.setVisibility(View.GONE);
        btncancel.setVisibility(View.GONE);
        PId = intent.getStringExtra("ID");
        image = intent.getStringExtra("image");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditDelProductActivity.this);
                alertDialog.setTitle("Do you want to delete this product?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DellProduct();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editproductname.setVisibility(View.VISIBLE);
                editproducttype.setVisibility(View.VISIBLE);
                editproductquantity.setVisibility(View.VISIBLE);
                getData();
                btndel.setVisibility(View.GONE);
                btnconfirm.setVisibility(View.VISIBLE);
                btncancel.setVisibility(View.VISIBLE);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  //  startActivity(new Intent(EditDelProductActivity.this, ShowProductsActivity.class));
                Intent intent1 = new Intent();
               // intent1.putExtra("delete",true);
                setResult(Activity.RESULT_OK,intent1);
                finish();
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditDelProductActivity.this);
                alertDialog.setTitle("Do you want to Edit this product?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ((TextUtils.isEmpty(editproductname.getText().toString()))) {
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Name.", Toast.LENGTH_LONG).show();
                        } else if ((TextUtils.isEmpty(editproducttype.getText().toString()))) {
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Type.", Toast.LENGTH_LONG).show();
                        } else if ((TextUtils.isEmpty(editproductquantity.getText().toString()))) {
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Quantity.", Toast.LENGTH_LONG).show();
                        } else {
                            EditProduct();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void DellProduct()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            dataSnapshot2.child(PId).getRef().removeValue();
                            Toast.makeText(EditDelProductActivity.this, "Product Delete Successfully", Toast.LENGTH_LONG ).show();
                        }
                    }
                }
                btncancel.setVisibility(View.VISIBLE);
                btncancel.setText("Back to Products");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getData()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.hasChild(PId)) {
                                ProductDataClass sdc = dataSnapshot2.child(PId).getValue(ProductDataClass.class);
                                 assert sdc != null;
                                String name = sdc.getProductName();
                                String type = sdc.getProductType();
                                String quantity = sdc.getProductQuantity();
                                editproductname.setText(name);
                                editproducttype.setText(type);
                                editproductquantity.setText(quantity);
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void EditProduct()
    {
        String name = editproductname.getText().toString();
        String type = editproducttype.getText().toString();
        String quantity = editproductquantity.getText().toString();

        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDataClass user = new ProductDataClass(name, type, quantity,PId,image);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.hasChild(PId)) {
                            dataSnapshot2.child(PId).getRef().setValue(user);
                            Toast.makeText(EditDelProductActivity.this, "Product Update Successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Intent intent1 = new Intent();
                 intent1.putExtra("delete",true);
                 setResult(RESULT_OK,intent1);
                 finish();
                //btncancel.setText("Back to Products");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditDelProductActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}