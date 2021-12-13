package br.java.a33_contatos.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import br.java.a33_contatos.R;
import br.java.a33_contatos.util.ContatoUtils;


public class ContatoFragment extends DialogFragment
           implements DialogInterface.OnClickListener, View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    Bitmap mFoto;
    ImageView mImgPhoto;
    EditText mEdtNome, mEdtEndereco, mEdtPhone;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_contato, null);

        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mImgPhoto.setOnClickListener(this);
        mEdtNome = (EditText) view.findViewById(R.id.edtNome);
        mEdtEndereco = (EditText) view.findViewById(R.id.edtEndereco);
        mEdtPhone = (EditText) view.findViewById(R.id.edtFone);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.adic_contato)
                .setView(view)
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancelar, null)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ContatoUtils.inserirContato(
                getActivity(),
                mEdtNome.getText().toString(),
                mEdtPhone.getText().toString(),
                mEdtEndereco.getText().toString(),
                mFoto);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            mFoto = BitmapFactory.decodeStream(
                    getActivity().getContentResolver().openInputStream(
                            data.getData()),
                    null,
                    options);

            mImgPhoto.setImageBitmap(mFoto);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}