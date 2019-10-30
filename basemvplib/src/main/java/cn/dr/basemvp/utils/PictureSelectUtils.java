package cn.dr.basemvp.utils;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

/**
 * Created by dingrui 2019/10/30
 */

public class PictureSelectUtils {

    public static void openCamera(Activity context) {
        PictureSelector.create(context)
                .openCamera(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void openCamera(Fragment context) {
        PictureSelector.create(context)
                .openCamera(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void openGallery(Activity context, boolean single, boolean circleCrop, int max) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(single ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .maxSelectNum(max)
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .circleDimmedLayer(circleCrop)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void openGallery(Fragment context, boolean single, boolean circleCrop, int max) {
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(single ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .maxSelectNum(max)
                .circleDimmedLayer(circleCrop)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
