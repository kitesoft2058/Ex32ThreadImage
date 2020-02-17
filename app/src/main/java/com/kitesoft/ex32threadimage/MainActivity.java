package com.kitesoft.ex32threadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);
    }

    public void clickBtn(View v){

        //네트워크 작업을 위해서는 반드시 Internet사용에 대한 퍼미션(permission)을 AndroidManifest.xml에 사용설정해야함.

        //MainThread는 네트워크 작업 불가!!!
        //별도의 Thread를 생성해서 네트워크작업 수행하도록..
        new Thread(){
            @Override
            public void run() {
                //Network에 있는 이미지를 읽어와서
                //이미지뷰에 설정
//                String imgUrl="http://img.seoul.co.kr/img/upload/2017/10/07/SSI_20171007154542_V.jpg";
//                String imgUrl="https://img.insight.co.kr/static/2018/05/18/700/w5k620g060q79k437y2f.jpg";
                String imgUrl="https://i.pinimg.com/originals/88/2a/9d/882a9deb197de85fc2d79c2f1d86dc8a.jpg";

                //Stream을 만들수 있는 해임달객체(URL) 생성
                try {
                    final URL url= new URL(imgUrl);
                    //무지개로드(Stream)을 얻어오기
                    InputStream is= url.openStream();

                    //스트림을 통해 이미지를 읽어와서
                    //이미지 뷰에 설정!!
                    final Bitmap bm= BitmapFactory.decodeStream(is);

                    //별도의 Thread는 UI변경작업 불가!!
                    //UI변경작업은 UI(Main)스레드만 가능
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //UI변경 가능!!
                            iv.setImageBitmap(bm);
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }//clickBtn method....

}//MainActivity class....
