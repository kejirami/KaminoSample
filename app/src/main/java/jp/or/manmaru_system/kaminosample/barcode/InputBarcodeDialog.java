package jp.or.manmaru_system.kaminosample.barcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.ToneGenerator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.List;

import jp.or.manmaru_system.kaminosample.R;

public class InputBarcodeDialog {
    public static Dialog show(Context context, ToneGenerator tg, InputBarcodeCallback cb) {
        TextView titleView = new TextView(context);
        titleView.setText("バーコード入力");
        titleView.setTextSize(30);

        titleView.setBackgroundColor(Color.GREEN);
        titleView.setTextColor(Color.WHITE);
        titleView.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.barcode_view, null);
        CompoundBarcodeView bv_Barcode = view.findViewById(R.id.barcodeView);
        CameraSettings settings = bv_Barcode.getBarcodeView().getCameraSettings();
        bv_Barcode.getBarcodeView().setCameraSettings(settings);
        bv_Barcode.setStatusText("シンボルを読んでください");
        bv_Barcode.setTorchOn();
//        bv_Barcode.setPadding(20, 20, 20, 20);
//        bv_Barcode.setLayoutParams(new CompoundBarcodeView.LayoutParams(-2,300));
        bv_Barcode.resume();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(titleView)
                .setView(view)
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        bv_Barcode.pauseAndWait();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        bv_Barcode.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                bv_Barcode.setStatusText(result.getText());
                tg.startTone(ToneGenerator.TONE_PROP_BEEP,100);
                if(cb != null) cb.run(result.getText());
                dialog.cancel();
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        });
        dialog.show();
        return dialog;
    }
}
