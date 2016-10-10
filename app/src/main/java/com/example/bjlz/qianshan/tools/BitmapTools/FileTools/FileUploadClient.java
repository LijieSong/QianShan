//package com.example.bjlz.qianshan.tools.BitmapTools.FileTools;
//
//import android.content.Context;
//
//import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
//
//import java.io.File;
//import java.io.IOException;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * 文件上传工具类、待完善
// * Created by slj on 2016/5/26.
// */
//public class FileUploadClient {
//    //////////////////////////////////////////////////////////
//    ////////////////////上传接口地址
//    private static final String URL = "这里是地址";
//    //////////////////////////////////////////////////////////
//    ////////////////////上传文件类型
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    private static final OkHttpClient client = new OkHttpClient();
//    // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
//    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
////    map.put("clientVersion", "1.0.0");
//
//    public static void uploadFilePost(Context ctx, String moduleName, String filepath) {
//        File uploadFile = new File(filepath);
//        RequestBody requestBody = new MultipartBody().type(MultipartBody.FORM)
//                .addFormDataPart("moduleName", moduleName)
////                .addFormDataPart("token", (String) SPUtil.getParam(ctx, Config.TOKEN, ""))
//                .addFormDataPart("clientVersion", "1.0.0")
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"clientVersion\""),
////                        RequestBody.create(null, "1.0.0"))
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"moduleName\""),
////                        RequestBody.create(null, moduleName))
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"token\""),
////                        RequestBody.create(null, (String) SPUtil.getParam(ctx, Config.TOKEN, "")))
////                .addPart(
////                        Headers.of("Content-Disposition", "form-data; name=\"imageFile\""),
////                        RequestBody.create(MEDIA_TYPE_PNG, uploadFile))
//                .addFormDataPart("imageFile", "image.png", RequestBody.create(MEDIA_TYPE_PNG, uploadFile))
//                .build();
//
//        Request request = new Request.Builder()
//                .url(URL)
////                .addHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY)
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.error("e:" + e.getMessage());
//                try {
//                    Response s = call.execute();
//                    LogUtils.error(s.toString());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                int code = response.code();
//                LogUtils.error(code + "");
//            }
//        });
//    }
//}
